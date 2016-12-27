package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class MapperRecommendPhase1 extends
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

			} else if (valueRead
					.startsWith(Protocols.TESTING_DATASET_STARTS_WITH)) {
				// System.out.println("");
				// Ignoring Line
			} else {
				// String[] userUserAndCount =
				// value.toString().split(Protocols.USER_USER_COUNT_SEPARATOR);
				// String[] users =
				// userUserAndCount[0].split(Protocols.USER_USER_SEPARATOR);
				//
				// context.write(new IntWritable(Integer.parseInt(users[0])),
				// new Text(users[1] + Protocols.USER_WEIGHT +
				// userUserAndCount[1]));
				// context.write(new IntWritable(Integer.parseInt(users[1])),
				// new Text(users[0] + Protocols.USER_WEIGHT +
				// userUserAndCount[1]));

				String[] userUserCounts = value.toString().split(
						Protocols.FRIEND_EDGE_SPLIT);
				String userID = userUserCounts[0];
				String[] usersAndCounts = userUserCounts[1]
						.split(Protocols.FRIEND_LIST_SEPARATOR);

				for (String eachUsers : usersAndCounts) {
					if (!eachUsers.isEmpty()) {
						String[] userWeight = eachUsers
								.split(Protocols.USER_WEIGHT);
						context.write(
								new IntWritable(Integer.parseInt(userWeight[0])),
								new Text(userID + Protocols.USER_WEIGHT
										+ userWeight[1].trim()));

					}
				}
			}
		} catch (Exception exception) {
			System.out.println("Exception In Line " + valueRead);
		}
	}
}
