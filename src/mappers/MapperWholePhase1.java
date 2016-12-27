package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class MapperWholePhase1 extends Mapper<LongWritable, Text, IntWritable, Text> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String valueRead = value.toString();
		try {

			String[] friends = valueRead.split(Protocols.FRIEND_EDGE_SPLIT);
			friends[0] = friends[0].trim();

			if (friends.length == 2) {
				if (!friends[1].contains(Protocols.VALID_FRIEND_CONTAINS)) {
					context.write(
							new IntWritable(Integer.parseInt(friends[0])),
							Protocols.IGNORE_ID);
				} else {
					context.write(
							new IntWritable(Integer.parseInt(friends[0])),
							Protocols.CONSIDER_ID);

					String[] friendList = friends[1]
							.split(Protocols.FRIEND_LIST_SEPARATOR);

					for (String eachFriend : friendList) {
						try {
							context.write(
									new IntWritable(Integer.parseInt(eachFriend
											.trim())), new Text(friends[0]));
						} catch (Exception e) {
							System.out.println("Cannot Send This Link"
									+ eachFriend);
						}
					}
				}
			} else {
				context.write(new IntWritable(Integer.parseInt(friends[0])),
						Protocols.IGNORE_ID);

			}
		} catch (Exception exception) {

		}
	}
}
