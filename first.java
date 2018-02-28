import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
class first extends JFrame
{
	JLabel l;
	JTextArea name;
	JButton submit;
	public first()
	{
		setSize(900,400);
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.GREEN);
		GridBagConstraints gbc=new GridBagConstraints();
		gbc.gridx=1;
		gbc.gridy=1;		
				
		gbc.gridwidth=1;
		gbc.gridheight=1;
		 	 
		gbc.weightx=1.0;
		gbc.weighty=1.0;

		gbc.ipadx=100;
		gbc.ipady=10;
		
		Font f=new Font("Enter Your name",Font.BOLD,24);
		JLabel ll=new JLabel("***************WELCOME TO Rishabh'S CHAT APPLICATION**************");
		ll.setFont(f);
		ll.setBackground(Color.BLUE);
		add(ll,gbc);
		
		gbc.gridx=1;
		gbc.gridy=2;
		l=new JLabel(" **************ENTER YOUR NAME***************");
		l.setBackground(Color.BLACK);
			
		l.setFont(f);
		add(l,gbc);
		
		gbc.gridy=3;
		name=new JTextArea();
		add(name,gbc);

		gbc.gridy=4;
		submit=new JButton("Submit");
		add(submit,gbc);
		setVisible(true);
	}
}
		

			