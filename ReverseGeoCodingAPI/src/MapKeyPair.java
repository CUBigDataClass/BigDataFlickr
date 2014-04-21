import java.util.List;

/* This Class is used by the callable and future constructs of Executor Service
 *  for the concurrent program to retrieve the location names from Google GeoCoding API
 */
public class MapKeyPair {
	
	 String location;
	 List<String> tags;
	
	 public MapKeyPair(String location, List<String> tags){
	     this.tags = tags;
	     this.location = location;
	 }
	 
	 public void setTags(List<String> tags){
		 this.tags= tags;
	 }
	 public void setLocation(String location){
		 this.location=location;
	 }
	 public List<String> getTags(){
		 return this.tags;
	 }
	 public String getLocation(){
		 return this.location;
	 }
}
