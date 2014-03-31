import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.simple.JSONObject;


public class writetoFile {
	// This class contains the function to write the output to a json file where each record is a tag and location name
	public void writeFile(List<String> tags, String locations){
		
		try {
		FileWriter	file = new FileWriter("C:\\result_test.json",true);
		
		//Appending the location name to each tag
		for(int i=0; i<tags.size();i++){
			JSONObject finalobj = new JSONObject();
			finalobj.put("Tag",tags.get(i));
			finalobj.put("location",locations.replaceAll("\"",""));
			file.write(finalobj.toJSONString() + System.getProperty( "line.separator" ));
	 
		} //for loop
	//	file.flush();
  		file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	
}