package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class MapperWholeRecommendPhase5 extends
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

				// followedFriends[1] = followedFriends[1].substring(0,
				// followedFriends[1].length() -
				// Protocols.FRIEND_LIST_SEPARATOR.length());

				// context.write(new
				// IntWritable(Integer.parseInt(followedFriends[0])), new
				// Text(Protocols.USER_FRIEND_LIST_STARTS_WITH +
				// followedFriends[1]));

				String[] followingList = followedFriends[1]
						.split(Protocols.FRIEND_LIST_SEPARATOR);
				IntWritable userID = new IntWritable(
						Integer.parseInt(followedFriends[0]));
				for (String eachFriend : followingList) {
					if (!eachFriend.isEmpty()) {
						context.write(userID, new Text(
								Protocols.RECOMMEND_PHASE_2_FRIENDS_STARTS_WITH
										+ eachFriend));
					}
				}

			} else if (valueRead
					.startsWith(Protocols.TESTING_DATASET_STARTS_WITH)) {
				// System.out.println("");
				// Ignoring Line

//				valueRead = valueRead
//						.substring(Protocols.TESTING_DATASET_STARTS_WITH
//								.length());
//				String[] followedFriends = valueRead
//						.split(Protocols.FRIEND_EDGE_SPLIT);
//
//				// followedFriends[1] = followedFriends[1].substring(0,
//				// followedFriends[1].length() -
//				// Protocols.FRIEND_LIST_SEPARATOR.length());
//
//				// context.write(new
//				// IntWritable(Integer.parseInt(followedFriends[0])), new
//				// Text(Protocols.USER_FRIEND_LIST_STARTS_WITH +
//				// followedFriends[1]));
//
//				String[] followingList = followedFriends[1]
//						.split(Protocols.FRIEND_LIST_SEPARATOR);
//				IntWritable userID = new IntWritable(
//						Integer.parseInt(followedFriends[0]));
//				for (String eachFriend : followingList) {
//					if (!eachFriend.isEmpty()) {
//						context.write(
//								userID,
//								new Text(
//										Protocols.RECOMMEND_PHASE_2_TEST_FRIENDS_STARTS_WITH
//												+ eachFriend));
//					}
//				}
			} else {
				String[] userCountList = valueRead
						.split(Protocols.VALUE_SEPARATOR);

				String[] friendList = userCountList[1].substring(
						Protocols.USER_FRIEND_LIST_STARTS_WITH.length()).split(
						Protocols.FRIEND_LIST_SEPARATOR);
				String[] userIDs = userCountList[0].substring(1,
						userCountList[0].length() - 1).split(
						Protocols.FRIEND_LIST_SEPARATOR);
				for (String userID : userIDs) {
					IntWritable userIDIntWritable = new IntWritable(
							Integer.parseInt(userID));
					for (String eachFriend : friendList) {
						context.write(userIDIntWritable, new Text(eachFriend));
					}
				}

			}
		} catch (Exception exception) {
			System.out.println("Exception In Line " + valueRead);
		}
	}
}
