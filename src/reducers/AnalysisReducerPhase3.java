package reducers;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class AnalysisReducerPhase3 extends
		Reducer<IntWritable, Text, NullWritable, Text> {
	@Override
	protected void reduce(IntWritable key, Iterable<Text> allValues, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		
			int count = 0;
			boolean iAmColdStart = true;
			for (Text values : allValues) {
				String valueRead = values.toString();
				if(valueRead.equals("YES"))
				{
					iAmColdStart = false;
				}
				else if (valueRead.equals("NO"))
				{
					iAmColdStart = true;
				}
				else
				{
					count = Integer.parseInt(valueRead);
				}
			}
			
			if(iAmColdStart)
			{
				context.write(NullWritable.get(), new Text(Protocols.COLD_START_COUNT + count));
			}
					
		
	}
}
