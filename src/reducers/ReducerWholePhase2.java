package reducers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class ReducerWholePhase2 extends
		Reducer<IntWritable, IntWritable, NullWritable, Text> {
	@Override
	protected void reduce(IntWritable key, Iterable<IntWritable> allValues,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		ArrayList<Integer> listOfFriends = new ArrayList<Integer>();

		for (IntWritable friendsOfKey : allValues) {
			listOfFriends.add(friendsOfKey.get());
		}

		float sizeOfList = listOfFriends.size();
//		Collections.shuffle(listOfFriends);
		List<Integer> training;
//		List<Integer> testing;
//		if(listOfFriends.size() !=1)
//		{
//			training = listOfFriends.subList(0,
//				(int) (sizeOfList * Protocols.TRAINING_TESTING));
//			testing = listOfFriends.subList(
//				(int) (sizeOfList * Protocols.TRAINING_TESTING),
//				(int) sizeOfList);
//		}
//		else
//		{
			training = listOfFriends;
//			testing = new ArrayList<Integer>();
//		}
		StringBuffer trainingBuffer = new StringBuffer();
		for (Integer eachUser : training) {
			trainingBuffer.append(eachUser + Protocols.FRIEND_LIST_SEPARATOR);
		}
		if (!trainingBuffer.toString().isEmpty()) {
			context.write(
					NullWritable.get(),
					new Text(Protocols.TRAINING_DATASET_STARTS_WITH + key
							+ Protocols.FRIEND_EDGE_SPLIT
							+ trainingBuffer.toString()));
		}

//		StringBuffer testingBuffer = new StringBuffer();
//		for (Integer eachUser : testing) {
//			testingBuffer.append(eachUser + Protocols.FRIEND_LIST_SEPARATOR);
//		}
//		if (!testingBuffer.toString().isEmpty()) {
//			context.write(
//					NullWritable.get(),
//					new Text(Protocols.TESTING_DATASET_STARTS_WITH + key
//							+ Protocols.FRIEND_EDGE_SPLIT
//							+ testingBuffer.toString()));
//		}

	}
}
