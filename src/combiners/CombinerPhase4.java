package combiners;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class CombinerPhase4 extends
		Reducer<Text, IntWritable, Text, IntWritable> {

	@Override
	protected void reduce(Text key, Iterable<IntWritable> allValues,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		//
		int count = 0;
		for (IntWritable counts : allValues) {
			count += counts.get();
		}
		context.write(key, new IntWritable(count));

	}
}
