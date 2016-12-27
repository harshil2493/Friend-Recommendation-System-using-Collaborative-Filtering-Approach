package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class MapperPhase5 extends Mapper<LongWritable, Text, IntWritable, Text> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		String[] userUserAndCount = value.toString().split(
				Protocols.USER_USER_COUNT_SEPARATOR);
		String[] users = userUserAndCount[0]
				.split(Protocols.USER_USER_SEPARATOR);

		context.write(new IntWritable(Integer.parseInt(users[0])), new Text(
				users[1] + Protocols.USER_WEIGHT + userUserAndCount[1]));
		context.write(new IntWritable(Integer.parseInt(users[1])), new Text(
				users[0] + Protocols.USER_WEIGHT + userUserAndCount[1]));

	}
}
