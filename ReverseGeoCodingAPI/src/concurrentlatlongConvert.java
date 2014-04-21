import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonObject;


public class concurrentlatlongConvert {

	/* Use ExecutorService framework to convert the latitude longitude format into location name format
	 by splitting the json file into different chunks and processing them in a concurrent way. */
	
	public static void main(final String[] args) throws InterruptedException, ExecutionException,IOException{
		
		 final int numberOfCores = Runtime.getRuntime().availableProcessors();
		 final double blockingCoefficient = 0.9;
		 final int poolSize = (int)(numberOfCores / (1 - blockingCoefficient));
		 System.out.println("Number of Cores available is " + numberOfCores);
		 System.out.println("Pool size is " + poolSize);
		 String line = null;
	
			 BufferedReader br = new BufferedReader( new FileReader("C:\\Users\\Krishna\\Desktop\\FlickrData\\tag_final_image16.json"));
			 final List<Callable<MapKeyPair>> partitions = new ArrayList<Callable<MapKeyPair>>();
			 while((line=br.readLine()) !=null){
				// loop array
				 readfromFile readfile = new readfromFile();
				 tagLocationPair tlpair = readfile.readFile(line);
				 final List<String> locations =tlpair.getArray2();
				 final List<String> tags = tlpair.getArray1();
				 partitions.add(new Callable<MapKeyPair>() {

					@Override
					public MapKeyPair call() throws Exception {
						
						JsonObject root = new convertLatLongToLocation().connectionToAPI(locations.get(0),locations.get(1));
						String location = new convertLatLongToLocation().LocationConversion(root);
						final MapKeyPair jsonrecord = new MapKeyPair(location,tags);
						return jsonrecord;
				 }
				 });	 				
				 	
			  } //while loop for each document in the json file			 	
			br.close();	 
			final ExecutorService executorPool = 
				      Executors.newFixedThreadPool(poolSize);
			  final List<Future<MapKeyPair>> LocationNames = 
				      executorPool.invokeAll(partitions, 50000, TimeUnit.SECONDS);
			
			  for(final Future<MapKeyPair> LocationName : LocationNames)
			  {
				  MapKeyPair mapkeypair = LocationName.get();
				  if((mapkeypair.getLocation()!=null)){
					  writetoFile writefile = new writetoFile();
					  writefile.writeFile(mapkeypair.getTags(),mapkeypair.getLocation());    
			 	} // if condition
			  }  
			  executorPool.shutdown();
	
		
	}//main function
		 
}//class

