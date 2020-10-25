package new_lab5;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Amino_acids_game extends JFrame
{
	private static final long serialVersionUID = -2433488564278977279L;
	private JTextArea afield=new JTextArea();
	private String c="";
	ArrayList<String> s1=new ArrayList<String>();
	String whole_AA [] = {"alanine","arginine","asparagine", 
			"aspartic acid","cysteine","glutamine",
			"glutamic acid","glycine","histidine",
			"isoleucine","leucine","lysine", 
			"methionine","phenylalanine","proline", 
			"serine","threonine","tryptophan", 
			"tyrosine", "valine"};
	String short_AA []={ "A","R","N","D","C","Q","E","G","H","I","L", 
			"K","M","F","P","S","T","W","Y","V"};
	public Amino_acids_game(String title)
	{
		super(title);
		setLocation(200,200);
		setSize(500,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(afield,BorderLayout.CENTER);
		setJMenuBar(getMyMenuBar());
		getContentPane().add(getBottomPanel(),BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private void updatefield() 
	{
		afield.setText(c);
		afield.setLineWrap(true);
		afield.setWrapStyleWord(true);
		validate();
	}
	private JPanel getBottomPanel()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(0,2));
		JButton name1=new JButton("Whole name to short name");
		JButton name2=new JButton("Short name to whole name");
		panel.add(name1);
		panel.add(name2);
		name1.addActionListener(
				new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						String a=afield.getText();
						s1.add(a);
						if(Arrays.asList(whole_AA).contains(a)) 
						{
							String b=short_AA[Arrays.binarySearch(whole_AA,a)];
							c="Results:"+"\n"+"Whole name: "+a+"\n"+"Short name: "+b;
						}
						else
						{
							c="Results:"+"\n"+"The name "+a+" is not a whole name of an amino acid.";
						}
						updatefield();
					}
				}
		);
		
		return panel;
	}
	
	private JMenuBar getMyMenuBar()
	{
		JMenuBar menubar=new JMenuBar();
		JMenu save=new JMenu("save");
		JMenuItem save1=new JMenuItem("Save all of the right answers");
		save.add(save1);
		save1.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				savestate();
			}
		});
		menubar.add(save);	
		return menubar;
	}
	
	private void savestate()
	{
		JFileChooser jfc=new JFileChooser();
		if(jfc.showSaveDialog(this)!=JFileChooser.APPROVE_OPTION) 
			return;
		if(jfc.getSelectedFile()==null)
			return;
		File chosenfile=jfc.getSelectedFile();
		if(jfc.getSelectedFile().exists())
		{
			String message="File"+jfc.getSelectedFile().getName()+" exists. Overwrite?";
			if(JOptionPane.showConfirmDialog(this, message)!=JOptionPane.YES_OPTION)
				return;
		}
		try
		{
			BufferedWriter writer=new BufferedWriter(new FileWriter(chosenfile));
			String x="";
			for(String i:s1)
			{
				if(Arrays.asList(whole_AA).contains(i))
					x=short_AA[Arrays.binarySearch(whole_AA,i)];
				if(Arrays.asList(short_AA).contains(i))
					x=whole_AA[Arrays.binarySearch(short_AA,i)];
				if(x!=null)
				{
					writer.write(i+"\t"+x+"\n");
				}
			}			
			writer.flush();
			writer.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this,ex.getMessage(),"Could not write file",JOptionPane.ERROR_MESSAGE);
		}
	}		
	public static void main(String[] args)
	{
		new Amino_acids_game("Amino acid game");
	}
}
