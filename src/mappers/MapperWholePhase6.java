package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class MapperWholePhase6 extends Mapper<LongWritable, Text, LongWritable, IntWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		String[] userAndList = value.toString().split(
				Protocols.FRIEND_EDGE_SPLIT);
		int totalFollowers = userAndList[1]
				.split(Protocols.FRIEND_LIST_SEPARATOR).length;

		context.write(new LongWritable(totalFollowers), new IntWritable(Integer.parseInt(userAndList[0])));

	}
}
