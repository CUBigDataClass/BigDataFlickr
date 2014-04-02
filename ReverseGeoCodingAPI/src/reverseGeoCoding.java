import com.google.gson.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class reverseGeoCoding {
	
	/* This class contains the main function to read the json file generated from Flickr API 
	    and convert it to json file where each record contains a tag and location name
	 */
 
	public static void main(String[] args){
		
		String line = null;
		 try {
			 BufferedReader br = new BufferedReader( new FileReader("C:\\test_image.json"));
			
			 while((line=br.readLine()) !=null){
				// loop array
				 readfromFile readfile = new readfromFile();
				 tagLocationPair tlpair = readfile.readFile(line);
				 convertLatLongToLocation convertlocation = new convertLatLongToLocation();
				 List<String> locations =tlpair.getArray2();
				 List<String> tags = tlpair.getArray1();
				 JsonObject root = convertlocation.connectionToAPI(locations.get(0),locations.get(1));
				 String location = convertlocation.LocationConversion(root);
				 				
				 	if(!(location.isEmpty())){
						  writetoFile writefile = new writetoFile();
						  writefile.writeFile(tags, location);    
				 	} // if condition
			  } //while loop for each document in the json file			 	
			br.close();	    	 	
		    } //try	
		 catch (FileNotFoundException e) {
		        e.printStackTrace();
		 }catch (IOException e) {
		        e.printStackTrace();
		 }		 
	  } // main 		
	} //class

