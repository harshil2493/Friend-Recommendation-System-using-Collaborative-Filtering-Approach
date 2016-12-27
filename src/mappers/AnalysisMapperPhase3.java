package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class AnalysisMapperPhase3 extends Mapper<LongWritable, Text, IntWritable, Text> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String valueRead = value.toString();
//		if(valueRead.startsWith(Protocols.TRAINING_DATASET_STARTS_WITH))
		
		if(valueRead.contains(Protocols.RECOMMEND_LIST_STARTS_WITH))
		{
			
			int userID = Integer.parseInt(valueRead.substring(Protocols.RECOMMEND_LIST_STARTS_WITH.length()).split(Protocols.VALUE_SEPARATOR)[0]);
			context.write(new IntWritable(userID), new Text("YES"));

		}
		else if(valueRead.contains(Protocols.COLD_START_STARTS_WITH))
		{
			
			int userID = Integer.parseInt(valueRead.substring(Protocols.COLD_START_STARTS_WITH.length()).split(Protocols.VALUE_SEPARATOR)[0]);
			context.write(new IntWritable(userID), new Text("NO"));

		}
		else 
		{
//			valueRead = valueRead.substring(Protocols.TRAINING_DATASET_STARTS_WITH.length());
			
			int userID = Integer.parseInt(valueRead.split(Protocols.FRIEND_EDGE_SPLIT)[0]);
			int countOfFriends = 0;
			if(valueRead.contains(Protocols.FRIEND_LIST_SEPARATOR))
			{
				countOfFriends = valueRead.split(Protocols.FRIEND_LIST_SEPARATOR).length;
				
			}
			context.write(new IntWritable(userID), new Text(countOfFriends + ""));
		}

	}
}
