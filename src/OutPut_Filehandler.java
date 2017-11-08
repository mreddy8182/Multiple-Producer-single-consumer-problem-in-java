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
import java.util.stream.Collectors;

public class OutPut_Filehandler {

	static int file_checker = 0;
	OutPut_Filehandler(){
		
		
	}
	
	
	static void fileformat() throws FileNotFoundException, IOException
	{
		ArrayList<String> temp = new ArrayList<String>();
		ArrayList<String> temp2 = new ArrayList<String>();
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader("output.txt"));
		String line;
		
			    
		String Filename = "D:\\Semester 9\\Java examples\\Assignment 2\\output.txt";
		br = Files.newBufferedReader(Paths.get(Filename));
		temp2 = (ArrayList<String>) br.lines().collect(Collectors.toList());   
		
		
		
		for(int i = 0 ; i <temp2.size() ;i++)
		{
			if(temp2.get(i).toString().startsWith("Worker name: Thread "))
				{
						temp.add(temp2.get(i).toString());
				}
		}
				   
		for(int i = 0 ; i <temp.size() ;i++)
		{
				   String a = temp.get(i);
				   int b = temp2.indexOf(a);
				   temp2.remove(b);
		}
		
		for(int i = 0 ; i <temp2.size() ;i++)
		{
			if(temp2.get(i).toString().startsWith("Worker name: Thread "))
				{
						temp2.remove(i);
				}
		}
		
		for(int i = temp2.size()-1 ; i >0 ;i--)
		{
			if(temp2.get(i).toString().startsWith("Worker name: Thread "))
				{
						temp2.remove(i);
				}
		}
				   
		
		
		
		
		
		OutPut_Filehandler obj = new OutPut_Filehandler();
			obj.filewriter(temp2);
			obj.filewriter(temp);

	System.out.println(temp2);
	System.out.println(temp);
	System.out.println("Total count of Palindrome" + MyThread.total_counter);
	}
	
 void filewriter(ArrayList<String> temp) throws IOException
{
	 PrintWriter writer = null;
	 FileWriter write = null;
	 File file = new File("result1.txt");
	 if (!file.exists()) {
	 file.createNewFile();
	}
	 write = new FileWriter(file.getAbsoluteFile(), true);
	writer = new PrintWriter(write);
	
	if(file_checker == 0)
	{
		for(int i = 0 ; i <temp.size() ; i++)
		{
			writer.println(temp.get(i));
			file_checker  =1;
		}
		
		writer.println("\n\n");
	}	
	
	else 
	{
		for(int i = 0 ; i <temp.size() ; i++)
		{
			writer.print(temp.get(i) + "  ");
			
		}
		writer.println(" ");
		writer.println("Total Palindrome count: "+MyThread.total_counter);
		
	}
		
	if (writer != null)
	writer.close();
	if (write != null)
	write.close();

}
 }
