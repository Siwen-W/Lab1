package lab1;

import java.util.Random;

public class Question1 
{

	public static void main(String[] args) 
	{
		Random random=new Random();
		float n=0;
		for(int i=0;i<1000;i++)
		{
			String s="";
			for(int j=0;j<3;j++)
			{
				int r=random.nextInt(4);
				if(r==0)
				{
					s+="A";
				}
				else if(r==1)
				{
					s+="C";
				}
				else if(r==2)
				{
					s+="G";
				}
				else
				{
					s+="T";
				}
			}
			if(s.equals("AAA"))
			{
				n++;
			}
			System.out.println(s);	
		}
		float m=n/1000;
		System.out.printf("Frequency of 'AAA' from java: %f \n",m);
		System.out.printf("Frequency of 'AAA' I expected: %f \n", 0.25*0.25*0.25);
		System.out.println("The number from java is close to the number I expected");
	}

}
