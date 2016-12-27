package reducers;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class ReducerWholeRecommendPhase5 extends
		Reducer<IntWritable, Text, NullWritable, Text> {
	

	@Override
	protected void reduce(IntWritable key, Iterable<Text> allValues,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		HashSet<Integer> recommendFriendInfo = new HashSet<Integer>();
		HashSet<Integer> myFriends = new HashSet<Integer>();
//		HashSet<Integer> testFriends = new HashSet<Integer>();

		for (Text eachValue : allValues) {
			String eachValueString = eachValue.toString();

			if (eachValueString
					.startsWith(Protocols.RECOMMEND_PHASE_2_FRIENDS_STARTS_WITH)) {
				eachValueString = eachValueString
						.substring(Protocols.RECOMMEND_PHASE_2_FRIENDS_STARTS_WITH
								.length());
				myFriends.add(Integer.parseInt(eachValueString));
			} else if (eachValueString
					.startsWith(Protocols.RECOMMEND_PHASE_2_TEST_FRIENDS_STARTS_WITH)) {
//				eachValueString = eachValueString
//						.substring(Protocols.RECOMMEND_PHASE_2_TEST_FRIENDS_STARTS_WITH
//								.length());
//				testFriends.add(Integer.parseInt(eachValueString));
			} else {
				recommendFriendInfo.add(Integer.parseInt(eachValueString));

			}
		}

		recommendFriendInfo.remove(key.get());
		for (Integer eachMyFriend : myFriends) {
			recommendFriendInfo.remove(eachMyFriend);
		}
//		int count = 0;
//		for (Integer eachTestInteger : testFriends) {
//			if (recommendFriendInfo.contains(eachTestInteger)) {
//				count++;
//			}
//		}
		// context.write(NullWritable.get(), new Text("Count: " + count +
		// " Out Of: " + testFriends.size() + " Accuracy: " + (float) count*1.0f
		// / testFriends.size()));
		// if(count>0)
		// {
		// context.write(NullWritable.get(), new Text("TRUE"));
		// }

		if (recommendFriendInfo.size() == 0) {
//			context.write(NullWritable.get(), new Text(
//					Protocols.COLD_START_STARTS_WITH + key
//							+ Protocols.VALUE_SEPARATOR + myFriends));
		} else {
			context.write(NullWritable.get(), new Text(
					Protocols.RECOMMEND_LIST_STARTS_WITH + key
							+ Protocols.VALUE_SEPARATOR
							+ recommendFriendInfo.toString()));

		}
//		if (testFriends.size() == 0) {
////			context.write(NullWritable.get(), new Text(Protocols.NO_TEST_DATA
////					+ key));
////			context.write(NullWritable.get(), new Text(
////					Protocols.ACCURACY_STARTS_WITH + "1.0"));
//
//		}
//		
//		int din = testFriends.size();
//		if(recommendFriendInfo.size() < testFriends.size())
//		{
//			din = recommendFriendInfo.size();
//		}
//		if (recommendFriendInfo.size() != 0 && testFriends.size() != 0) {
//			context.write(NullWritable.get(), new Text(
//					Protocols.ACCURACY_STARTS_WITH + (float) count * 1.0f
//							/ din));
//
//		}
		// context.write(NullWritable.get(), new Text(key +
		// Protocols.VALUE_SEPARATOR + result));

	}
}
