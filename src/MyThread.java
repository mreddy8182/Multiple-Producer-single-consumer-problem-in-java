import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//producer 

class MyThread extends Thread{
	
	ArrayList<String> task = new ArrayList<String>();
	ArrayList<String> sharedArr = new ArrayList<String>();
	String threadname = new String();
	String temp;
	static int index = 0;
	int counter = 0;
	static public ArrayList<String> thread_data = new ArrayList<String>();
	int no_of_bags;
	static int check = 0;
	static int total_counter = 0;
	int b;
	
	
	MyThread(String threadname , ArrayList<String> task , ArrayList<String> sharedArr, int no_of_bags )
	{
		this.threadname = threadname;
		this.task = task;
		this.sharedArr = sharedArr;
		this.no_of_bags = no_of_bags;
	}

	
boolean palindrome(String temp, ArrayList<String> task )
	{
		StringBuilder a = new StringBuilder(temp);
		a = a.reverse();
		if(task.contains(a.toString()))
		{
				b = task.indexOf(a.toString());
				return true;
		}
		
		for(int i  = 0 ;  i < temp.length()/2 ; i++)
		{
			if(temp.charAt(i) != temp.charAt(temp.length()-1-i))
			{
				
				return false;
			}
			
		}
		
		
		return true;
	}
	









	
	void producer() throws InterruptedException
	{
					
		for(int i = 0 ; i < task.size() ;i++)
		{
			temp = task.get(0);
			task.remove(0);
			if(palindrome(temp , task) == true)
			{
				synchronized(sharedArr)
				{
					sharedArr.add(temp);
					counter++;
					total_counter++;
					if(b != 0)
						{
							String a = task.get(b).toString();
							sharedArr.add(a);
							counter++;
							total_counter++;
							b = 0;
						}
					
					sharedArr.notifyAll();
				
				}
			}
		}
	}
	
	//Run method for the thread
	public void run()
	{

		try {
			while(task.size() != 0)
				producer();
			
			if(task.size() == 0)
			{synchronized(sharedArr)
				{
				sharedArr.add(threadname + counter);
				sharedArr.notifyAll();
				}
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	}
	
