import com.mongodb.hadoop.MongoConfig;
import com.mongodb.hadoop.MongoInputFormat;
import com.mongodb.hadoop.MongoOutputFormat;
import com.mongodb.hadoop.util.MongoTool;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.util.ToolRunner;

public class taglocationCount extends MongoTool {
	
	public taglocationCount(){
		Configuration conf = new Configuration();
        MongoConfig config = new MongoConfig(conf);
        setConf(conf);
     
        config.setInputFormat(MongoInputFormat.class);
        config.setInputURI("mongodb://localhost:27017/mongo_hadoop.messages");
        config.setMapper(mapJob.class);
        config.setReducer(reduceJob.class);
        config.setMapperOutputKey(KeyPair.class);
        config.setMapperOutputValue(IntWritable.class);
        config.setOutputKey(KeyPair.class);
        config.setOutputValue(IntWritable.class);
        config.setOutputURI("mongodb://localhost:27017/mongo_hadoop.message_pairs");
        config.setOutputFormat(MongoOutputFormat.class);
    }

    public static void main(final String[] pArgs) throws Exception {
        System.exit(ToolRunner.run(new taglocationCount(), pArgs));
    }
}


