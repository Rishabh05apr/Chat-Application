import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
class server1 extends Thread
{
	Socket sock;
	Vector<Socket> v=new Vector<Socket>();
	Vector<String> v1=new Vector<String>();
	ServerSocket ss;		
	int count=0;

	public void  server1()
	{
		try
		{
			
			ss=new ServerSocket(8081);
			while(true)
			{
				System.out.println("Server starting");
				 sock=ss.accept();
				v.add(sock);
				boolean g=call();
				if(g==true)
				{
					System.out.println(" g value in while loop:- "+g);
					new sn(sock, v,v1).start();
					System.out.println("client port number:- "+sock.getPort());			
				} 	
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in server :- "+e.getMessage());
			e.printStackTrace();
		}	
	}
	
	public String check(String g)
	{
		boolean rag=false;
		int pos=0;
		System.out.println("Enter in check");
		for(int i=0;i<v1.size();i++)
		{
			String f=v1.get(i);
			String f1[]=f.split("###");
			if(g.equals(f1[0]))
			{
				rag=true;
				pos=i;
				System.out.println(v1.get(i));
				break;
			}
		}
		System.out.println("return value:- "+rag+"C"+pos);
		return rag+"C"+pos;
	}
	public boolean call()
	{
		boolean flag=false;
		try{
		
		System.out.println("enter in call");
		BufferedReader in=new BufferedReader(new InputStreamReader(sock.getInputStream()));		
		String text=in.readLine();
		System.out.println("text is:- "+text);
		if(text.contains("###"))
		{
			String x[]=text.split("###");
			String getans=check(x[0]);
			String y[]=getans.split("C");
			System.out.println("y value:- "+y[0]);
			if(y[0].equals("false"))
			{
				v1.add(x[0]+"###"+sock.getPort());		
				System.out.println(v1.size());
				int xyz=Integer.parseInt(y[1]);
				PrintWriter out=new PrintWriter(v.get((v.size()-1)).getOutputStream(),true);
				out.println("new");
				flag=true;
			
			}
			else if(y[0].equals("true"))
			{
				System.out.println("\n*************already present******** ");
				int xyz=Integer.parseInt(y[1]);
				PrintWriter out=new PrintWriter(v.get((v.size()-1)).getOutputStream(),true);
				out.println("already added");
				v.removeElementAt((v.size()-1));	
				flag=false;
			}
				
			System.out.println("Entered in call:- ");
		
		if(flag==true)
		{
			for(int  i=0;i<v.size();i++)
			{	
				PrintWriter out=new PrintWriter(v.get(i).getOutputStream(),true);
				int j;
				System.out.println("size of v1:- "+v1.size());
				for(j=0;j<v1.size();j++)
				{
					String text1=v1.get(j);
					System.out.println("wrriten text:- "+text1);
					try
					{
						Robot r=new Robot();
						r.delay(20);	
					}
					catch(Exception e1)
					{
						System.out.println("Exception in robot  method:- "+e1.getMessage());
					}
					out.println(text1);	
				}//end of for loop
			
			}
		}//end of if(flag==true)

		}
		else
		flag=true;
		
		}
		catch(Exception e)
		{
			System.out.println("Exception in call method:- "+e.getMessage());
		}
		System.out.println("flag value at the end of call function:- "+flag);						
		return flag;
	}
	
	
	public static void main(String args[])
	{
		server1 s147=new server1();
		s147.server1();
	
	}

}
	
class sn extends Thread
{
	Socket sock1;	
	Vector <Socket> v;
	Vector <String> v1;
	public sn(Socket s,Vector<Socket> k,Vector<String> k1)
	{
		sock1=s;	
		v=k;
		v1=k1;
	}
	public void run()
	{
		try{
			while(true)
			{
				System.out.println(".......start of while loop........");
				
				BufferedReader in=new BufferedReader(new InputStreamReader(sock1.getInputStream()));		
		
				String text=in.readLine();
				System.out.println(sock1.getPort()+" typed: "+text);
				
				
				if(v.size()>1)
				{
					for(int  i=0;i<v.size();i++)
					{
						System.out.println("entered in for loop for writing");
						PrintWriter out=new PrintWriter(v.get(i).getOutputStream(),true);
						out.println(text);
						if(text.equals("end"))
						break;
					}
					if(text.equalsIgnoreCase("END"))
					{
		 				System.out.println("Chatting over");
					}		
				}
				
				if(text.contains("%%%"))
				{
					String de[]=text.split("%%%");
					int index=v1.indexOf(de[1]);
					System.out.println("\n\n........index of "+de[1]+" is:- "+index+"\n\n");
					v1.removeElementAt(index);
					v.removeElementAt(index);
					System.out.println(v1.size()+"      "+v.size());
				}
				

				System.out.println(".......end of while loop........");
				
			}
		}
		catch(Exception e)
		{
			System.out.println("\n...........Error in client type1 function:;- "+ e.getMessage());
			e.printStackTrace();
		}	
	
		
		System.out.println("thread reached at end");
	}
}


