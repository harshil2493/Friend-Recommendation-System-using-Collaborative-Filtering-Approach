package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class AnalysisMapperPhase1 extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String valueRead = value.toString();
//		if(valueRead.startsWith(Protocols.TRAINING_DATASET_STARTS_WITH))
		
		if(valueRead.contains(Protocols.RECOMMEND_LIST_STARTS_WITH))
		{
			context.write(new Text("Recommended Users"), new IntWritable(1));


		}
		else if(valueRead.contains(Protocols.COLD_START_STARTS_WITH))
		{
			context.write(new Text("Cold Start Users"), new IntWritable(1));
//			String userID = valueRead.substring(Protocols.COLD_START_STARTS_WITH.length()).split(Protocols.VALUE_SEPARATOR)[0];
//			context.write(new Text(userID), new Text("YES"));

		}


	}
}
