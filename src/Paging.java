import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Paging {
	static int i, j, t, flag, flag1 = 0,k=0,ph,pf;
	static int nf, np, fc, pc, lookup, available = -1,  frame_no;
	static int pages[], curr_frame[];
	static Queue<Integer> curr__frame = new LinkedList<>();

	static BufferedReader br;
	public static void main(String[] args) throws IOException
	{
		Scanner sc=new Scanner(System.in);
		int ans;
		
		
		
		
		do {
			System.out.println("Choose Policy For Page Replacement");
			System.out.println("1.FIFO\n2.LRU\n3.OPTIMAL");
			System.out.print("\nEnter your choice:\t");
			int ch=sc.nextInt();
		switch(ch)
		{
		case 1:
		{
			System.out.println("FIFO");
			System.out.println("Enter number of pages:");
			np=sc.nextInt();
			System.out.println("Enter frame size:");
			nf=sc.nextInt();
			//int curr_frame[] = new int[nf];
			int pages[] = new int[np];
			System.out.println("Enter values of pages:");
			for(i = 0; i < np; i++)
			{
				pages[i]=sc.nextInt();
			}
			init();
			fifo(pages);
			break;
		}
		case 2:
		{
			System.out.println("LRU");
			System.out.println("Enter number of pages:");
			np=sc.nextInt();
			System.out.println("Enter frame size:");
			nf=sc.nextInt();
			int curr_frame[] = new int[nf];
			int pages[] = new int[np];
			System.out.println("Enter values of pages:");
			for(i = 0; i < np; i++)
			{
				pages[i]=sc.nextInt();
			}
			initialize(curr_frame);
			lru(curr_frame, pages);
			break;
		}
		case 3:
		{
			System.out.println("OPTIMAL");
			System.out.println("Enter number of pages:");
			np=sc.nextInt();
			System.out.println("Enter frame size:");
			nf=sc.nextInt();
			int curr_frame[] = new int[nf];
			int pages[] = new int[np];
			System.out.println("Enter values of pages:");
			for(i = 0; i < np; i++)
			{
				pages[i]=sc.nextInt();
			}
			initialize(curr_frame);
			opti(curr_frame, pages);
			break;
		}
		default:
			System.out.println("Invalid Choice!!");
		}
		System.out.print("\nDo you want to continue?\n(If 'yes' press 1):\t");
		ans=sc.nextInt();
		}while(ans==1);
		sc.close();
		System.out.println("Thank You!!!!");
	}
	
	//Main end-------------------------------------------------------------------------------------------
	static void init()
	{
		for(i = 0; i < nf; i++)
		{
			curr__frame.add(-1);
		}
	}
	static void initialize(int curr_frame[])
	{
		for(i = 0; i < nf; i++)
		{
			curr_frame[i] = -1;
		}
	}
	
	//Init end------------------------------------------------------------------------------------------
	static void fifo(int pages[])
	{
		fc = 0;
		pc = 0;
		ph=0;
		while(pc != np)
		{
			if(curr__frame.peek() == -1)
			{
				for (Integer Item: curr__frame)
				{
					if(Item == pages[pc])
					{
						flag = 1;
						pc++;
						ph++;
						System.out.println("\nHit");
						fifodis();
						break;
					}
				}
				curr__frame.remove();
				curr__frame.add(pages[pc]);
				pc++;
				fc++;
				fifodis();
			}
			else
			{
				if(fc == nf)
				{
					fc = 0;
				}
				for (Integer Item: curr__frame)
				{
					if(Item == pages[pc])
					{
						flag = 1;
						pc++;
						ph++;
						System.out.println("\nHit");
						fifodis();
						break;
					}
				}
				if(flag == 0)
				{
					curr__frame.remove();
					curr__frame.add(pages[pc]);
					pc++;
					fc++;
					fifodis();
				}
				flag = 0;
			}
		}
		System.out.println("\n\nFOR FIFO:");
		System.out.println("Page hits are:"+ph);
		pf=np-ph;
		System.out.println("Number of page faults are:"+pf);
	}
	
	//fifo end----------------------------------------------------------------------------------
	static void lru(int curr_frame[], int pages[])//
	{
		flag = 0;
		int temp;
		pc = 0;
		fc = 0;
		ph=0;
		t=0;
		while(pc != np)
		{
			while(fc != nf)
			{
				if(curr_frame[fc] == -1)
				{
					for(i = 0; i < nf; i++)//check for repeatation
					{
						if(curr_frame[i] == pages[pc])
						{
							j = i;
							k = i;
							flag = 1;
							for(j = k+1; j < fc; j++, k++)//take repeated element to last index of array to make it MRU
							{
								temp = curr_frame[k];
								curr_frame[k] = curr_frame[j];
								curr_frame[j] = temp;
							}
							ph++;
							System.out.println("\nHit");
							lrudis(curr_frame);
							pc++;
							break;
						}
					}
					curr_frame[fc] = pages[pc];
					fc++;
					pc++;
					lrudis(curr_frame);
				}
			}
			for(i = 0; i < nf; i++)//check for repeatation
			{
				if(curr_frame[i] == pages[pc])
				{
					j = i;
					k = i;
					flag = 1;
					for(j = k+1; j < nf; j++, k++)//take repeated element to last index of array to make it MRU
					{
						temp = curr_frame[k];
						curr_frame[k] = curr_frame[j];
						curr_frame[j] = temp;
					}
					ph++;
					System.out.println("\nHit");
					lrudis(curr_frame);
					pc++;
				}
			}
			if(flag == 0)//remove curr_frame[0] and shift remaining elements then add upcoming page at last index
			{
				for(i = 0; i < nf-1; i++)
				{
					curr_frame[i] = curr_frame[i+1];
				}
				curr_frame[nf-1] = pages[pc];
				lrudis(curr_frame);
				pc++;
			}
			flag = 0;
		}
		System.out.println("\n\nFOR LRU:");
		System.out.println("Page hits are:"+ph);
		pf=np-ph;
		System.out.println("Number of page faults are:"+pf);
	}
	//lru end----------------------------------------------------------------------------------------
	static void opti(int curr_frame[], int pages[])
	{
		fc = 0; pc = 0; flag = 0;
		ph=0;
		t=0;
		while(pc != np)
		{
			while(fc != nf)
			{
				if(curr_frame[fc] == -1)
				{
					for(i = 0; i < fc; i++)
					{
						if(curr_frame[i] == pages[pc])
						{
							flag = 1;
							pc++;
							ph++;
							System.out.println("\nhit");
							optidis(curr_frame);
							break;
						}
					}
					curr_frame[fc] = pages[pc];
					fc++;
					pc++;
					optidis(curr_frame);
				}
			}
			for(i = 0; i < nf; i++)
			{
				if(curr_frame[i] == pages[pc])
				{
					flag = 1;
					pc++;
					ph++;
					System.out.println("\nhit");
					optidis(curr_frame);
					break;
				}
			}
			if(flag == 0)
			{
				int distance = 0;
				for(i = 0; i < nf; i++)
				{
					for(j = pc+1; j < np; j++)
					{
						flag1=0;
						if(curr_frame[i] == pages[j])
						{
							
							int d1=j-pc;
							if(distance < d1)
							{
								distance = j-pc;
								//System.out.println("di"+distance);
								frame_no = i;
								flag1 = 1;
								break;
							}
						}
						
					}
					//System.out.println("f"+i+":"+flag1);
					if(flag1 == 0)
					{
						available = i;
						break;
					}
				}
				if(available != -1)
				{
					curr_frame[available] = pages[pc];
					pc++;
					optidis(curr_frame);
				}
				else
				{
					curr_frame[frame_no] = pages[pc];
					pc++;
					optidis(curr_frame);
				}
			}
			flag = 0;
		}
		System.out.println("\n\nFOR OPTIMAL:");
		System.out.println("Page hits are:"+ph);
		pf=np-ph;
		System.out.println("Number of page faults are:"+pf);
	}
	//opti end-----------------------------------------------------------------------------------
	static void fifodis()
	{
		if(j + 1 == np)
		{
			System.out.print("\nFinal Iteration "+(j+1)+":\t");
		}
		else
		{
			System.out.print("\nIteration "+(j+1)+":\t");
		}
		//System.out.println("Current frame contents are:");
		for (Integer Item: curr__frame)
		{
			System.out.print(Item+" ");
		}
		j++;

	}
	
	static void lrudis(int curr_frame[])
	{
		
		if(t + 1 == np)
		{
			System.out.print("\nFinal Iteration "+(t+1)+":\t");
		}
		else
		{
			System.out.print("\nIteration "+(t+1)+":\t");
		}
		//System.out.println("Current frame contents are:");
		for(i = 0; i < nf; i++)
		{
			System.out.print(curr_frame[i]+" ");
		}
		//System.out.print("\n");
		t++;
	}
	
	static void optidis(int curr_frame[])
	{
		
		if(t + 1 == np)
		{
			System.out.print("\nFinal Iteration "+(t+1)+":\t");
		}
		else
		{
			System.out.print("\nIteration "+(t+1)+":\t");
		}
		//System.out.println("Current frame contents are:");
		for(i = 0; i < nf; i++)
		{
			System.out.print(curr_frame[i]+" ");
		}
		//System.out.print("\n");
		t++;
	}

	

}
