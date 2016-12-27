package reducers;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class ReducerRecommendPhase6 extends
		Reducer<Text, FloatWritable, NullWritable, Text> {

	@Override
	protected void reduce(Text key, Iterable<FloatWritable> allValues,
			Context context) throws IOException, InterruptedException {

		// TODO Auto-generated method stub

		if (key.toString().startsWith(Protocols.ACCURACY_COUNT)) {
			int count = 0;
			float totalAccuracy = 0f;
			for (FloatWritable eachAccuracy : allValues) {
				totalAccuracy += eachAccuracy.get();
				count++;
			}

			context.write(NullWritable.get(), new Text(key.toString()
					.substring(Protocols.ACCURACY_COUNT.length())
					+ "\t"
					+ ((float) totalAccuracy * 100f / count) + "\t" + count));
		} 
//		else if (key.toString().startsWith(Protocols.COLD_START_COUNT)) {
//			int count = 0;
//			for (FloatWritable eachAccuracy : allValues) {
//				count++;
//			}
//			context.write(NullWritable.get(), new Text(
//					"Total Cold Start Users: " + count));
//		}
//		else if(key.toString().startsWith(Protocols.COLD_START_USERS_STARTS_WITH))
//		{
//			HashSet<Float> coldUsers = new HashSet<Float>();
//			for (FloatWritable eachAccuracy : allValues) {
//				coldUsers.add(eachAccuracy.get());
//			}
//			
//			context.write(NullWritable.get(), new Text(key + coldUsers.toString()));
//		}
	}
}
