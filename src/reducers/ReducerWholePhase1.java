package reducers;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class ReducerWholePhase1 extends
		Reducer<IntWritable, Text, NullWritable, Text> {
	@Override
	protected void reduce(IntWritable key, Iterable<Text> allValues,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		boolean ignoreID = true;
		HashSet<String> listOfFollowedID = new HashSet<String>();
		String IGNORE_STRING = Protocols.IGNORE_ID.toString();
		String CONSIDER_STRING = Protocols.CONSIDER_ID.toString();
		for (Text eachValue : allValues) {
			String eachValueRead = eachValue.toString();
			if (eachValueRead.equals(IGNORE_STRING)) {
				ignoreID = true;
				break;
			} else if (eachValueRead.equals(CONSIDER_STRING)) {
				ignoreID = false;
			} else {
				listOfFollowedID.add(eachValueRead);
			}
		}

		if (!ignoreID) {
			StringBuffer followedBuffer = new StringBuffer();
			for (String eachUser : listOfFollowedID) {
				followedBuffer.append(eachUser
						+ Protocols.FRIEND_LIST_SEPARATOR);
			}
			if (!followedBuffer.toString().isEmpty()) {
				context.write(NullWritable.get(),
						new Text(key + Protocols.FRIEND_EDGE_SPLIT
								+ followedBuffer.toString()));
			}
		}
	}
}
