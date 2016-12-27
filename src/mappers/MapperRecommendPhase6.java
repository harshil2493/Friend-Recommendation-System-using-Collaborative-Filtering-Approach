package mappers;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class MapperRecommendPhase6 extends
		Mapper<LongWritable, Text, Text, FloatWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		String valueRead = value.toString();
		try {
			if (valueRead.startsWith(Protocols.ACCURACY_STARTS_WITH)) {
				valueRead = valueRead.substring(Protocols.ACCURACY_STARTS_WITH
						.length());
				context.write(new Text(Protocols.ACCURACY_COUNT + "Accuracy"),
						new FloatWritable(Float.parseFloat(valueRead)));

				context.write(
						new Text(Protocols.ACCURACY_COUNT
								+ ((int) (Float.parseFloat(valueRead) * 10))
								+ ""),
						new FloatWritable(Float.parseFloat(valueRead)));

			} 
//			else if (valueRead.startsWith(Protocols.COLD_START_STARTS_WITH)) {
//				context.write(new Text(Protocols.COLD_START_COUNT),
//						new FloatWritable(1));
//				
////				String[] userAndFriendsList = valueRead.substring(Protocols.COLD_START_STARTS_WITH.length()).split(Protocols.VALUE_SEPARATOR);
////				
////				String[] friends = userAndFriendsList[1].substring(1, userAndFriendsList[1].length() - 1).split(Protocols.FRIEND_LIST_SEPARATOR);
////				
//////				FloatWritable userFloat = new FloatWritable(Float.parseFloat(userAndFriendsList[0]));
////				int userID = Integer.parseInt(userAndFriendsList[0]);
////				for(String eachUser : friends)
////				{
////					if(!eachUser.isEmpty())
////					{
////						context.write(new Text(Protocols.COLD_START_USERS_STARTS_WITH + eachUser.trim()), new FloatWritable(userID));
////					}
////				}
//				
//
//			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
