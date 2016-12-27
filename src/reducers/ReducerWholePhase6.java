package reducers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
//import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import utils.Protocols;

public class ReducerWholePhase6 extends
		Reducer<LongWritable, IntWritable, NullWritable, Text> {

	public static int count = 0;

	//
	// private static HashMap sortByIntegerValues(
	// HashMap<Integer, Integer> argumentMap) {
	// List list = new LinkedList(argumentMap.entrySet());
	// // Defined Custom Comparator here
	// Collections.sort(list, new Comparator() {
	// public int compare(Object o1, Object o2) {
	// return (((Comparable) ((Map.Entry) (o1)).getValue())
	// .compareTo(((Map.Entry) (o2)).getValue())) * -1;
	// }
	// });
	//
	// // Here I am copying the sorted list in HashMap
	// // using LinkedHashMap to preserve the insertion order
	// HashMap sortedHashMap = new LinkedHashMap();
	// for (Iterator it = list.iterator(); it.hasNext();) {
	// Map.Entry entry = (Map.Entry) it.next();
	// sortedHashMap.put(entry.getKey(), entry.getValue());
	// }
	// return sortedHashMap;
	// }
	// SortedMap<Integer, ArrayList<Integer>> mapOfUsers = new TreeMap<Integer,
	// ArrayList<Integer>>();
	@Override
	protected void reduce(LongWritable key, Iterable<IntWritable> allValues,
			Context context) throws IOException, InterruptedException {
		if (count != 10) {
			for (IntWritable values : allValues) {

				context.write(NullWritable.get(), new Text(key
						+ Protocols.USER_WEIGHT + values));
				count++;

			}
		}
		// context.write(NullWritable.get(), new Text(mapOfUsers.toString()));
		// TODO Auto-generated method stub
		// HashMap<Integer, Integer> countOfEachUser = new HashMap<Integer,
		// Integer>();
		// //
		// for (Text eachValue : allValues) {
		// String[] values = eachValue.toString().split(Protocols.USER_WEIGHT);
		//
		// int userID = Integer.parseInt(values[0]);
		// int weight = Integer.parseInt(values[1]);
		// if (countOfEachUser.containsKey(userID)) {
		// countOfEachUser.put(userID, countOfEachUser.get(userID)
		// + weight);
		// } else {
		// countOfEachUser.put(userID, weight);
		// }
		//
		// }
		// //
		// HashMap<Integer, Integer> sortedList =
		// sortByIntegerValues(countOfEachUser);
		// StringBuffer valueKey = new StringBuffer();
		// int count = 0;
		// for (Integer eachUser : sortedList.keySet()) {
		// if (count == Protocols.THRESHOLD_ON_COUNT_OF_USERS) {
		// break;
		// } else {
		// count++;
		// valueKey.append(eachUser + Protocols.USER_WEIGHT
		// + sortedList.get(eachUser)
		// + Protocols.FRIEND_LIST_SEPARATOR);
		//
		// }
		// }
		// //
		// if (!valueKey.toString().isEmpty()) {
		// context.write(NullWritable.get(), new
		// Text(Protocols.RECOMMEND_TO_NON_RECOMMENDED + valueKey.toString()));
		// }
		// //
		// // int count = 0;
		// // for (IntWritable counts : allValues) {
		// // count += counts.get();
		// // }
		// //
		// // if(count > Protocols.MINIMUM_WEIGHT_FOR_USER_REQUIRED)
		// // {
		// // context.write(NullWritable.get(), new Text(key +
		// // Protocols.USER_WEIGHT + count));
		// // }

	}
}
