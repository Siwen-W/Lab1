package lab2;

import java.util.Random;

public class Amino_acide 
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
		int score=0;
		long start_time=System.currentTimeMillis()/1000;
		while(true)
		{
			Random random=new Random();
			int i=random.nextInt(20);
			System.out.println(whole_AA[i]);
			String aString = System.console().readLine().toUpperCase();
			long current_time=System.currentTimeMillis()/1000;
			long time=current_time-start_time;
			if(time<30)
			{
				if(aString.equals(short_AA[i]))
				{
					score++;
					System.out.println("right. "+"Score="+score+" ; "+"seconds="+time+" out of 30");
				}
				else
				{
					System.out.println("WRONG "+"shoule be "+short_AA[i]);
					System.out.println("Test ends with score of "+score);
					System.exit(0);
				}
			}
			else
			{
				System.out.println("Time out.");
				System.out.println("Test ends with score of "+score);
				System.exit(0);
			}		
		}
	}
}
