
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Mapper;
import org.bson.BSONObject;

public class mapJob extends Mapper<Object, BSONObject, KeyPair, IntWritable> {
	
	public void map(final Object key,final BSONObject val, final Context context ) throws IOException, InterruptedException{
		
			BSONObject headers = (BSONObject)val.get("headers");
			String Tag = (String)headers.get("Tag");
			String Location = (String)headers.get("Location");
			context.write(new KeyPair(Tag,Location), new IntWritable(1));
	}
	
}
