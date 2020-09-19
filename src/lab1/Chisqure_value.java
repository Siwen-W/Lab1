package lab1;

import java.util.Random;

public class Chisqure_value 
{

	public static void main(String[] args)
	{
		double exp_A=10000*3*0.12;
		double exp_C=10000*3*0.38;
		double exp_G=10000*3*0.39;
		double exp_T=10000*3*0.11;
		Random random=new Random();
		for(int x=0;x<10000;x++)
		{
			double act_A=0;
			double act_C=0;
			double act_G=0;
			double act_T=0;
			for(int i=0;i<10000;i++)
			{
				for(int j=0;j<3;j++)
				{
					float f=random.nextFloat();
					if(f<=0.12)
					{
						act_A++;
					}
					else if(f>0.12 && f<=0.50)
					{
						act_C++;
					}
					else if(f>0.50 && f<=0.89)
					{
						act_G++;
					}
					else
					{
						act_T++;
					}
				}
			}
			double chi_value=Math.pow((act_A-exp_A),2)/exp_A+Math.pow((act_C-exp_C),2)/exp_C+Math.pow((act_G-exp_G),2)/exp_G+Math.pow((act_T-exp_T),2)/exp_T;
			System.out.println(chi_value);
		}
		
	}
}
	
