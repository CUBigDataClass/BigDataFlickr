import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.bson.BSONObject;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.hadoop.io.BSONWritable;

public class reduceJob extends Reducer<KeyPair, IntWritable, BSONWritable,IntWritable>{

	public void reduce(final KeyPair kpair, final Iterable<IntWritable> kvalues, final Context context) throws IOException, InterruptedException{
		
		int sum = 0;
		for(final IntWritable value: kvalues){
			sum = sum + value.get();
		}
		
		BSONObject outdoc =  new BasicDBObjectBuilder().start().add("tag", kpair.getTag()).add("location",kpair.getLocation()).get();
		BSONWritable pkeyOut =new BSONWritable(outdoc);
		context.write(pkeyOut,new IntWritable(sum));
		
	}

}
