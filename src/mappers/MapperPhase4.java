package mappers;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import utils.Protocols;

public class MapperPhase4 extends Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// String[] valueRead = value.toString().split(
		// Protocols.FRIEND_LIST_SEPARATOR);
		//
		// // if(valueRead.length>1)
		// // {
		// int size = valueRead.length;
		//
		// for (int outer = 0; outer < size; outer++) {
		// String outerString = valueRead[outer];
		// for (int inner = outer + 1; inner < size; inner++) {
		// String innerString = valueRead[inner];
		// context.write(new Text(outerString
		// + Protocols.USER_USER_SEPARATOR + innerString),
		// NullWritable.get());
		// }
		// }

		String values = value.toString().substring(
				0,
				value.toString().length()
						- Protocols.FRIEND_LIST_SEPARATOR.length());

		String[] valueRead = values.split(Protocols.FRIEND_LIST_SEPARATOR);

		// if(valueRead.length>1)
		// {
		int size = valueRead.length;

		for (int outer = 0; outer < size; outer++) {
			String outerString = valueRead[outer];
			for (int inner = outer + 1; inner < size; inner++) {
				String innerString = valueRead[inner];
				context.write(new Text(outerString
						+ Protocols.USER_USER_SEPARATOR + innerString),
						new IntWritable(1));
			}
		}

		// }

	}
}
