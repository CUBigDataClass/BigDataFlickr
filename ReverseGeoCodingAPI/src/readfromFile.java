import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class readfromFile {
	// This class contains the function which reads the json file and parses out the Tag and Latitude and Longitude values
	public  tagLocationPair readFile(String line){
		try{
		
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(line);
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray Tag = (JSONArray) jsonObject.get("Tag");
			Iterator<String> iterator1 = Tag.iterator();
			
			//creating a list to store all the tags corresponding to each document
			
			List<String> tags = new ArrayList<String>();
			
				while (iterator1.hasNext()) {
			 		tags.add(iterator1.next());
			 	}
			
			JSONArray Location = (JSONArray) jsonObject.get("Location");
			Iterator<String> iterator2 = Location.iterator();
			
			 // Creating a list into which the latitude longitude are stored
			
			List<String> locations = new ArrayList<String>();
			
				while (iterator2.hasNext()) {
			 		locations.add(iterator2.next());
			 	}
				 tagLocationPair P =new  tagLocationPair(tags, locations);
			return P;
		}
		catch (ParseException e) {
	        e.printStackTrace();
	        return null;
	 }
		

		
	}
}

