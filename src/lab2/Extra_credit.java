package lab2;

import java.util.*;

public class Extra_credit 
{
	public static void main(String[] args) 
	{
		String whole_AA [] = {"alanine","arginine","asparagine", 
				"aspartic acid","cysteine","glutamine",
				"glutamic acid","glycine","histidine",
				"isoleucine","leucine","lysine", 
				"methionine","phenylalanine","proline", 
				"serine","threonine","tryptophan", 
				"tyrosine", "valine"};
		String short_AA []={ "A","R","N","D","C","Q","E","G","H","I","L", 
							"K","M","F","P","S","T","W","Y","V"};
		System.out.println("Please input the seconds:");
		Scanner m=new Scanner(System.in);
		int seconds = m.nextInt();
		System.out.println("Please input the number of questions:");
		int questions = m.nextInt();
		System.out.println("You could also quit by typing 'quit'.");
		int number=0;
		List<String> name=new ArrayList<>();
		List<String> right=new ArrayList<>();
		List<String> wrong=new ArrayList<>();
		List<String> no_repeat=new ArrayList<>();
		long start_time=System.currentTimeMillis()/1000;
		while(true)
		{
			Random random=new Random();
			int i=random.nextInt(20);
			System.out.println(whole_AA[i]);
			String aString=m.next();
			long current_time=System.currentTimeMillis()/1000;
			long time=current_time-start_time;
			number++;
			if(aString.equals("quit"))
			{
				System.out.println("quit.");
				System.out.println("Summary:");
				if(name.size()>0)
				{
					for(int a=0;a<name.size();a++)
					{
						String aa_name=name.get(a);
						if(!no_repeat.contains(aa_name))
						{
							no_repeat.add(aa_name);
							int r=Collections.frequency(right,aa_name);
							int w=Collections.frequency(wrong,aa_name);
							System.out.println(aa_name+":"+"right times:"+r+",wrong times:"+w);
						}				
					}
				}
				else
				{
					System.out.println(0);
				}
				m.close();
				System.exit(0);
			}
			if(time>seconds)
			{
				System.out.println("Time out.");
				System.out.println("Summary:");
				if(name.size()>0)
				{
					for(int a=0;a<name.size();a++)
					{
						String aa_name=name.get(a);
						if(!no_repeat.contains(aa_name))
						{
							no_repeat.add(aa_name);
							int r=Collections.frequency(right,aa_name);
							int w=Collections.frequency(wrong,aa_name);
							System.out.println(aa_name+":"+"right times:"+r+",wrong times:"+w);
						}			
					}
				}
				else
				{
					System.out.println(0);
				}
				m.close();
				System.exit(0);
			}
			else
			{
				name.add(whole_AA[i]);
				if(aString.equalsIgnoreCase(short_AA[i]))
				{
					right.add(whole_AA[i]);
					System.out.println("right. "+"seconds="+time+" out of "+seconds);
				}
				else
		
				{
					wrong.add(whole_AA[i]);
					System.out.println("WRONG "+"shoule be "+short_AA[i]);
				}
			}
			if(number>=questions)
			{
				System.out.println("reach the question number.");
				System.out.println("Summary:");
				if(name.size()>0)
				{
					for(int a=0;a<name.size();a++)
					{
						String aa_name=name.get(a);
						if(!no_repeat.contains(aa_name))
						{
							no_repeat.add(aa_name);
							int r=Collections.frequency(right,aa_name);
							int w=Collections.frequency(wrong,aa_name);
							System.out.println(aa_name+":"+"right times:"+r+",wrong times:"+w);
						}			
					}
				}
				else
				{
					System.out.println(0);
				}
				m.close();
				System.exit(0);
			}
		}
		
	}
}
