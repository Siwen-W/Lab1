package lab4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FastaSequence 
{
	private String s1;
	
	public FastaSequence(String s1)
	{
		this.s1=s1;
	}
	
	public String getHeader()
	{
		int a=s1.indexOf('>');
		int b=s1.indexOf(' ');
		return s1.substring(a+1,b);
	}
	
	public String getSequence() 
	{
		int a=s1.indexOf(' ');
		return s1.substring(a+1,s1.length());
	}
	
	public float getGCRatio()
	{
		int a=s1.indexOf(' ');
		String b=s1.substring(a+1,s1.length());
		float i=b.length();
		float numC=b.chars().filter(ch->ch=='C').count();
		float numG=b.chars().filter(ch->ch=='G').count();
		return (numC+numG)/i;
	}
	
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception 
	{
		BufferedReader r=new BufferedReader(new FileReader(new File(filepath)));
		List<FastaSequence> l=new ArrayList<FastaSequence>();
		String s="";
		for(String nextline=r.readLine();nextline!=null;nextline=r.readLine())
		{
			if(nextline.charAt(0)!='>')
			{
				nextline=nextline.replace("\n","");
				s=s+nextline;
			}
			else
			{
				if(!s.equals(""))
				{
					l.add(new FastaSequence(s));
				}
				s="";
				s=s+nextline+" ";
			}
		}
		l.add(new FastaSequence(s));
		r.close();
		return l;
	}
	
	public static void writeUnique(File inFile,File outFile) throws Exception
	{
		BufferedReader r=new BufferedReader(new FileReader(inFile));
		Map<String,Integer> map=new HashMap<String,Integer>();
		List<String> l=new ArrayList<>(); 
		for(String nextline=r.readLine();nextline!=null;nextline=r.readLine())
		{
			if(nextline.charAt(0)!='>')
			{
				nextline=nextline.replace("\n","");
				Integer count=map.get(nextline);
				if(count==null)
				{
					count=0;
				}
				count++;
				map.put(nextline,count);
			}
		}
		r.close();
		BufferedWriter writer=new BufferedWriter(new FileWriter(outFile));
		for(String x:map.keySet())
		{
			Integer y=map.get(x);
			String a=Integer.toString(y)+" "+x;
			l.add(a);
		}
		Collections.sort(l);
		for(String i:l)
		{
			int b=i.indexOf(' ');
			String number=i.substring(0, b);
			String sequence=i.substring(b+1, i.length());
			writer.write(">"+number+"\n"+sequence+"\n");
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
		
		File x=new File("/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/lab4/input.fasta");
		File y=new File("/Users/siwenwu/Documents/2020_fall/programming_3/javacode/Lab/src/lab4/output.txt");
		writeUnique(x,y);
		
	}
}
