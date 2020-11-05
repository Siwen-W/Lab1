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
	long start_time=0;
	long time=0;
	int right=0;
	int wrong=0;
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
		bfield.setLineWrap(true);
		bfield.setWrapStyleWord(true);
		validate();
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(0,3));
		JButton start=new JButton("Start quiz");
		JButton enter=new JButton("Enter");
		JButton cancel=new JButton("Cancel");
		panel.add(start);
		panel.add(enter);
		panel.add(cancel);
		start.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				right=0;
				wrong=0;
				time=0;
				j=getAA();
				whole="Please input the short name of \'"+whole_AA[j]+"\' in the box above."+"\n"
						+"\n"+"Results:"+"\n"+"Right number: "+right+"\n"+"Wrong number: "+wrong
						+"\n"+"Time left: "+(30-time)+"s";
				enter.setEnabled(true);
				cancel.setEnabled(true);
				updatefield();
				start_time=System.currentTimeMillis()/1000;
			}
		});
		enter.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				long current_time=System.currentTimeMillis()/1000;
				time=current_time-start_time;
				if(time<=30)
				{
					String s=afield.getText().toUpperCase();
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
							+"\n"+"Time left: "+(30-time)+"s";
					updatefield();
				}
				else
				{
					whole="Time out! Please click on \'Start quiz\' to start again."
							+"\n"+"\n"+"Results:"+"\n"+"Right number: "+right+"\n"+"Wrong number: "+wrong;
					enter.setEnabled(false);
					cancel.setEnabled(false);
					updatefield();
				}	
			}
		});
		cancel.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				right=0;
				wrong=0;
				time=0;
				whole="You have cancelled this process! Please click on \'Start quiz\' to start again.";
				enter.setEnabled(false);
				cancel.setEnabled(false);
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
	
	public static void main(String[] args) 
	{
		new Amino_acid_quiz("Amino acid quiz");
	}
}
