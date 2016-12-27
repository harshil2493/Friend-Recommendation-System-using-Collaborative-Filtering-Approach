package reducers;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class ReducerRecommendPhase1 extends
		Reducer<IntWritable, Text, NullWritable, Text> {
	@Override
	protected void reduce(IntWritable key, Iterable<Text> allValues,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String friendList = "";
		HashSet<String> userInformations = new HashSet<String>();

		for (Text eachValue : allValues) {
			String eachValueString = eachValue.toString();

			if (eachValueString
					.startsWith(Protocols.USER_FRIEND_LIST_STARTS_WITH)) {
				friendList = eachValueString;
			} else {
				userInformations.add(eachValueString);
			}
		}
		if(!friendList.isEmpty())
		{
		for (String eachUserString : userInformations) {
			context.write(NullWritable.get(), new Text(eachUserString
					+ Protocols.VALUE_SEPARATOR + friendList));
		}
		}
	}
}
