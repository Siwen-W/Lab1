package siwen_final_project;

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
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
	private JScrollPane show_all_result=new JScrollPane(result_field);
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
		getContentPane().add(show_all_result,BorderLayout.CENTER);
		setJMenuBar(getMyMenuBar());
		getContentPane().add(getBottomPanel(),BorderLayout.SOUTH);
		show_all_input.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		show_all_result.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		setVisible(true);
	}
	
	private void updatefield() 
	{
		result_field.setText(c);
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
						sequence.setEnabled(false);
						String a=input_field.getText();
						String b=database.get(a);
						if(b!=null) 
						{
							a1="/Users/siwenwu/Documents/2020_fall/programming_3/javacode"
								+"/Lab/src/siwen_final_project/picture_rename/"+a+".jpeg";
							c="Results:"+"\n"+"Name in PDB: "+a+"\n"+"Sequence: "+b;
						}
						else
						{
							a1="";
							c="Results:"+"\n"+"The name "+a+" is not in PDB.";
						}
						updatefield();
						sequence.setEnabled(true);
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
		sequence.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				name.setEnabled(false);
				picture.setEnabled(false);
				String a=input_field.getText();
				try
				{
					BufferedWriter w=new BufferedWriter(new FileWriter("/Users/siwenwu/"
							+ "Documents/2020_fall/programming_3/javacode/Lab/src/"
							+"siwen_final_project/query.fa"));
					w.write(">"+"input_query"+"\n"+a+"\n");
					w.flush();
					w.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				String[] blast_a1=new String[7];
				blast_a1[0]="/usr/local/ncbi/blast/bin/makeblastdb";
				blast_a1[1]="-in";
				blast_a1[2]="/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/"
						+"siwen_final_project/protein.fa";
				blast_a1[3]="-dbtype"; 
				blast_a1[4]="prot";
				blast_a1[5]="-out";
				blast_a1[6]="PDB";
				try
				{
					new Blastp(blast_a1);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				blast_a1=new String[11];
				blast_a1[0]="/usr/local/ncbi/blast/bin/blastp";
				blast_a1[1]="-query";
				blast_a1[2]="/Users/siwenwu/Documents/2020_fall/programming_3"
						+"/javacode/Lab/src/siwen_final_project/query.fa";
				blast_a1[3]="-out";
				blast_a1[4]="/Users/siwenwu/Downloads/result.txt";
				blast_a1[5]="-db";
				blast_a1[6]="PDB";
				blast_a1[7]="-outfmt";
				blast_a1[8]="6";
				blast_a1[9]="-max_target_seqs";
				blast_a1[10]="1";
				try
				{
					new Blastp(blast_a1);
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				try
				{
					BufferedReader r1=new BufferedReader(new FileReader(new File("/Users/siwenwu/Downloads/result.txt")));
					c="Input query: "+a+"\n";
					c+="Result from blastp:"+"\n";
					c+="qseqid"+"\t"+"sseqid"+"\t"+"pident"+"\t"+"length"+"\t"+"mismatch"+"\t"
					+"gapopen"+"\t"+"qstart"+"\t"+"qend"+"\t"+"sstart"+"\t"+"send"+"\t"+"evalue"
					+"\t"+"bitscore"+"\n";
					String c1=c;
					for(String nextline=r1.readLine();nextline!=null;nextline=r1.readLine())
					{
						nextline=nextline.replace("\n","");
						if(nextline!="")
						{
							c+=nextline+"\n";
							a1="/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab"
									+"/src/siwen_final_project/picture_rename/"+nextline.split("\t")[1]+".jpeg";
						}
					}	
					if(c1==c)
					{
						c+="Empty results from blastp."+"\n";
					}
					r1.close();
					updatefield();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				picture.setEnabled(true);
				name.setEnabled(true);
			}
		});
		return panel;
	}
	
	private class Blastp
	{
		public Blastp(String[] cmdArgs) throws Exception
		{
			Runtime r=Runtime.getRuntime();
			Process p=r.exec(cmdArgs);
			p.waitFor();
			p.destroy();
		}
	}
	
	private static Map<String,String> getDatabase() 
	{
		Map<String,String> map=new LinkedHashMap<>();
		try
		{
			BufferedReader r=new BufferedReader(new FileReader(new File("/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/siwen_final_project/protein.fa")));
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
