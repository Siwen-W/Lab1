package lab7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Prime_number extends JFrame
{
	private static final long serialVersionUID = 458400600141951672L;
	private JTextField input=new JTextField();
	private JTextArea result=new JTextArea();
	private JScrollPane result1=new JScrollPane(result);
	JButton start=new JButton("Start");
	JButton cancel=new JButton("Cancel");
	JButton ok=new JButton("OK");
	boolean cancelled=false;
	String info="";
	public Prime_number(String title)
	{
		super(title);
		setLocation(200,200);
		setSize(500,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(input,BorderLayout.NORTH);
		getContentPane().add(result1,BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(),BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private void updatefield() 
	{
		result.setText(info);
		validate();
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(0,3));
		panel.add(start);
		panel.add(ok);
		panel.add(cancel);
		ok.setEnabled(false);
		cancel.setEnabled(false);
		start.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				cancelled=false;
				input.setText("");
				info="Please input a large integer in the box above and press \'OK\'"
						+ " to confirm."+"\n";
				updatefield();
				cancel.setEnabled(true);
				ok.setEnabled(true);
				start.setEnabled(false);
			}
		});
		ok.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				ok.setEnabled(false);
				new Thread(new Mythread()).start();
			}
		});
		cancel.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				cancelled=true;
				start.setEnabled(true);
				ok.setEnabled(false);
				cancel.setEnabled(false);
			}
		});
		return panel;
	}
	
	public static int getPrime(int i)
	{
		int m=i/2;
		for(int j=2;j<=m;j++)
		{
			if(i%j==0)
			{
				return 0;
			}
		}
		return i;
	}
	
	private class Mythread implements Runnable
	{
		@Override
		public void run() 
		{
			try
			{
				String s=input.getText();
				int number=Integer.parseInt(s);
				int time=0;
				int number1=0;
				List<Integer> l=new ArrayList<>();
				if(number==0 || number==1)
				{
					info="There is no prime number."+"\n";
					updatefield();
					start.setEnabled(true);
					ok.setEnabled(false);
					cancel.setEnabled(false);	
				}
				else
				{
					int i=2;
					while(i<=number && !cancelled)
					{
						int r=getPrime(i);
						if(r!=0)
						{
							l.add(r);
							number1++;
						}
						info="Found "+number1+" prime numbers in "+time+" seconds."+"\n";
						updatefield();
						i++;
						time++;
						Thread.sleep(1000);
					}
					start.setEnabled(true);
					ok.setEnabled(false);
					cancel.setEnabled(false);
					info="";
					for(int a=0;a<l.size();a++)
					{
						int x=l.get(a);
						info+=x+""+"\n";
					}
					if(cancelled)
					{
						info+="Cancelled!"+"\n"+"Time = "+time+" seconds"+"\n";
						updatefield();
					}
					else
					{
						info+="Found all!"+"\n"+"Time = "+time+" seconds"+"\n";
						updatefield();
					}
				}
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) 
	{
		new Prime_number("Prime number finder");
	}
}