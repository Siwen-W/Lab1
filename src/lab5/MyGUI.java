package lab5;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class MyGUI extends JFrame
{
	private static final long serialVersionUID = -3021357346697227441L;
	private JTextArea afield=new JTextArea();
	private String c="";
	public MyGUI(String title)
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
		JButton name=new JButton("search by name");
		JButton sequence=new JButton("search by sequence");
		panel.add(name);
		panel.add(sequence);
		name.addActionListener(
				new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						String a=afield.getText();
						Map<String,String> database=getDatabase();
						String b=database.get(a);
						if(b!=null) 
						{
							c="Results:"+"\n"+"Name in PDB: "+a+"\n"+"Sequence: "+b;
						}
						else
						{
							c="Results:"+"\n"+"The name "+a+" is not in PDB.";
						}
						updatefield();
					}
				}
		);
		return panel;
	}
	
	private static Map<String,String> getDatabase() 
	{
		Map<String,String> map=new HashMap<String,String>();
		try
		{
			BufferedReader r=new BufferedReader(new FileReader(new File("/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/lab5/test.txt")));
			String header="";
			for(String nextline=r.readLine();nextline!=null;nextline=r.readLine())
			{
				nextline=nextline.replace("\n","");
				if(!nextline.equals(""))
				{
					nextline=nextline.replace("\n","");
					if(nextline.charAt(0)=='>')
					{
						header=nextline.substring(1, nextline.length());
					}
					else
					{
						map.put(header,nextline);
					}
				}	
			}
			r.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return map;
	}
	
	private JMenuBar getMyMenuBar()
	{
		JMenuBar menubar=new JMenuBar();
		JMenu save=new JMenu("save");
		JMenuItem save_text=new JMenuItem("Save the text results");
		save.add(save_text);
		save_text.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				savestate();
			}
		});
		JMenuItem save_picture=new JMenuItem("Save the picture result");
		save.add(save_picture);
		menubar.add(save);
		JMenu information=new JMenu("Program information");
		menubar.add(information);		
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
			writer.write(c);
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
		new MyGUI("Protein structure prediction");
	}
}
