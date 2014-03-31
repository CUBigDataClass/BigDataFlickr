
import java.io.IOException;

import org.apache.hadoop.io.*; 
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context; 
import org.bson.BSONObject;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.hadoop.io.BSONWritable;

public class mapJob extends Mapper<Object, BSONObject, Object, IntWritable> {
	
	public void map(NullWritable key, BSONObject val, final Context context ) throws IOException, InterruptedException{
		
			BSONObject headers = (BSONObject)val.get("headers");
			String Tag = (String)headers.get("Tag");
			String Location = (String)headers.get("Location");
			context.write(new KeyPair(Tag,Location), new IntWritable(1));
	}
	
}
