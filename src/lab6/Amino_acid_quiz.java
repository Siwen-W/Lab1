package lab6;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Amino_acid_quiz extends JFrame
{
	private static final long serialVersionUID = 97594027530354791L;
	private JTextField afield=new JTextField();
	private JTextArea bfield=new JTextArea();
	JButton start=new JButton("Start quiz");
	JButton cancel=new JButton("Cancel");
	int right=0;
	int wrong=0;
	boolean canceled=false;
	String whole_AA [] = {"alanine","arginine","asparagine", 
			"aspartic acid","cysteine","glutamine",
			"glutamic acid","glycine","histidine",
			"isoleucine","leucine","lysine", 
			"methionine","phenylalanine","proline", 
			"serine","threonine","tryptophan", 
			"tyrosine", "valine"};
	String short_AA []={ "A","R","N","D","C","Q","E","G","H","I","L", 
			"K","M","F","P","S","T","W","Y","V"};
	String whole="";
	int j=0;
	public Amino_acid_quiz(String title)
	{
		super(title);
		setLocation(200,200);
		setSize(500,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(afield,BorderLayout.NORTH);
		getContentPane().add(bfield,BorderLayout.CENTER);
		getContentPane().add(getBottomPanel(),BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private void updatefield() 
	{
		bfield.setText(whole);
		validate();
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(start);
		panel.add(cancel);
		cancel.setEnabled(false);
		start.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				canceled=false;
				j=getAA();
				afield.setText("");
				whole="Please input the short name of \'"+whole_AA[j]+"\' in the box above."+"\n"
						+"\n"+"Results:"+"\n"+"Right number: "+right+"\n"+"Wrong number: "+wrong
						+"\n";
				new Thread(new Mythread()).start();
				cancel.setEnabled(true);
				start.setEnabled(false);
			}
		});		
		cancel.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				canceled=true;
				whole="You have cancelled this process! Please click on \'Start quiz\' to start again.";
				updatefield();
			}
		});
		return panel;
	}
	
	public static int getAA()
	{
		Random random=new Random();
		int i=random.nextInt(20);
		return i;
	}
	
	private class Mythread implements Runnable
	{
		@Override
		public void run() 
		{
			try
			{
				int time=30;
				while(time>=0 && !canceled)
				{
					bfield.setText(whole+"Time left: "+time+"s");
					validate();
					String s=afield.getText().toUpperCase();
					if(!s.equals(""))
					{
						if(s.equals(short_AA[j]))
						{
							right++;
						}
						else
						{
							wrong++;
						}
						j=getAA();
						whole="Please input the short name of \'"+whole_AA[j]+"\' in the box above."+"\n"
								+"\n"+"Results:"+"\n"+"Right number: "+right+"\n"+"Wrong number: "+wrong
								+"\n";
						afield.setText("");
					}
					time--;
					Thread.sleep(1000);
				}
				if(time<=0)
				{
					whole="Time out! Please click on \'Start quiz\' to start again."
							+"\n"+"\n"+"Results:"+"\n"+"Right number: "+right+"\n"+"Wrong number: "+wrong;	
					updatefield();
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			right=0;
			wrong=0;
			start.setEnabled(true);
			cancel.setEnabled(false);
		}
		
	}
	
	public static void main(String[] args) 
	{
		new Amino_acid_quiz("Amino acid quiz");
	}
	
}