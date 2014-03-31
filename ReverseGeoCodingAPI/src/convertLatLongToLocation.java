import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class convertLatLongToLocation {
	
	/* This class contains the functions to connect to the Google GeoCoding API and parses out the location name containing "State, Country" 
		from the output of jsonobject */
	
	public JsonObject connectionToAPI(String latitude, String longitude) throws IOException{
	 		
		String URL ="https://maps.googleapis.com/maps/api/geocode/json?latlng="+ latitude +","+ longitude
				+"&sensor=false&result_type=administrative_area_level_1&result_type=country&key=xxxxxxxxxxxxxxx";
		
		URL url = new URL(URL);
	    HttpURLConnection request = (HttpURLConnection) url.openConnection();
	    request.connect();
	    
	    // Convert to a JSON object to print data
	    JsonParser jp = new JsonParser(); //from gson
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //convert the input stream to a json element
	    JsonObject rootobj = root.getAsJsonObject(); //may be an array, may be an object. 
	    return rootobj;
	
	} // URLConnection function ends
	
	public String LocationConversion(JsonObject jsonobject){
	
		String location_name = null;
		String Actual_Status = "\"OK\"";
	    String Status = jsonobject.get("status").toString();
	         
	    if (Status.equals(Actual_Status) ){
		    JsonArray jarray = jsonobject.getAsJsonArray("results");
		    jsonobject = jarray.get(0).getAsJsonObject();
		    location_name = jsonobject.get("formatted_address").toString();			    		    		    
	   // System.out.println(location_name);
	    }
	    else{
	    	location_name="";
	    }
	    return location_name;
	    }  // LocationConversion function ends
	
}
