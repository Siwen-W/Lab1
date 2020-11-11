package lab5;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class MyGUI extends JFrame
{
	private static final long serialVersionUID = -3021357346697227441L;
	private JTextField input_field=new JTextField();
	JScrollPane show_all_input=new JScrollPane(input_field);
	private JTextArea result_field=new JTextArea("Please input the protein name of PDB"
			+" or protein sequence in the box above."+"\n"+
			"If the input is protein name, please click on \'Search by Name\'."+"\n"
			+"If the input is protein sequence, please click on \'Search by Sequence\'.");
	private JTextPane picture_field=new JTextPane();
	private String a1="";
	private String c="";
	JButton name=new JButton("Search by Name");
	JButton picture=new JButton("Picture result");
	JButton sequence=new JButton("Search by Sequence");
	Map<String,String> database=getDatabase();
	public MyGUI(String title)
	{
		super(title);
		setLocation(350,150);
		setSize(800,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(show_all_input,BorderLayout.NORTH);
		getContentPane().add(result_field,BorderLayout.CENTER);
		setJMenuBar(getMyMenuBar());
		getContentPane().add(getBottomPanel(),BorderLayout.SOUTH);
		setVisible(true);
	}
	
	private void updatefield() 
	{
		result_field.setText(c);
		result_field.setLineWrap(true);
		result_field.setWrapStyleWord(true);
		validate();
	}
	
	private void updatepicture()
	{
		getContentPane().add(picture_field,BorderLayout.CENTER);
		picture_field.setText("");
		try 
		{
			ImageIcon icon=new ImageIcon(a1);
			picture_field.insertIcon(icon);
			picture_field.requestFocus();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		validate();	
	}
	
	private JPanel getBottomPanel()
	{
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(0,4));
		panel.add(name);
		panel.add(picture);
		panel.add(sequence);
		picture.setEnabled(false);
		name.addActionListener(
				new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e)
					{
						picture.setEnabled(true);
						String a=input_field.getText();
						String b=database.get(a);
						if(b!=null) 
						{
							a1="/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/lab5/"+a+".jpeg";
							c="Results:"+"\n"+"Name in PDB: "+a+"\n"+"Sequence: "+b;
						}
						else
						{
							a1="";
							c="Results:"+"\n"+"The name "+a+" is not in PDB.";
						}
						updatefield();
					}
				}
		);
		picture.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				updatepicture();
			}
		});
		return panel;
	}
	
	private static Map<String,String> getDatabase() 
	{
		Map<String,String> map=new HashMap<String,String>();
		try
		{
			BufferedReader r=new BufferedReader(new FileReader(new File("/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/lab5/protein.fa")));
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
		save_picture.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				savepicture();
			}
		});
		save.add(save_picture);
		menubar.add(save);		
		return menubar;
	}
	
	private void savepicture()
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
			FileInputStream in=new FileInputStream(new File(a1).getAbsolutePath());
			FileOutputStream out=new FileOutputStream(chosenfile);
			byte[] read=new byte[1024];
			int len=0;
			while((len=in.read(read))!=-1)
			{
				out.write(read,0,len);
			}
			in.close();
			out.flush();
			out.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this,ex.getMessage(),"Could not write file",JOptionPane.ERROR_MESSAGE);
		}
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
