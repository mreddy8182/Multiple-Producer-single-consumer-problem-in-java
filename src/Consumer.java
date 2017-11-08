import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Consumer extends Thread {

	ArrayList<String> sharedArr = new ArrayList<String>();
	String threadname;
	static public ArrayList<String> thread_data = new ArrayList<String>();
	int no_of_bags;
	
	Consumer(String threadname , ArrayList<String> sharedArr , int no_of_bags)
	{
		this.threadname = threadname;
		this.sharedArr = sharedArr;
		this.no_of_bags = no_of_bags;
	}
		
	 void consume() throws IOException, InterruptedException
	{
		synchronized (sharedArr){
		while(sharedArr.isEmpty()){
		sharedArr.wait();
	}
		filewriter();
		sharedArr.clear();
		sharedArr.notifyAll();
	}
}

	 void filewriter() throws IOException
	 {
		 PrintWriter writer = null;
		 FileWriter write = null;
		 File file = new File("output.txt");
		 if (!file.exists()) {
		 file.createNewFile();
		}
		write = new FileWriter(file.getAbsoluteFile(), true);
		writer = new PrintWriter(write);
		
			for(int i = 0 ; i <sharedArr.size() ; i++)
			{
				synchronized(sharedArr)
				{		
					//thread_data.add(sharedArr.get(i));
					writer.println(sharedArr.get(i));
					sharedArr.notifyAll();
				}
		}
		if (writer != null)
		writer.close();
		if (write != null)
		write.close();

}

	  
	
	public void run()
	{
	
		while(true) {
			try {
					consume();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
