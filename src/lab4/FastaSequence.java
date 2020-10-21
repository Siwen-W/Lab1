package lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FastaSequence 
{
	private String header;
	private StringBuffer s1;
	
	public FastaSequence(String header,StringBuffer s1)
	{
		this.header=header;
		this.s1=s1;
	}
	
	public String getHeader()
	{
		return header;
	}
	
	public StringBuffer getSequence() 
	{
		return s1;
	}
	
	public float getGCRatio()
	{
		float numC=s1.chars().filter(ch->ch=='C').count();
		float numG=s1.chars().filter(ch->ch=='G').count();
		return (numC+numG)/s1.length();
	}
	
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception 
	{
		BufferedReader r=new BufferedReader(new FileReader(new File(filepath)));
		List<FastaSequence> l=new ArrayList<FastaSequence>();
		String header="";
		StringBuffer s=null;
		for(String nextline=r.readLine();nextline!=null;nextline=r.readLine())
		{
			if(!nextline.equals(""))
			{
				nextline=nextline.replace("\n","");
				if(nextline.charAt(0)!='>')
				{
					s.append(nextline);
				}
				else
				{
					if(s!=null)
					{
						l.add(new FastaSequence(header,s));
					}
					header=nextline.substring(1, nextline.length());
					s=new StringBuffer();
				}
			}			
		}
		l.add(new FastaSequence(header,s));
		r.close();
		return l;
	}
	
	public static void writeUnique(File inFile,File outFile) throws Exception
	{
		List<FastaSequence> fa=FastaSequence.readFastaFile(inFile.getAbsolutePath());
		Map<String,Integer> map=new HashMap<String,Integer>(); 
		for(FastaSequence fs:fa)
		{
			StringBuffer s=fs.getSequence();
			Integer count=map.get(s.toString());
			if(count==null)
			{
				count=0;
			}
			count++;
			map.put(s.toString(),count);
		}
		BufferedWriter writer=new BufferedWriter(new FileWriter(outFile));
		List<Integer> l=new ArrayList<Integer>(new HashSet<>(map.values()));
		Collections.sort(l);
		for(int i=0;i<l.size();i++)
		{
			for(String x:map.keySet())
			{
				Integer y=map.get(x);
				if(y==l.get(i))
				{
					writer.write(">"+y+"\n"+x+"\n");
				}
			}
		}
		writer.flush();
		writer.close();
	}

	public static void main(String[] args) throws Exception
	{
		List<FastaSequence> fastaList=FastaSequence.readFastaFile("/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/lab4/test.fasta");
		for(FastaSequence fs:fastaList)
		{
			System.out.println(fs.getHeader());
			System.out.println(fs.getSequence());
			System.out.println(fs.getGCRatio());
		
		}
		
		File x=new File("/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/lab4/sampleFasta.txt");
		File y=new File("/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/lab4/output.txt");
		writeUnique(x,y);
		
	}
}