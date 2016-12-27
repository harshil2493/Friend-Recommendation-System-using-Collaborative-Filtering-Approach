package reducers;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class AnalysisReducerPhase1 extends
		Reducer<Text, IntWritable, NullWritable, Text> {
	@Override
	protected void reduce(Text key, Iterable<IntWritable> allValues, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		
			int total = 0;
			for (IntWritable values : allValues) {
				total = total + values.get();
			}
			context.write(NullWritable.get(), new Text(key + "\t" + total));
//		}
		
		
	}
}
