package lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Fasta_stats 
{

	public static void main(String[] args) throws Exception
	{
		BufferedReader reader=new BufferedReader(new FileReader(new File("test.fasta")));
		String sequence="";
		List<String> name=new ArrayList<>();
		List<String> seq=new ArrayList<>();
		for(String nextline=reader.readLine();nextline!=null;nextline=reader.readLine())
		{
			if(nextline.charAt(0)!='>')
			{
				nextline=nextline.replace("\n","");
				sequence=sequence+nextline;
			}
			else
			{
				String sequenceID=nextline.substring(1,nextline.length());
				name.add(sequenceID);
				if(!sequence.equals(""))
				{
					seq.add(sequence);
				}
				sequence="";
			}
		}
		seq.add(sequence);
		reader.close();
		BufferedWriter writer=new BufferedWriter(new FileWriter(new File("summary.txt")));
		writer.write("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\t"+"\n");
		for(int i=0;i<seq.size();i++)
		{
			String seqid=name.get(i);
			String seqx=seq.get(i);
			long numA=seqx.chars().filter(ch->ch=='A').count();
			long numC=seqx.chars().filter(ch->ch=='C').count();
			long numG=seqx.chars().filter(ch->ch=='G').count();
			long numT=seqx.chars().filter(ch->ch=='T').count();
			writer.write(seqid+"\t"+numA+"\t"+numC+"\t"+numG+"\t"+numT+"\t"+seqx+"\n");
		}
		writer.flush();
		writer.close();
	}
}
