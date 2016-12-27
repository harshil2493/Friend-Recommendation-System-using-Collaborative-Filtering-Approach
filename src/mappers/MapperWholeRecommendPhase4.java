package mappers;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class MapperWholeRecommendPhase4 extends
		Mapper<LongWritable, Text, IntWritable, Text> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String valueRead = value.toString();
		try {
			if (valueRead.startsWith(Protocols.TRAINING_DATASET_STARTS_WITH)) {
				valueRead = valueRead
						.substring(Protocols.TRAINING_DATASET_STARTS_WITH
								.length());
				String[] followedFriends = valueRead
						.split(Protocols.FRIEND_EDGE_SPLIT);

				followedFriends[1] = followedFriends[1].substring(0,
						followedFriends[1].length()
								- Protocols.FRIEND_LIST_SEPARATOR.length());

				context.write(
						new IntWritable(Integer.parseInt(followedFriends[0])),
						new Text(Protocols.USER_FRIEND_LIST_STARTS_WITH
								+ followedFriends[1]));

			}else if (valueRead.startsWith(Protocols.COLD_START_STARTS_WITH)) {
				
				
				String[] userAndFriendsList = valueRead.substring(Protocols.COLD_START_STARTS_WITH.length()).split(Protocols.VALUE_SEPARATOR);
//				
				String[] friends = userAndFriendsList[1].substring(1, userAndFriendsList[1].length() - 1).split(Protocols.FRIEND_LIST_SEPARATOR);
//				
////				FloatWritable userFloat = new FloatWritable(Float.parseFloat(userAndFriendsList[0]));
//				int userID = Integer.parseInt(userAndFriendsList[0]);
				for(String eachUser : friends)
				{
					if(!eachUser.isEmpty())
					{
						context.write(new IntWritable(Integer.parseInt(eachUser.trim())), new Text(userAndFriendsList[0]));
					}
				}
				

			}
		} catch (Exception exception) {
			System.out.println("Exception In Line " + valueRead);
		}
	}
}
