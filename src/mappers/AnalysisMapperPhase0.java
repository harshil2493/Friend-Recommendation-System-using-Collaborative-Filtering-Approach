package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class AnalysisMapperPhase0 extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String valueRead = value.toString();
//		if(valueRead.startsWith(Protocols.TRAINING_DATASET_STARTS_WITH))
		if(valueRead.startsWith(Protocols.TRAINING_DATASET_STARTS_WITH))
		{
//			context.write(new Text("Total Users"), new IntWritable(1));

			context.write(new Text("Training DataSet Users"), new IntWritable(1));

		}
		
		else if(valueRead.contains(Protocols.PRIVATE_USER))
		{
			context.write(new Text("Total Users"), new IntWritable(1));

			context.write(new Text("Private Users"), new IntWritable(1));

		}
		else if(valueRead.contains(Protocols.NOT_FOUND_USER))
		{
			context.write(new Text("Total Users"), new IntWritable(1));

			context.write(new Text("Not Found Users"), new IntWritable(1));

		}
		else if(valueRead.contains(Protocols.FRIEND_LIST_SEPARATOR))
		{
			context.write(new Text("Total Users"), new IntWritable(1));

			int friendsNumber = valueRead.split(Protocols.FRIEND_LIST_SEPARATOR).length;
			context.write(new Text("Total Edges"), new IntWritable(friendsNumber));
			context.write(new Text(friendsNumber + "\t Total Friends"), new IntWritable(1));
			
			context.write(new Text("Users With Friends"), new IntWritable(1));


		}
		else 
		{
			context.write(new Text("Total Users"), new IntWritable(1));

			context.write(new Text("Users With Zero Friends"), new IntWritable(1));

		}

	}
}
