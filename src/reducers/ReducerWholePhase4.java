package reducers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class ReducerWholePhase4 extends
		Reducer<Text, IntWritable, NullWritable, Text> {
	@Override
	protected void reduce(Text key, Iterable<IntWritable> allValues,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// int count = 0;
		// for (NullWritable nulls : allValues) {
		// count++;
		// }
		//
		// context.write(NullWritable.get(), new
		// Text(key+Protocols.USER_USER_COUNT_SEPARATOR + count));

		int count = 0;
		for (IntWritable counts : allValues) {
			count += counts.get();
		}

		// if(count > Protocols.MINIMUM_WEIGHT_FOR_USER_REQUIRED)
		// {
		context.write(NullWritable.get(), new Text(key
				+ Protocols.USER_USER_COUNT_SEPARATOR + count));
		// }
	}
}
