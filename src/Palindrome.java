import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Palindrome {
	
	//This index is used to get the arraylist from the hashmap
	static int index = 0;
	//here we store the all words
	static ArrayList<String> dictionary = new ArrayList<String>();
	//we read the file during loadtime
	static
	{
		String Filename = "D:\\Semester 9\\Java examples\\Assignment 2\\Words.txt";
		try(BufferedReader br = Files.newBufferedReader(Paths.get(Filename)))
		{
		 	dictionary = (ArrayList<String>) br.lines().collect(Collectors.toList());   
		}catch (IOException e)
		{
			e.printStackTrace();	
		}
	}
		
@SuppressWarnings("static-access")
public static void main(String[] args)
		throws InterruptedException, FileNotFoundException, IOException {
		//used for the naming of the threads
		int check = 0;
		//This contains the arraylist as value and length as key
		HashMap<String, ArrayList<String>>  mapBagOfTasks  = new HashMap<String, ArrayList<String>>();
	
		//I am using the length to get the total no of words with different lengths
		ArrayList<Integer> length = new ArrayList<Integer>();
		
		//Get the no of workers
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the no of worker threads you want to create");
		int no_of_workers;
		double input = scan.nextDouble();
		
		//I am using this to empty the result file if it already is not empty
		PrintWriter w = new PrintWriter
				("D:\\Semester 9\\Java examples\\Assignment 2\\Output.txt");
		w.print("");
		w.close();
		
		//I am using this to empty the result file if it already is not empty
		PrintWriter w2 = new PrintWriter
				("D:\\Semester 9\\Java examples\\Assignment 2\\Finalresult.txt");
		w2.print("");
		w2.close();
		
		while(input <1)
		{
			System.out.println("Enter the no of worker threads again");
			input = scan.nextDouble();
			
			
		}
			
		no_of_workers = (int)input;
		
		
		//no of bags is used to get the total no of bags and each bag has words
		//with same length
		int no_of_bags = 0;
		
		//This is the sharedarr between producer and consumer
		ArrayList<String> sharedArr = new ArrayList<String>();
		
		//In this loop get the total no of bags and also get
		//the no of words which have same length
		for(int i = 0 ; i < dictionary.size() ; i++)
		{
			if(!length.contains(dictionary.get(i).length()))
			{
				length.add(dictionary.get(i).length());
				no_of_bags++;
			}
		
		}
		
		//I have extended arrlist into the stringarrylist so that
		//I can make arrays of generic types
		ArrayList<String>[] arrbag = new StringArrayList [no_of_bags]; 
		for(int i = 0 ; i < no_of_bags ; i++)
		{
			arrbag[i] = new StringArrayList();
			for(int j = 0 ;j < dictionary.size() ; j++)
			{
				if(dictionary.get(j).length() == length.get(i))
				{
					arrbag[i].add(dictionary.get(j));
					
				}
			}
		}
		
		//In this loop I put the value and key in the hashmap
		for(int i = 0 ; i < no_of_bags ; i++)
		{
			mapBagOfTasks.put(length.get(i).toString(), arrbag[i]);
		}
	
		//make worker threads
		ExecutorService executor = Executors.newFixedThreadPool(no_of_workers);
		
		//make writer thread and give it consumer
		ExecutorService writer = Executors.newFixedThreadPool(2);
		Runnable runnable_w = new Consumer("Thread writer" , sharedArr , no_of_bags); 
		writer.execute(runnable_w);
		
		//In this loop I am giving each worker that is
		//specifed by the user a task and after that worker
		//thread has completed its task I give it another task
		for(int i = 0 ; i < no_of_bags ; i++)
		{
			if(mapBagOfTasks.containsKey(length.get(0).toString()))
			{
				ArrayList<String> task =	new ArrayList<String>(mapBagOfTasks.get(length.get(index).toString()));
				//this index controls the bag given to task
				index++;
				//this check controls the naming of worker thread
				if(check == no_of_workers) 
				{
					check = 0;
				}
				//make runnable obj and give it Mythread producer adn execute
				Runnable runnable = new MyThread("Worker name: Thread "+ check+ ", " +"Palindrome count: " , task, sharedArr , no_of_bags);
				check++;
				executor.execute(runnable);
				
			} 
		}
		
		//here i am terminating all the threads
		executor.shutdown();
		while(!executor.isTerminated()) {}
		System.out.println("Finished all the worker threads");
		//here I am terminating all the threads
		writer.awaitTermination(5, TimeUnit.SECONDS);
		writer.shutdown();
		
		//I am calling this function only to create the
		//desired format of the output that sir mentioned 
		//we need to have 
		OutPut_Filehandler obj = new OutPut_Filehandler();
		obj.fileformat();
	
	}

}



/*I have extended this class from arraylist so that I can make 
array of Arraylist<String> which is generic type
   */
class StringArrayList extends ArrayList<String>
{
		/**	
		 *  */
	private static final long serialVersionUID = 1L;
}
