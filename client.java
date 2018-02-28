import java.net.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class client extends Thread
{				
        JFrame f;
	JButton send,vector;	
	
	JTextArea t;
	JTextField t1;
	Frame collect[];
	JScrollPane j,j1;
	JSplitPane sp;
	JPanel p1,p2;
	
	Vector<String> a1=new Vector<String>();
	int index=0;
	boolean flag=true;
	Socket s;
	JList list;
	first f1;
	chat cx,cy,c11;

	InetAddress addr;
	
	DefaultListModel listModel,lm;  
	boolean check=false;
	boolean check1=false;
	String xyz;
	public client()
	{
		f1=new first();
		f1.submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(f1.name.getText().length()==0)
				{
					JOptionPane.showMessageDialog(null,"Please enter your name");
				}
				else
				{
					f1.dispose();
					frame();
					
				}	
			}
		});
	}	   
	public void frame()
	{
		try{
		
		listModel = new DefaultListModel(); 
		lm = new DefaultListModel();
		list = new JList( listModel);
		p1=new JPanel();
		p2=new JPanel();
		p1.setLayout(new GridLayout());
		
			
				
		
		f=new JFrame();
		f.setTitle(f1.name.getText());
		
		f.setSize(400,400);
		f.setLayout(new BorderLayout());
	
		t=new JTextArea();
	//	t.setBackground(Color.RED);
		t1=new JTextField();
	//	t1.setBackground(Color.YELLOW);
		
		
		j=new JScrollPane(t);
		j1=new JScrollPane(t1);
			
		
		t.setEditable(false); 	
		
		
	
		
		sp=new JSplitPane(JSplitPane.VERTICAL_SPLIT,true,j,j1);
		p1.add(sp,"Center");
		f.add(p1,"Center");
		
		
		send=new JButton("Send");
		vector=new JButton("Vector");
		p2.add(send);
	//	p2.add(vector);
		f.add(p2,"South");
	
		f.add(list,"East");
		
		addr = InetAddress.getByName("localhost");
		s=new Socket(addr,8081);
		PrintWriter out1=new PrintWriter(s.getOutputStream(),true);
		out1.println(f.getTitle()+"###"+s.getPort());
		BufferedReader in1=new BufferedReader(new InputStreamReader(s.getInputStream()));
		String s235=in1.readLine();
		if(s235.equals("already added"))
		{
			JOptionPane.showMessageDialog(null,"Name Already Exist");
			s235="aaa";
			new client();
			
		}
		else if(s235.equals("new"))
		{	
		
		System.out.println("ServerConnecting..");
		f.setVisible(true);	
		start();

	/*	vector.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
					
				System.out.println("***********Elements in vector a1 ******************\n\n");	
				for(int i=0;i<a1.size();i++)
				{
					System.out.println(a1.get(i));
				}	
			}
		});*/
			
		send.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				if(!(t1.getText().equals("")))
				{
				System.out.println("send outside run called");
				System.out.println("Text is****:- "+t1.getText());
				String sendmsg=f.getTitle()+"~~~";  //~~~ for global chat
				send(sendmsg+t1.getText());
				}
			}
		});
		list.addMouseListener(new MouseAdapter() 
		{
        			final JFrame fd=f;
			public void mouseClicked(MouseEvent e78) 
			{
       				if(e78.getClickCount() == 2) 
				{
					boolean entry=true;
					
					if(a1.size()>0)
					{
						System.out.println("************Contents of a1************** ");
						for(int i=0;i<a1.size();i++)
						{
							System.out.println(a1.get(i));
						}
					
					
					for(int i=0;i<a1.size();i++)
					{
					
						String get2=(String)list.getSelectedValue();
						final String fz2[]=get2.split(" :");
						String con=fd.getTitle()+fz2[0];
						String revcon=fz2[0]+fd.getTitle();

						
						String check=a1.get(i);
						System.out.println("check value:- "+check);
						if(check.equals(con))      //||(check.equals(revcon)) )
						{

							entry=false;
							send(fd.getTitle()+":::"+fz2[0]);
							System.out.println( a1.get(i)+" already present");
						}
					}		
					}					
					if(entry==true)
					{			
						String get1=(String)list.getSelectedValue();
						final String fz1[]=get1.split(" :");
						
						cx=new chat();
						cy=new chat();			
		
						
						delay();
					
										

						System.out.println("/************ index value is *********/:- "+index);
						cx.chat((fd.getTitle()+fz1[0]) , fz1[0].length(),a1);
						cy.chat((fz1[0]+fd.getTitle()) , fd.getTitle().length(),a1);
					
						a1.add(cx.f.getTitle());
						a1.add(cy.f.getTitle());
						delay();
						send(fd.getTitle()+"a1"+fz1[0]);// to add same elements in the other client vector a1
						cy.f.setVisible(false);
					
					}
					
					
				}
        			}
        		});
		
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				//send("remove name"+f.getTitle()+"###"+s.getLocalPort());
				send("%%%"+f.getTitle()+"###"+s.getLocalPort());
				
			}
		});
      		
		}
		}// end of else "already added"
		catch(Exception e)
		{			
			System.out.println("Error in frame of client:- "+e.getMessage());
		}	
	}

	
	public void delay()
	{
		try
		{
			Robot r=new Robot();
			r.delay(10);
		}
		catch(Exception e)
		{
			System.out.println("Exception in delay function:- "+e.getMessage());
		}
	}
		


	public void send(String s1)
	{
		try
		{
			t1.setText("");
			System.out.println("in send function:- "+s1);
			PrintWriter out=new PrintWriter(s.getOutputStream(),true);
			out.println(s1);
			if(s1.equals("end"))
			System.exit(0);
			System.out.println("Inside Send");
		}
				
			
	
			catch(Exception e)
			{			
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}
	public void run()
	{
		try
		{
			while(true)
	
			{
				System.out.println("run started of "+f.getTitle());
				if(f.getTitle().equals("FINISH"))
				{
					interrupt();// to destroy threads with name finish
				}
				BufferedReader in1=new BufferedReader(new InputStreamReader(s.getInputStream()));
				String sss=in1.readLine();
				System.out.println("ssss value:- "+sss);
				if((sss==null)||(sss.equals(null)))
				{
					sss="aaa";
				}
				
				if(sss.contains("close_chat"))// used in close button of chat class to close private chat window
				{
					String ht[]=sss.split("close_chat");
					boolean r8,f8;
					if(a1.contains(ht[0]))
					r8=a1.removeElement(ht[0]);
					
					if(a1.contains(ht[1]))
					f8=a1.removeElement(ht[1]);
					
					sss="aaa";
				}
				if(sss.equals("already added"))	// to check whether same name exists
				{
					sss="aaa";
				}
				if(sss.equals("new"))// to check whether same name exists
				{
					sss="aaa";
				}
				
				if(sss.contains(":::")) // to maximize the private chat windows
				{
					String u[]=sss.split(":::");
					String y=u[0]+u[1];
					String y1=u[1]+u[0];
					Frame gt[]=JFrame.getFrames();
					for(int i2=0;i2<gt.length;i2++)
					{
						System.out.println(gt[i2].getTitle());
						if(  ( (gt[i2].getTitle()).equals(y))||( (gt[i2].getTitle()).equals(y1)) )
						{
							gt[i2].setVisible(true);
						}
					}
					sss="aaa";
				}

			
				if(sss.contains("a1")) // to add in al vector of the client with whom provate chat is done
				{
					String j[]=sss.split("a1");
					/*if(f.getTitle().equals(j[1]))
					{*/
					if(!(a1.contains(j[0]+j[1])) )
					{
						a1.add(j[0]+j[1]);
					}
					if(!(a1.contains(j[1]+j[0])) )	
					{
						a1.add(j[1]+j[0]);
					}	
					//}	
					sss="aaa";
				}
				
				
					

				
				
				if(sss.contains("%%%"))
				{
					String def[]=sss.split("%%%");
					
					System.out.println("def value:- "+def[1]);
					
					String def1[]=def[1].split("###");
			
					String qq=def1[0]+" :"+def1[1];
					System.out.println("qqq value:- "+qq);

					int index=listModel.indexOf(qq);
					System.out.println("index is:- "+index);		
					if(index!=-1)
					listModel.removeElementAt(index);		
					
						Frame f12[]=JFrame.getFrames();
						for(int i=0;i<f12.length;i++)
						{
							System.out.println("name      "+f12[i].getTitle());	
							if( (f12[i].getTitle()).contains(def1[0]))
							{
								f12[i].setTitle("null");
								f12[i].dispose();
							}
						}
					
					sss="aaa";
				}
				if(sss.contains("###")) // ### for jlist
				{
				 	String x[]=sss.split("###");	
					System.out.println("\n.......printing x values......");
					for(int i=0;i<x.length;i++)
					{
						System.out.println(x[i]);
					}
					
					if(x[0].equals(f.getTitle()))
					{
						System.out.println("no addition in jlist");
					}
					else
					{
						if(!(listModel.contains(x[0]+" :"+x[1])))
						listModel.addElement(x[0]+" :"+x[1]);
					}
					
					
				}
				else if(!(sss.contains("!!!")))
				{
					if(sss.equals("end"))
					{
						System.exit(0);	
					}
					if(! (sss.equals("aaa") ) )
					{
						String d[]=sss.split("~~~"); // for global chat	
						if(d[0].equals(f.getTitle()))
						t.setText(t.getText()+"\n"+"Me"+" >"+d[1]+"\n");
						else
						t.setText(t.getText()+"\n"+d[0]+" >"+d[1]+"\n");
					}
				}

			
			
			}
		}
		catch(Exception e2)
		{			
			System.out.println("error in run of client thread:- "+e2.getMessage());
			e2.printStackTrace();
		}
	}
	public static void main(String args[])
	{
		client c=new client();
	}
}

		