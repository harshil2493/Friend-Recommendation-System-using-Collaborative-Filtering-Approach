package reducers;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class AnalysisReducerPhase4 extends
		Reducer<IntWritable, IntWritable, NullWritable, Text> {
	@Override
	protected void reduce(IntWritable key, Iterable<IntWritable> allValues, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		
			int count = 0;
			int totalValue = 0;
			
			for (IntWritable values : allValues) {
				count++;
				totalValue = totalValue + values.get();
			}
			
			context.write(NullWritable.get(), new Text(key + "\tTotal: " + count + "\tAverage: " + (totalValue * 1.0f / count)));
					
		
	}
}
