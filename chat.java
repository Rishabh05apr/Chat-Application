import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class chat extends Thread
{
	JFrame f;
	JPanel p;
	JTextField t1;
	JTextArea t;
	JButton send,mini,close;
	boolean check=false;
	Socket s;
	
	InetAddress addr;
	
	public chat()
	{
	}
	public void chat(String sx,int len,final Vector y)
	{
		try
		{
		f=new JFrame();
	
		
		p=new JPanel();
		f.setTitle(sx);	
		f.setSize(400,400);
		f.setLayout(new BorderLayout());
		
		send=new JButton("Send");
		t=new JTextArea();
		t.setBackground(Color.YELLOW);
		t1=new JTextField();
		t1.setBackground(Color.GREEN);
		t.setEditable(false);
		
		mini=new JButton("Minimize");
		close=new JButton("Close Chat");
		f.add(t,"North");
		f.add(t1,"Center");
	
		p.add(send);
		p.add(mini);
		p.add(close);
		f.add(p,"South");
		addr = InetAddress.getByName("localhost");
		s=new Socket(addr,8081);
		PrintWriter out1=new PrintWriter(s.getOutputStream(),true);
		out1.println(f.getTitle()+"!!!"+s.getPort()); //=== for private chat
		start();
		
		final JFrame fd=f;
		final int lenx=len;
		send.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				if(!(t1.getText().equals("")))
				{
	
					
					System.out.println("send outside run called in chat class");
					System.out.println("message send in chat send is:- "+t1.getText());
				

					int l1=(fd.getTitle().length())-lenx;
					String rev=fd.getTitle().substring(l1,fd.getTitle().length());
					rev=rev+fd.getTitle().substring(0,l1);
				
					String sendmsg=fd.getTitle()+"!!!"+rev+"!!!";	 //!!! for private chat]
					t.setText(t.getText()+"\n"+"Me >"+t1.getText());
					send(sendmsg+t1.getText());
					
				}
			}
		});
		
		// if clicked on mini button then chat window would hide
		mini.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				f.setVisible(false);
			}
		});
		close.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int l1=(fd.getTitle().length())-lenx;
				String rev=fd.getTitle().substring(l1,fd.getTitle().length());
				rev=rev+fd.getTitle().substring(0,l1);
				System.out.println("\nREV:- "+rev+"\n\n");
				for(int i=0;i<y.size();i++)
				{
					if( (y.get(i)).equals(fd.getTitle()) )
					{			
						y.removeElementAt(i);
						
					}
					if( (y.get(i)).equals(rev) )
					{			
						y.removeElementAt(i);
						
					}
				}
					
				System.out.println("***********Elements in vector a1 in CHAT CLASS******************\n\n");	
				for(int i=0;i<y.size();i++)
				{
					System.out.println(y.get(i));
				}	
				send(fd.getTitle()+"close_chat"+rev);
			}
			
		});
					

		f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		f.setVisible(true);
		}
		catch(Exception e32)
		{
			System.out.println("exception in chat frame method:- "+e32.getMessage());
		}
	}

	
	public void send(String s1)
	{
		try
		{
			
			t1.setText("");
			PrintWriter out=new PrintWriter(s.getOutputStream(),true);
			out.println(s1);
		
			System.out.println("Inside Send of ");
		}
		catch(Exception e)
		{			
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		while(true)
	
			{
				try
				{
				System.out.println("run started of "+f.getTitle()+" in CHAT CLASS");
				if(f.getTitle().equals("FINISH"))
				{
					interrupt();
				}
				BufferedReader in1=new BufferedReader(new InputStreamReader(s.getInputStream()));
				String sss=in1.readLine();
				System.out.println("ssss value in chat:- "+sss);

				if(sss.contains("close_chat"))
				{
					String v[]=sss.split("close_chat"); //v[0] contains  the name of the client which closes chat
					if( (f.getTitle().equals(v[0]) )||(f.getTitle().equals(v[1]) ) )
					{
						f.setTitle("FINISH");
						f.dispose();
					}
				}
					
	
				if(sss.contains("!!!"))
				{
					String e[]=sss.split("!!!");
					if(f.getTitle().equals(e[1]))
						{
							f.setVisible(true);
							t.setText(t.getText()+"\n"+e[0]+" >"+e[2]);
						}
					
				}
				
				}//end of try
				catch(Exception e2)
				{
					System.out.println("exception in chat run method:- "+e2.getMessage());
				}
			}
	}
}
							
	
		
