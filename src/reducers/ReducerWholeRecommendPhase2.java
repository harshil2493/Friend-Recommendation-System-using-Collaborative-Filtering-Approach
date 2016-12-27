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

public class ReducerWholeRecommendPhase2 extends
		Reducer<IntWritable, Text, NullWritable, Text> {
	private static HashMap sortByIntegerValues(
			HashMap<Integer, Integer> argumentMap) {
		List list = new LinkedList(argumentMap.entrySet());
		// Defined Custom Comparator here
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return (((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue())) * -1;
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	@Override
	protected void reduce(IntWritable key, Iterable<Text> allValues,
			Context context) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		HashMap<Integer, Integer> recommendFriendInfo = new HashMap<Integer, Integer>();
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
				String[] recommendFriendAndWeight = eachValueString
						.split(Protocols.USER_WEIGHT);
				int recommendFriend = Integer
						.parseInt(recommendFriendAndWeight[0]);
				int weight = Integer.parseInt(recommendFriendAndWeight[1]);

				if (recommendFriendInfo.containsKey(recommendFriend)) {
					recommendFriendInfo.put(recommendFriend,
							recommendFriendInfo.get(recommendFriend) + weight);

				} else {
					recommendFriendInfo.put(recommendFriend, weight);
				}

			}
		}

		recommendFriendInfo.remove(key.get());
		for (Integer eachMyFriend : myFriends) {
			recommendFriendInfo.remove(eachMyFriend);
		}
//		int count = 0;
//		for (Integer eachTestInteger : testFriends) {
//			if (recommendFriendInfo.containsKey(eachTestInteger)) {
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

		recommendFriendInfo = sortByIntegerValues(recommendFriendInfo);
		if (recommendFriendInfo.size() == 0) {
			context.write(NullWritable.get(), new Text(
					Protocols.COLD_START_STARTS_WITH + key
							+ Protocols.VALUE_SEPARATOR + myFriends));
		} else {
			context.write(NullWritable.get(), new Text(
					Protocols.RECOMMEND_LIST_STARTS_WITH + key
							+ Protocols.VALUE_SEPARATOR
							+ recommendFriendInfo.keySet().toString()));

		}
//		if (testFriends.size() == 0) {
//			context.write(NullWritable.get(), new Text(Protocols.NO_TEST_DATA
//					+ key));
//			context.write(NullWritable.get(), new Text(
//					Protocols.ACCURACY_STARTS_WITH + "1.0"));
//
//		}
//		
//		int din = testFriends.size();
//		if(recommendFriendInfo.size() < testFriends.size())
//		{
//			din = recommendFriendInfo.size();
//		}
//		
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
