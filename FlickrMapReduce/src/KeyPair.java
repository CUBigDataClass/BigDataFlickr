import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

// Code to create a concatenated key(tag and location) for Map Reduce

public class KeyPair implements WritableComparable{
	
			 String tag;
			 String location;
			 
			 public KeyPair(){
				 
			 }
			 public KeyPair(String tag, String location){
			     this.tag = tag;
			     this.location = location;
			 }
			 public void write(final DataOutput out) throws IOException {
			        out.writeUTF(this.tag);
			        out.writeUTF(this.location);
			    }
			 public void readFields(DataInput in) throws IOException {
		         tag = in.readUTF();
		         location = in.readUTF();
		       }
	
			public int compareTo(final Object o) {
				if (!(o instanceof KeyPair)) {
		            return -1;
		        }
		        KeyPair mp = (KeyPair) o;
		        int first = tag.compareTo(mp.tag);
		        if (first != 0) {
		            return first;
		        }
		        int second = location.compareTo(mp.location);
		        if (second != 0) {
		            return second;
		        }
		        return 0;
		    }
	}
    



