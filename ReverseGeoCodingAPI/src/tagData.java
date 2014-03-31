import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class tagData {

	
public static void main(String[] args){
			
		
		String line = null;
		 try {
			 
			 // Reading the json file on which we filter the records containing the tags
			 BufferedReader br = new BufferedReader( new FileReader("C:\\Users\\Krishna\\Desktop\\FlickrData\\image.json"));
			 JSONParser parser = new JSONParser();
			 
			 
			 //Creating a File to store the only the records containing Tags in it
			 FileWriter file = new FileWriter("C:\\Users\\Krishna\\Desktop\\FlickrData\\tag_final__image.json");
			 
			 //Loop through each record in the json file
			 while((line=br.readLine()) !=null){
				
			 	Object obj = parser.parse(line);
				JSONObject jsonObject = (JSONObject) obj;
				
				JSONArray Tag = (JSONArray) jsonObject.get("Tag");
							
				//Enter the if condition is the Tag parameter is not empty and the Location parameter is a JSONArray
					if(!(Tag.isEmpty()) && (jsonObject.get("Location") instanceof JSONArray)){
						JSONObject finalobj = new JSONObject();
																														
						finalobj.putAll(jsonObject);
						
						//Appending each record to the new json file
						file.write(finalobj.toJSONString() + System.getProperty( "line.separator" ));
					}	//if condition		   
			 	
			 	} //while loop for each document in the json file
			 		 	
			br.close();
		    file.flush();
	  		file.close();
		 	
		    } //try
		
		 catch (FileNotFoundException e) {
		        e.printStackTrace();
		 }catch (IOException e) {
		        e.printStackTrace();
		 }catch (ParseException e) {
		        e.printStackTrace();
		 }
		 
	  } // main funnction
	
} // class
