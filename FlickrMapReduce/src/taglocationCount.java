import com.mongodb.BasicDBObjectBuilder;
import org.bson.BasicBSONObject;
import com.mongodb.hadoop.MongoInputFormat;
import com.mongodb.hadoop.MongoOutputFormat;
import com.mongodb.hadoop.io.BSONWritable;
import com.mongodb.hadoop.util.MongoTool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.ToolRunner;
import org.bson.BSONObject;

import java.io.IOException;



public class taglocationCount extends MongoTool {

	

	    static class taglocationMapper extends Mapper<Object , BSONObject, KeyPair, IntWritable> {

	        @Override
	        public void map(Object key, BSONObject value,final Context context) throws IOException, InterruptedException {
	        	String tag = value.get("Tag").toString();
	        	String location = value.get("location").toString();
	            context.write(new KeyPair(tag,location), new IntWritable(1));
	        }

	    }

	    static class taglocationReducer extends Reducer<KeyPair, IntWritable, BSONWritable, IntWritable> {

	        @Override
	        public void reduce(final KeyPair key, final Iterable<IntWritable> iterator, final Context pcontext) throws IOException,InterruptedException {
	            int count = 0;
	            for ( final IntWritable value : iterator ){
	                count += value.get();
	            }
	BasicBSONObject output = new BasicBSONObject();
	output.put("tag",key.tag);
	output.put("location",key.location);
	
	          pcontext.write(new BSONWritable(output), new IntWritable(count));
	        }

	    }

	    @SuppressWarnings("deprecation")
		public int run(final String[] args) throws Exception {
	        final Configuration conf = getConf();
	        final Job job = new Job(conf, "tag location records Count");
	        
	        job.setJarByClass(taglocationCount.class);
	        job.setReducerClass(taglocationReducer.class);
	        job.setMapperClass(taglocationMapper.class); 
	        job.setInputFormatClass(MongoInputFormat.class);
	        job.setOutputFormatClass(MongoOutputFormat.class);
	        job.setOutputKeyClass(BSONWritable.class);
	        job.setOutputValueClass(IntWritable.class);
	        job.setMapOutputKeyClass(KeyPair.class);
	        job.setMapOutputValueClass(IntWritable.class);
	        return job.waitForCompletion( true ) ? 0 : 1;
	    }

	    public static void main(final String[] args) throws Exception {
	    	System.exit( ToolRunner.run( new taglocationCount(), args ) );
	    }
}


