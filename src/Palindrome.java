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
	
	static int index = 0;
	static ArrayList<String> dictionary = new ArrayList<String>();
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
public static void main(String[] args) throws InterruptedException, FileNotFoundException, IOException {
		
		HashMap<String, ArrayList<String>>  mapBagOfTasks  = new HashMap<String, ArrayList<String>>();
		ArrayList<Integer> length = new ArrayList<Integer>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the no of worker threads you want to create");
		int no_of_workers = scan.nextInt();
		MyThread [] worker = new MyThread[no_of_workers];
		int no_of_bags = 0;
		ArrayList<String> sharedArr = new ArrayList<String>();
		
	for(int i = 0 ; i < dictionary.size() ; i++)
		{
			if(!length.contains(dictionary.get(i).length()))
			{
				length.add(dictionary.get(i).length());
				no_of_bags++;
			}
		
		}
		
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
		
	for(int i = 0 ; i < no_of_bags ; i++)
		{
			mapBagOfTasks.put(length.get(i).toString(), arrbag[i]);
		}
		ExecutorService executor = Executors.newFixedThreadPool(no_of_workers);
		int check = 0;
		ExecutorService writer = Executors.newFixedThreadPool(1);
		Runnable runnable_w = new Consumer("Thread writer" , sharedArr , no_of_bags); 
		writer.execute(runnable_w);
		
		for(int i = 0 ; i < no_of_bags ; i++)
		{
			if(mapBagOfTasks.containsKey(length.get(0).toString()))
			{
				ArrayList<String> task =	new 
						ArrayList<String>(mapBagOfTasks.get(length.get(index).toString()));
						index++;
				if(check == no_of_workers) 
				{
					check = 0;
				}
				Runnable runnable = new 
						MyThread("Worker name: Thread "+ check+ ", " +"Palindrome count: " , task, sharedArr , no_of_bags);
				check++;
				executor.execute(runnable);
				
			} 
		}

		executor.shutdown();
		while(!executor.isTerminated()) {}
		System.out.println("Finished all the worker threads");
		writer.awaitTermination(5, TimeUnit.SECONDS);
		writer.shutdown();
		
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
