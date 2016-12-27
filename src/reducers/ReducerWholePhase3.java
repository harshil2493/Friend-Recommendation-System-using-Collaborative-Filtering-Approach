package reducers;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class ReducerWholePhase3 extends
		Reducer<IntWritable, IntWritable, NullWritable, Text> {
	@Override
	protected void reduce(IntWritable key, Iterable<IntWritable> allValues,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		ArrayList<Integer> listOfFriends = new ArrayList<Integer>();

		for (IntWritable friendsOfKey : allValues) {
			listOfFriends.add(friendsOfKey.get());
		}

		// int size = listOfFriends.size();
		//
		// for (int outer = 0; outer < size; outer++) {
		// int outerString = listOfFriends.get(outer);
		// for (int inner = 1; inner < size; inner++) {
		// int innerString = listOfFriends.get(inner);
		// context.write(NullWritable.get(), new Text(outerString
		// + Protocols.USER_USER_SEPARATOR + innerString));
		// }
		// }
		StringBuffer buffer = new StringBuffer();
		for (Integer eachUser : listOfFriends) {
			buffer.append(eachUser + Protocols.FRIEND_LIST_SEPARATOR);
		}
		context.write(NullWritable.get(), new Text(buffer.toString()));
	}
}
