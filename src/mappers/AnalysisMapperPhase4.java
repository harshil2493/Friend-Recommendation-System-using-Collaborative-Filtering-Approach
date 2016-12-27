package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class AnalysisMapperPhase4 extends Mapper<LongWritable, Text, IntWritable, IntWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		int valueRead = Integer.parseInt(value.toString().substring(Protocols.COLD_START_COUNT.length()));
//		if(valueRead.startsWith(Protocols.TRAINING_DATASET_STARTS_WITH))
		
		context.write(new IntWritable(valueRead), new IntWritable(1));
		context.write(new IntWritable(-500), new IntWritable(valueRead));

	}
}
