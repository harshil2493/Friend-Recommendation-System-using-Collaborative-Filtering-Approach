package jobs;

import java.io.IOException;

import mappers.AnalysisMapperPhase0;
import mappers.AnalysisMapperPhase1;
import mappers.AnalysisMapperPhase3;
import mappers.AnalysisMapperPhase4;
import mappers.MapperPhase1;
import mappers.MapperPhase2;
import mappers.MapperPhase3;
import mappers.MapperPhase4;
import mappers.MapperPhase5;
import mappers.MapperRecommendPhase1;
import mappers.MapperRecommendPhase2;
import mappers.MapperRecommendPhase3;
import mappers.MapperRecommendPhase4;
import mappers.MapperRecommendPhase5;
import mappers.MapperRecommendPhase6;
import mappers.MapperWholePhase1;
import mappers.MapperWholePhase2;
import mappers.MapperWholePhase3;
import mappers.MapperWholePhase4;
import mappers.MapperWholePhase5;
import mappers.MapperWholePhase6;
import mappers.MapperWholeRecommendPhase1;
import mappers.MapperWholeRecommendPhase2;
import mappers.MapperWholeRecommendPhase4;
import mappers.MapperWholeRecommendPhase5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import reducers.AnalysisReducerPhase0;
import reducers.AnalysisReducerPhase1;
import reducers.AnalysisReducerPhase3;
import reducers.AnalysisReducerPhase4;
import reducers.ReducerPhase1;
import reducers.ReducerPhase2;
import reducers.ReducerPhase3;
import reducers.ReducerPhase4;
import reducers.ReducerPhase5;
import reducers.ReducerRecommendPhase1;
import reducers.ReducerRecommendPhase2;
import reducers.ReducerRecommendPhase3;
import reducers.ReducerRecommendPhase4;
import reducers.ReducerRecommendPhase5;
import reducers.ReducerRecommendPhase6;
import reducers.ReducerWholePhase1;
import reducers.ReducerWholePhase2;
import reducers.ReducerWholePhase3;
import reducers.ReducerWholePhase4;
import reducers.ReducerWholePhase5;
import reducers.ReducerWholePhase6;
import reducers.ReducerWholeRecommendPhase1;
import reducers.ReducerWholeRecommendPhase2;
import reducers.ReducerWholeRecommendPhase4;
import reducers.ReducerWholeRecommendPhase5;

import combiners.AnalysisCombinerPhase0;
import combiners.AnalysisCombinerPhase1;
import combiners.CombinerPhase4;
import combiners.CombinerWholePhase4;

public class MainJob {
	public static void main(String[] args) throws IOException,
			ClassNotFoundException, InterruptedException {
		if (args[1].endsWith("/")) {
			System.out.println("Removing / In The End Of Second Argument");
			args[1] = args[1].substring(0, args[1].length() - 1);
		}

		boolean accuracyPurpose = true;
		System.out.println("\nFor Calculation Of Accuracy: " + accuracyPurpose);

		boolean wholeDataScanningAndRecommendation = true;
		System.out.println("\nFor Whole Data Scanning And Recommendation: "
				+ wholeDataScanningAndRecommendation);

		if (accuracyPurpose) {
			// Removing Unwanted Users
			boolean phase1 = true;
			// Creating Training_Testing 70-30
			boolean phase2 = true;
			// Creating Similarity Matrix Phase 1
			boolean phase3 = true;
			// Creating Similarity Matrix Phase 2
			boolean phase4 = true;
			// Creating Similarity Matrix Phase 2
			boolean phase5 = true;
			String outputOfPhase1 = "";
			String outputOfPhase2 = "";
			String outputOfPhase3 = "";
			String outputOfPhase4 = "";
			String outputOfPhase5 = "";

			System.out.println("\nInitial Phase One: " + phase1);
			System.out.println("\nInitial Phase Two: " + phase2);
			System.out.println("\nInitial Phase Three: " + phase3);
			System.out.println("\nInitial Phase Four: " + phase4);
			System.out.println("\nInitial Phase Five: " + phase5);

			if (phase1) {
				System.out.println("****Removing_Unwanted Users****");
				Configuration configurationPhase1 = new Configuration();
				FileSystem fileSystemPhase1 = FileSystem
						.get(configurationPhase1);

				Job jobPhase1 = Job.getInstance(configurationPhase1,
						"FriendSter_hkshah_phase1_removing_unwanted_users");

				jobPhase1.setJarByClass(MainJob.class);

				jobPhase1.setNumReduceTasks(40);

				jobPhase1.setMapperClass(MapperPhase1.class);

				jobPhase1.setReducerClass(ReducerPhase1.class);

				jobPhase1.setMapOutputKeyClass(IntWritable.class);
				jobPhase1.setMapOutputValueClass(Text.class);

				jobPhase1.setOutputKeyClass(NullWritable.class);
				jobPhase1.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase One: " + args[0]
						+ "****");

				FileInputFormat.addInputPath(jobPhase1, new Path(args[0]));

				outputOfPhase1 = args[1] + "_FriendSter_Phase_One";

				if (fileSystemPhase1.exists(new Path(outputOfPhase1)))
					fileSystemPhase1.delete(new Path(outputOfPhase1), true);

				FileOutputFormat.setOutputPath(jobPhase1, new Path(
						outputOfPhase1));
				jobPhase1.waitForCompletion(true);
				System.out.println("****Output Of Phase One: " + outputOfPhase1
						+ "****");
			} else {
				outputOfPhase1 = args[1] + "_FriendSter_Phase_One";
			}

			System.out.println("****Output Of Phase 1 :" + outputOfPhase1
					+ "****");

			if (phase2) {
				System.out.println("****Creating_Training_Testing_Dataset****");
				Configuration configurationPhase2 = new Configuration();
				FileSystem fileSystemPhase2 = FileSystem
						.get(configurationPhase2);

				Job jobPhase2 = Job.getInstance(configurationPhase2,
						"FriendSter_hkshah_phase_2_training_testing");

				jobPhase2.setJarByClass(MainJob.class);

				jobPhase2.setNumReduceTasks(60);

				jobPhase2.setMapperClass(MapperPhase2.class);

				jobPhase2.setReducerClass(ReducerPhase2.class);

				jobPhase2.setMapOutputKeyClass(IntWritable.class);
				jobPhase2.setMapOutputValueClass(IntWritable.class);

				jobPhase2.setOutputKeyClass(NullWritable.class);
				jobPhase2.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase Two: " + outputOfPhase1
						+ "****");

				FileInputFormat.addInputPath(jobPhase2,
						new Path(outputOfPhase1));

				outputOfPhase2 = args[1] + "_FriendSter_Phase_Two";

				if (fileSystemPhase2.exists(new Path(outputOfPhase2)))
					fileSystemPhase2.delete(new Path(outputOfPhase2), true);

				FileOutputFormat.setOutputPath(jobPhase2, new Path(
						outputOfPhase2));
				jobPhase2.waitForCompletion(true);
				System.out.println("****Output Of Phase Two: " + outputOfPhase2
						+ "****");
			} else {
				outputOfPhase2 = args[1] + "_FriendSter_Phase_Two";
			}

			System.out.println("****Output Of Phase 2 :" + outputOfPhase2
					+ "****");
			// Get U U Count Based On Right Side U
			if (phase3) {
				System.out
						.println("****Creating_User_User_Similarity_Matrix_Phase_One****");
				Configuration configurationPhase3 = new Configuration();
				FileSystem fileSystemPhase3 = FileSystem
						.get(configurationPhase3);

				// System.out.println("****DELETING ORIGINAL DATASET****" +
				// args[0]);
				// fileSystemPhase3.delete(new Path(args[0]), true);

				Job jobPhase3 = Job
						.getInstance(configurationPhase3,
								"FriendSter_hkshah_phase3_user_user_similarity_matrix_phase1");

				jobPhase3.setJarByClass(MainJob.class);

				jobPhase3.setNumReduceTasks(200);

				jobPhase3.setMapperClass(MapperPhase3.class);

				jobPhase3.setReducerClass(ReducerPhase3.class);

				jobPhase3.setMapOutputKeyClass(IntWritable.class);
				jobPhase3.setMapOutputValueClass(IntWritable.class);

				jobPhase3.setOutputKeyClass(NullWritable.class);
				jobPhase3.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase Three: "
						+ outputOfPhase2 + "****");

				FileInputFormat.addInputPath(jobPhase3,
						new Path(outputOfPhase2));

				outputOfPhase3 = args[1] + "_FriendSter_Phase_Three";

				if (fileSystemPhase3.exists(new Path(outputOfPhase3)))
					fileSystemPhase3.delete(new Path(outputOfPhase3), true);

				FileOutputFormat.setOutputPath(jobPhase3, new Path(
						outputOfPhase3));
				jobPhase3.waitForCompletion(true);
				System.out.println("****Output Of Phase Three: "
						+ outputOfPhase3 + "****");
			} else {
				outputOfPhase3 = args[1] + "_FriendSter_Phase_Three";
			}

			System.out.println("****Output Of Phase 3 :" + outputOfPhase3
					+ "****");
			// Commend It
			// String temporaryInputPath1 = outputOfPhase3 + "/part-r-00000";
			// // String temporaryInputPath2 = outputOfPhase3 + "/part-r-00001";
			// System.out.println("TEMPORARY: " + temporaryInputPath1);
			if (phase4) {
				System.out
						.println("****Creating_User_User_Similarity_Matrix_Phase_Two****");
				Configuration configurationPhase4 = new Configuration();
				FileSystem fileSystemPhase4 = FileSystem
						.get(configurationPhase4);

				// System.out.println("****DELETING PHASE ONE****" +
				// outputOfPhase1);
				// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

				Job jobPhase4 = Job
						.getInstance(configurationPhase4,
								"FriendSter_hkshah_phase4_user_user_similarity_matrix_phase2");

				jobPhase4.setJarByClass(MainJob.class);

				jobPhase4.setNumReduceTasks(80);

				jobPhase4.setMapperClass(MapperPhase4.class);
				jobPhase4.setCombinerClass(CombinerPhase4.class);

				jobPhase4.setReducerClass(ReducerPhase4.class);

				jobPhase4.setMapOutputKeyClass(Text.class);
				jobPhase4.setMapOutputValueClass(IntWritable.class);

				jobPhase4.setOutputKeyClass(NullWritable.class);
				jobPhase4.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase Four: " + outputOfPhase3
						+ "****");

				FileInputFormat.addInputPath(jobPhase4,
						new Path(outputOfPhase3));

				// System.out.println("****Input Of Phase Four: " +
				// temporaryInputPath2
				// + "****");
				//
				// FileInputFormat.addInputPath(jobPhase4, new
				// Path(temporaryInputPath2));

				outputOfPhase4 = args[1] + "_FriendSter_Phase_Four";

				if (fileSystemPhase4.exists(new Path(outputOfPhase4)))
					fileSystemPhase4.delete(new Path(outputOfPhase4), true);

				FileOutputFormat.setOutputPath(jobPhase4, new Path(
						outputOfPhase4));
				jobPhase4.waitForCompletion(true);
				System.out.println("****Output Of Phase Four: "
						+ outputOfPhase4 + "****");
			} else {
				outputOfPhase4 = args[1] + "_FriendSter_Phase_Four";
			}

			System.out.println("****Output Of Phase 4 :" + outputOfPhase4
					+ "****");

			if (phase5) {
				System.out
						.println("****Creating_User_User_Similarity_Matrix_Phase_Three****");
				Configuration configurationPhase5 = new Configuration();
				FileSystem fileSystemPhase5 = FileSystem
						.get(configurationPhase5);

				Job jobPhase4 = Job.getInstance(configurationPhase5,
						"FriendSter_hkshah_user_user_similarity_matrix_phase3");

				jobPhase4.setJarByClass(MainJob.class);

				jobPhase4.setNumReduceTasks(60);

				jobPhase4.setMapperClass(MapperPhase5.class);
				// jobPhase4.setCombinerClass(CombinerPhase5.class);
				jobPhase4.setReducerClass(ReducerPhase5.class);

				jobPhase4.setMapOutputKeyClass(IntWritable.class);
				jobPhase4.setMapOutputValueClass(Text.class);

				jobPhase4.setOutputKeyClass(NullWritable.class);
				jobPhase4.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase Five: " + outputOfPhase4
						+ "****");

				FileInputFormat.addInputPath(jobPhase4,
						new Path(outputOfPhase4));

				outputOfPhase5 = args[1] + "_FriendSter_Phase_Five";

				if (fileSystemPhase5.exists(new Path(outputOfPhase5)))
					fileSystemPhase5.delete(new Path(outputOfPhase5), true);

				FileOutputFormat.setOutputPath(jobPhase4, new Path(
						outputOfPhase5));
				jobPhase4.waitForCompletion(true);
				System.out.println("****Output Of Phase Five: "
						+ outputOfPhase5 + "****");
			} else {
				outputOfPhase5 = args[1] + "_FriendSter_Phase_Five";
			}

			System.out.println("****Output Of Phase 5:" + outputOfPhase5
					+ "****");

			System.out.println("\n\n");
			System.out.println("****Similarity Matrix Location: "
					+ outputOfPhase5 + "****");
			System.out.println("****User_Friend_Location: " + outputOfPhase2
					+ "****");
			System.out.println("\n\n");

			String userSimilarityMatrix = outputOfPhase5;
			String userFriendList = outputOfPhase2;

			boolean recommendPhase = true;
			System.out.println("Recommendation Phase: " + recommendPhase);

			if (recommendPhase) {
				System.out.println("****Running Recommend Phase****");
				boolean recommendPhase1 = true;
				boolean recommendPhase2 = true;
				boolean recommendPhase3 = true;
				boolean recommendPhase4 = true;
				boolean recommendPhase5 = true;
				boolean recommendPhase6 = true;
				boolean recommendPhase7 = true;

				String outputOfRecommendPhase1 = "";
				String outputOfRecommendPhase2 = "";
				String outputOfRecommendPhase3 = "";
				String outputOfRecommendPhase4 = "";
				String outputOfRecommendPhase5 = "";
				String outputOfRecommendPhase6 = "";
				String outputOfRecommendPhase7 = "";

				System.out
						.println("****Recommend Phase One:" + recommendPhase1);
				System.out
						.println("****Recommend Phase Two:" + recommendPhase2);

				System.out.println("****Recommend Phase Three:"
						+ recommendPhase3);
				System.out.println("****Recommend Phase Four:"
						+ recommendPhase4);

				System.out.println("****Recommend Phase Five:"
						+ recommendPhase5);
				System.out
						.println("****Recommend Phase Six:" + recommendPhase6);
				System.out.println("****Recommend Phase Seven:"
						+ recommendPhase7);
				if (recommendPhase1) {
					System.out.println("\n****Recommend Phase 1****");

					Configuration configurationRecommendPhase1 = new Configuration();
					FileSystem fileSystemRecommendPhase1 = FileSystem
							.get(configurationRecommendPhase1);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase1 = Job.getInstance(
							configurationRecommendPhase1,
							"FriendSter_hkshah_recommend_phase_1");

					jobRecommendPhase1.setJarByClass(MainJob.class);

					jobRecommendPhase1.setNumReduceTasks(200);

					jobRecommendPhase1
							.setMapperClass(MapperRecommendPhase1.class);

					// jobRecommendPhase1.setInputFormatClass(NLineInputFormat.class);
					// jobRecommendPhase1.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap",
					// 15000);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);

					jobRecommendPhase1
							.setReducerClass(ReducerRecommendPhase1.class);

					jobRecommendPhase1.setMapOutputKeyClass(IntWritable.class);
					jobRecommendPhase1.setMapOutputValueClass(Text.class);

					jobRecommendPhase1.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase1.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase One: "
							+ userSimilarityMatrix + "****");

					FileInputFormat.addInputPath(jobRecommendPhase1, new Path(
							userSimilarityMatrix));
					System.out.println("****Input Of Recommend Phase One: "
							+ userFriendList + "****");

					FileInputFormat.addInputPath(jobRecommendPhase1, new Path(
							userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase1 = args[1]
							+ "_FriendSter_Recommend_Phase_One";

					if (fileSystemRecommendPhase1.exists(new Path(
							outputOfRecommendPhase1)))
						fileSystemRecommendPhase1.delete(new Path(
								outputOfRecommendPhase1), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase1,
							new Path(outputOfRecommendPhase1));
					jobRecommendPhase1.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase One: "
							+ outputOfRecommendPhase1 + "****");
				} else {
					outputOfRecommendPhase1 = args[1]
							+ "_FriendSter_Recommend_Phase_One";

				}
				System.out.println("\nRecommend Phase One Output: "
						+ outputOfRecommendPhase1);
				// Commend Below
				// outputOfRecommendPhase1 = outputOfRecommendPhase1 +
				// "/part-r-0000*";
				// System.out.println("Temporary Location: " +
				// outputOfRecommendPhase1);
				if (recommendPhase2) {
					System.out.println("\n****Recommend Phase 2****");

					Configuration configurationRecommendPhase2 = new Configuration();
					FileSystem fileSystemRecommendPhase2 = FileSystem
							.get(configurationRecommendPhase2);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase2 = Job.getInstance(
							configurationRecommendPhase2,
							"FriendSter_hkshah_recommend_phase_2");

					jobRecommendPhase2.setJarByClass(MainJob.class);

					jobRecommendPhase2.setNumReduceTasks(600);

					jobRecommendPhase2
							.setMapperClass(MapperRecommendPhase2.class);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);

					jobRecommendPhase2
							.setReducerClass(ReducerRecommendPhase2.class);

					jobRecommendPhase2.setMapOutputKeyClass(IntWritable.class);
					jobRecommendPhase2.setMapOutputValueClass(Text.class);

					jobRecommendPhase2.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase2.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase Two: "
							+ outputOfRecommendPhase1 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase2, new Path(
							outputOfRecommendPhase1));
					System.out.println("****Input Of Recommend Phase Two: "
							+ userFriendList + "****");

					FileInputFormat.addInputPath(jobRecommendPhase2, new Path(
							userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase2 = args[1]
							+ "_FriendSter_Recommend_Phase_Two";

					if (fileSystemRecommendPhase2.exists(new Path(
							outputOfRecommendPhase2)))
						fileSystemRecommendPhase2.delete(new Path(
								outputOfRecommendPhase2), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase2,
							new Path(outputOfRecommendPhase2));
					jobRecommendPhase2.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase Two: "
							+ outputOfRecommendPhase2 + "****");

				} else {
					outputOfRecommendPhase2 = args[1]
							+ "_FriendSter_Recommend_Phase_Two";
					// /friendSterPhase_FriendSter_Recommend_Phase_Two
				}
				System.out.println("\nRecommend Phase Two Output: "
						+ outputOfRecommendPhase2);

				if (recommendPhase3) {
					System.out.println("\n****Recommend Phase 3****");

					Configuration configurationRecommendPhase3 = new Configuration();
					FileSystem fileSystemRecommendPhase3 = FileSystem
							.get(configurationRecommendPhase3);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase3 = Job.getInstance(
							configurationRecommendPhase3,
							"FriendSter_hkshah_recommend_phase_3");

					jobRecommendPhase3.setJarByClass(MainJob.class);

					// jobRecommendPhase3.setNumReduceTasks(15);

					jobRecommendPhase3
							.setMapperClass(MapperRecommendPhase3.class);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);
					// jobRecommendPhase3
					// .setPartitionerClass(PartitionerRecommendPhase3.class);
					jobRecommendPhase3
							.setReducerClass(ReducerRecommendPhase3.class);

					jobRecommendPhase3.setMapOutputKeyClass(Text.class);
					jobRecommendPhase3
							.setMapOutputValueClass(FloatWritable.class);

					jobRecommendPhase3.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase3.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase Three: "
							+ outputOfRecommendPhase2 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase3, new Path(
							outputOfRecommendPhase2));
					// System.out.println("****Input Of Recommend Phase Two: " +
					// userFriendList
					// + "****");
					//
					// FileInputFormat.addInputPath(jobRecommendPhase2, new
					// Path(userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase3 = args[1]
							+ "_FriendSter_Recommend_Phase_Three";

					if (fileSystemRecommendPhase3.exists(new Path(
							outputOfRecommendPhase3)))
						fileSystemRecommendPhase3.delete(new Path(
								outputOfRecommendPhase3), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase3,
							new Path(outputOfRecommendPhase3));
					jobRecommendPhase3.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase Three: "
							+ outputOfRecommendPhase3 + "****");

				} else {
					outputOfRecommendPhase3 = args[1]
							+ "_FriendSter_Recommend_Phase_Three";

				}
				System.out.println("\nRecommend Phase Three Output: "
						+ outputOfRecommendPhase3);

				if (recommendPhase4) {
					System.out.println("\n****Recommend Phase 4****");

					Configuration configurationRecommendPhase4 = new Configuration();
					FileSystem fileSystemRecommendPhase4 = FileSystem
							.get(configurationRecommendPhase4);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase4 = Job.getInstance(
							configurationRecommendPhase4,
							"FriendSter_hkshah_recommend_phase_4");

					jobRecommendPhase4.setJarByClass(MainJob.class);

					jobRecommendPhase4.setNumReduceTasks(60);

					jobRecommendPhase4
							.setMapperClass(MapperRecommendPhase4.class);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);
					// jobRecommendPhase3
					// .setPartitionerClass(PartitionerRecommendPhase3.class);
					jobRecommendPhase4
							.setReducerClass(ReducerRecommendPhase4.class);

					jobRecommendPhase4.setMapOutputKeyClass(IntWritable.class);
					jobRecommendPhase4.setMapOutputValueClass(Text.class);

					jobRecommendPhase4.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase4.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase Four: "
							+ outputOfRecommendPhase2 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase4, new Path(
							outputOfRecommendPhase2));

					System.out.println("****Input Of Recommend Phase Four: "
							+ userFriendList + "****");

					FileInputFormat.addInputPath(jobRecommendPhase4, new Path(
							userFriendList));
					// System.out.println("****Input Of Recommend Phase Two: " +
					// userFriendList
					// + "****");
					//
					// FileInputFormat.addInputPath(jobRecommendPhase2, new
					// Path(userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase4 = args[1]
							+ "_FriendSter_Recommend_Phase_Four";

					if (fileSystemRecommendPhase4.exists(new Path(
							outputOfRecommendPhase4)))
						fileSystemRecommendPhase4.delete(new Path(
								outputOfRecommendPhase4), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase4,
							new Path(outputOfRecommendPhase4));
					jobRecommendPhase4.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase Four: "
							+ outputOfRecommendPhase4 + "****");

				} else {
					outputOfRecommendPhase4 = args[1]
							+ "_FriendSter_Recommend_Phase_Four";

				}
				System.out.println("\nRecommend Phase Four Output: "
						+ outputOfRecommendPhase4);

				if (recommendPhase5) {
					System.out.println("\n****Recommend Phase 5****");

					Configuration configurationRecommendPhase5 = new Configuration();
					FileSystem fileSystemRecommendPhase5 = FileSystem
							.get(configurationRecommendPhase5);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase5 = Job.getInstance(
							configurationRecommendPhase5,
							"FriendSter_hkshah_recommend_phase_5");

					jobRecommendPhase5.setJarByClass(MainJob.class);

					jobRecommendPhase5.setNumReduceTasks(60);

					jobRecommendPhase5
							.setMapperClass(MapperRecommendPhase5.class);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);
					// jobRecommendPhase3
					// .setPartitionerClass(PartitionerRecommendPhase3.class);
					jobRecommendPhase5
							.setReducerClass(ReducerRecommendPhase5.class);

					jobRecommendPhase5.setMapOutputKeyClass(IntWritable.class);
					jobRecommendPhase5.setMapOutputValueClass(Text.class);

					jobRecommendPhase5.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase5.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase Five: "
							+ outputOfRecommendPhase4 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase5, new Path(
							outputOfRecommendPhase4));

					System.out.println("****Input Of Recommend Phase Five: "
							+ userFriendList + "****");

					FileInputFormat.addInputPath(jobRecommendPhase5, new Path(
							userFriendList));

					// System.out.println("****Input Of Recommend Phase Four: "
					// + userFriendList + "****");
					//
					// FileInputFormat.addInputPath(jobRecommendPhase5, new
					// Path(
					// userFriendList));
					// System.out.println("****Input Of Recommend Phase Two: " +
					// userFriendList
					// + "****");
					//
					// FileInputFormat.addInputPath(jobRecommendPhase2, new
					// Path(userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase5 = args[1]
							+ "_FriendSter_Recommend_Phase_Five";

					if (fileSystemRecommendPhase5.exists(new Path(
							outputOfRecommendPhase5)))
						fileSystemRecommendPhase5.delete(new Path(
								outputOfRecommendPhase5), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase5,
							new Path(outputOfRecommendPhase5));
					jobRecommendPhase5.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase Five: "
							+ outputOfRecommendPhase5 + "****");

				} else {
					outputOfRecommendPhase5 = args[1]
							+ "_FriendSter_Recommend_Phase_Five";

				}
				System.out.println("\nRecommend Phase Five Output: "
						+ outputOfRecommendPhase5);
				if (recommendPhase6) {
					System.out.println("\n****Recommend Phase 6****");

					Configuration configurationRecommendPhase6 = new Configuration();
					FileSystem fileSystemRecommendPhase6 = FileSystem
							.get(configurationRecommendPhase6);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase6 = Job.getInstance(
							configurationRecommendPhase6,
							"FriendSter_hkshah_recommend_phase_6");

					jobRecommendPhase6.setJarByClass(MainJob.class);

					// jobRecommendPhase3.setNumReduceTasks(15);

					jobRecommendPhase6
							.setMapperClass(MapperRecommendPhase6.class);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);
					// jobRecommendPhase3
					// .setPartitionerClass(PartitionerRecommendPhase3.class);
					jobRecommendPhase6
							.setReducerClass(ReducerRecommendPhase6.class);

					jobRecommendPhase6.setMapOutputKeyClass(Text.class);
					jobRecommendPhase6
							.setMapOutputValueClass(FloatWritable.class);

					jobRecommendPhase6.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase6.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase Six: "
							+ outputOfRecommendPhase5 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase6, new Path(
							outputOfRecommendPhase5));
					// System.out.println("****Input Of Recommend Phase Two: " +
					// userFriendList
					// + "****");
					//
					// FileInputFormat.addInputPath(jobRecommendPhase2, new
					// Path(userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase6 = args[1]
							+ "_FriendSter_Recommend_Phase_Six";

					if (fileSystemRecommendPhase6.exists(new Path(
							outputOfRecommendPhase6)))
						fileSystemRecommendPhase6.delete(new Path(
								outputOfRecommendPhase6), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase6,
							new Path(outputOfRecommendPhase6));
					jobRecommendPhase6.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase Six: "
							+ outputOfRecommendPhase6 + "****");

				} else {
					outputOfRecommendPhase6 = args[1]
							+ "_FriendSter_Recommend_Phase_Six";

				}
				System.out.println("\nRecommend Phase Six Output: "
						+ outputOfRecommendPhase6);

				if (recommendPhase7) {
					System.out.println("\n****Recommend Phase 7****");

					Configuration configurationRecommendPhase7 = new Configuration();
					FileSystem fileSystemRecommendPhase7 = FileSystem
							.get(configurationRecommendPhase7);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase7 = Job.getInstance(
							configurationRecommendPhase7,
							"FriendSter_hkshah_recommend_phase_7");

					jobRecommendPhase7.setJarByClass(MainJob.class);

					// jobRecommendPhase3.setNumReduceTasks(15);

					jobRecommendPhase7
							.setMapperClass(MapperRecommendPhase6.class);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);
					// jobRecommendPhase3
					// .setPartitionerClass(PartitionerRecommendPhase3.class);
					jobRecommendPhase7
							.setReducerClass(ReducerRecommendPhase6.class);

					jobRecommendPhase7.setMapOutputKeyClass(Text.class);
					jobRecommendPhase7
							.setMapOutputValueClass(FloatWritable.class);

					jobRecommendPhase7.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase7.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase Six: "
							+ outputOfRecommendPhase5 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase7, new Path(
							outputOfRecommendPhase5));
					System.out.println("****Input Of Recommend Phase Six: "
							+ outputOfRecommendPhase2 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase7, new Path(
							outputOfRecommendPhase2));
					// outputOfRecommendPhase2
					// System.out.println("****Input Of Recommend Phase Two: " +
					// userFriendList
					// + "****");
					//
					// FileInputFormat.addInputPath(jobRecommendPhase2, new
					// Path(userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase7 = args[1]
							+ "_FriendSter_Recommend_Phase_Seven";

					if (fileSystemRecommendPhase7.exists(new Path(
							outputOfRecommendPhase7)))
						fileSystemRecommendPhase7.delete(new Path(
								outputOfRecommendPhase7), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase7,
							new Path(outputOfRecommendPhase7));
					jobRecommendPhase7.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase Seven: "
							+ outputOfRecommendPhase7 + "****");

				} else {
					outputOfRecommendPhase7 = args[1]
							+ "_FriendSter_Recommend_Phase_Seven";

				}
				System.out.println("\nRecommend Phase Seven Output: "
						+ outputOfRecommendPhase7);
				//

			} else {
				System.out.println("****Recommend Phase Is Set To False****");

			}

		}
		String actualTrainingData = "";
		String recommendUsingCollaborative = "";
		String recommendUsingFriendsOfFriends = "";
		if (wholeDataScanningAndRecommendation) {
			System.out
					.println("Scanning Whole Data And Creating Recommendation!!");
			// Removing Unwanted Users
			boolean phase1 = true;
			// Creating Training_Testing 70-30
			boolean phase2 = true;
			// Creating Similarity Matrix Phase 1
			boolean phase3 = true;
			// Creating Similarity Matrix Phase 2
			boolean phase4 = true;
			// Creating Similarity Matrix Phase 2
			boolean phase5 = true;

			boolean phase6 = true;
			String outputOfPhase1 = "";
			String outputOfPhase2 = "";
			String outputOfPhase3 = "";
			String outputOfPhase4 = "";
			String outputOfPhase5 = "";
			String outputOfPhase6 = "";
			System.out.println("\nInitial Phase One: " + phase1);
			System.out.println("\nInitial Phase Two: " + phase2);
			System.out.println("\nInitial Phase Three: " + phase3);
			System.out.println("\nInitial Phase Four: " + phase4);
			System.out.println("\nInitial Phase Five: " + phase5);
			System.out.println("\nInitial Phase Six: " + phase6);

			if (phase1) {
				System.out.println("****Removing_Unwanted Users****");
				Configuration configurationPhase1 = new Configuration();
				FileSystem fileSystemPhase1 = FileSystem
						.get(configurationPhase1);

				Job jobPhase1 = Job.getInstance(configurationPhase1,
						"FriendSter_hkshah_phase1_removing_unwanted_users");

				jobPhase1.setJarByClass(MainJob.class);

				jobPhase1.setNumReduceTasks(40);

				jobPhase1.setMapperClass(MapperWholePhase1.class);

				jobPhase1.setReducerClass(ReducerWholePhase1.class);

				jobPhase1.setMapOutputKeyClass(IntWritable.class);
				jobPhase1.setMapOutputValueClass(Text.class);

				jobPhase1.setOutputKeyClass(NullWritable.class);
				jobPhase1.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase One: " + args[0]
						+ "****");

				FileInputFormat.addInputPath(jobPhase1, new Path(args[0]));

				outputOfPhase1 = args[1] + "_FriendSter_Whole_Phase_One";

				if (fileSystemPhase1.exists(new Path(outputOfPhase1)))
					fileSystemPhase1.delete(new Path(outputOfPhase1), true);

				FileOutputFormat.setOutputPath(jobPhase1, new Path(
						outputOfPhase1));
				jobPhase1.waitForCompletion(true);
				System.out.println("****Output Of Phase One: " + outputOfPhase1
						+ "****");
			} else {
				outputOfPhase1 = args[1] + "_FriendSter_Whole_Phase_One";
			}

			System.out.println("****Output Of Phase 1 :" + outputOfPhase1
					+ "****");
			if (phase2) {
				System.out.println("****Creating_Training_Testing_Dataset****");
				Configuration configurationPhase2 = new Configuration();
				FileSystem fileSystemPhase2 = FileSystem
						.get(configurationPhase2);

				Job jobPhase2 = Job.getInstance(configurationPhase2,
						"FriendSter_hkshah_phase_2_training_testing");

				jobPhase2.setJarByClass(MainJob.class);

				jobPhase2.setNumReduceTasks(60);

				jobPhase2.setMapperClass(MapperWholePhase2.class);

				jobPhase2.setReducerClass(ReducerWholePhase2.class);

				jobPhase2.setMapOutputKeyClass(IntWritable.class);
				jobPhase2.setMapOutputValueClass(IntWritable.class);

				jobPhase2.setOutputKeyClass(NullWritable.class);
				jobPhase2.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase Two: " + outputOfPhase1
						+ "****");

				FileInputFormat.addInputPath(jobPhase2,
						new Path(outputOfPhase1));

				outputOfPhase2 = args[1] + "_FriendSter_Whole_Phase_Two";

				if (fileSystemPhase2.exists(new Path(outputOfPhase2)))
					fileSystemPhase2.delete(new Path(outputOfPhase2), true);

				FileOutputFormat.setOutputPath(jobPhase2, new Path(
						outputOfPhase2));
				jobPhase2.waitForCompletion(true);
				System.out.println("****Output Of Phase Two: " + outputOfPhase2
						+ "****");
			} else {
				outputOfPhase2 = args[1] + "_FriendSter_Whole_Phase_Two";
			}

			System.out.println("****Output Of Phase 2 :" + outputOfPhase2
					+ "****");

			actualTrainingData = outputOfPhase2;
			// Get U U Count Based On Right Side U
			if (phase3) {
				System.out
						.println("****Creating_User_User_Similarity_Matrix_Phase_One****");
				Configuration configurationPhase3 = new Configuration();
				FileSystem fileSystemPhase3 = FileSystem
						.get(configurationPhase3);

				// System.out.println("****DELETING ORIGINAL DATASET****" +
				// args[0]);
				// fileSystemPhase3.delete(new Path(args[0]), true);

				Job jobPhase3 = Job
						.getInstance(configurationPhase3,
								"FriendSter_hkshah_phase3_user_user_similarity_matrix_phase1");

				jobPhase3.setJarByClass(MainJob.class);

				jobPhase3.setNumReduceTasks(200);

				jobPhase3.setMapperClass(MapperWholePhase3.class);

				jobPhase3.setReducerClass(ReducerWholePhase3.class);

				jobPhase3.setMapOutputKeyClass(IntWritable.class);
				jobPhase3.setMapOutputValueClass(IntWritable.class);

				jobPhase3.setOutputKeyClass(NullWritable.class);
				jobPhase3.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase Three: "
						+ outputOfPhase2 + "****");

				FileInputFormat.addInputPath(jobPhase3,
						new Path(outputOfPhase2));

				outputOfPhase3 = args[1] + "_FriendSter_Whole_Phase_Three";

				if (fileSystemPhase3.exists(new Path(outputOfPhase3)))
					fileSystemPhase3.delete(new Path(outputOfPhase3), true);

				FileOutputFormat.setOutputPath(jobPhase3, new Path(
						outputOfPhase3));
				jobPhase3.waitForCompletion(true);
				System.out.println("****Output Of Phase Three: "
						+ outputOfPhase3 + "****");
			} else {
				outputOfPhase3 = args[1] + "_FriendSter_Whole_Phase_Three";
			}

			System.out.println("****Output Of Phase 3 :" + outputOfPhase3
					+ "****");

			if (phase4) {
				System.out
						.println("****Creating_User_User_Similarity_Matrix_Phase_Two****");
				Configuration configurationPhase4 = new Configuration();
				FileSystem fileSystemPhase4 = FileSystem
						.get(configurationPhase4);

				// System.out.println("****DELETING PHASE ONE****" +
				// outputOfPhase1);
				// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

				Job jobPhase4 = Job
						.getInstance(configurationPhase4,
								"FriendSter_hkshah_phase4_user_user_similarity_matrix_phase2");

				jobPhase4.setJarByClass(MainJob.class);

				jobPhase4.setNumReduceTasks(80);

				jobPhase4.setMapperClass(MapperWholePhase4.class);
				jobPhase4.setCombinerClass(CombinerWholePhase4.class);

				jobPhase4.setReducerClass(ReducerWholePhase4.class);

				jobPhase4.setMapOutputKeyClass(Text.class);
				jobPhase4.setMapOutputValueClass(IntWritable.class);

				jobPhase4.setOutputKeyClass(NullWritable.class);
				jobPhase4.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase Four: " + outputOfPhase3
						+ "****");

				FileInputFormat.addInputPath(jobPhase4,
						new Path(outputOfPhase3));

				// System.out.println("****Input Of Phase Four: " +
				// temporaryInputPath2
				// + "****");
				//
				// FileInputFormat.addInputPath(jobPhase4, new
				// Path(temporaryInputPath2));

				outputOfPhase4 = args[1] + "_FriendSter_Whole_Phase_Four";

				if (fileSystemPhase4.exists(new Path(outputOfPhase4)))
					fileSystemPhase4.delete(new Path(outputOfPhase4), true);

				FileOutputFormat.setOutputPath(jobPhase4, new Path(
						outputOfPhase4));
				jobPhase4.waitForCompletion(true);
				System.out.println("****Output Of Phase Four: "
						+ outputOfPhase4 + "****");
			} else {
				outputOfPhase4 = args[1] + "_FriendSter_Whole_Phase_Four";
			}

			System.out.println("****Output Of Phase 4 :" + outputOfPhase4
					+ "****");

			if (phase5) {
				System.out
						.println("****Creating_User_User_Similarity_Matrix_Phase_Three****");
				Configuration configurationPhase5 = new Configuration();
				FileSystem fileSystemPhase5 = FileSystem
						.get(configurationPhase5);

				Job jobPhase4 = Job.getInstance(configurationPhase5,
						"FriendSter_hkshah_user_user_similarity_matrix_phase3");

				jobPhase4.setJarByClass(MainJob.class);

				jobPhase4.setNumReduceTasks(60);

				jobPhase4.setMapperClass(MapperWholePhase5.class);
				// jobPhase4.setCombinerClass(CombinerPhase5.class);
				jobPhase4.setReducerClass(ReducerWholePhase5.class);

				jobPhase4.setMapOutputKeyClass(IntWritable.class);
				jobPhase4.setMapOutputValueClass(Text.class);

				jobPhase4.setOutputKeyClass(NullWritable.class);
				jobPhase4.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase Five: " + outputOfPhase4
						+ "****");

				FileInputFormat.addInputPath(jobPhase4,
						new Path(outputOfPhase4));

				outputOfPhase5 = args[1] + "_FriendSter_Whole_Phase_Five";

				if (fileSystemPhase5.exists(new Path(outputOfPhase5)))
					fileSystemPhase5.delete(new Path(outputOfPhase5), true);

				FileOutputFormat.setOutputPath(jobPhase4, new Path(
						outputOfPhase5));
				jobPhase4.waitForCompletion(true);
				System.out.println("****Output Of Phase Five: "
						+ outputOfPhase5 + "****");
			} else {
				outputOfPhase5 = args[1] + "_FriendSter_Whole_Phase_Five";
			}

			System.out.println("****Output Of Phase 5:" + outputOfPhase5
					+ "****");

			if (phase6) {
				System.out.println("****Counting Most Popular Users****");
				Configuration configurationPhase6 = new Configuration();
				FileSystem fileSystemPhase6 = FileSystem
						.get(configurationPhase6);

				Job jobPhase6 = Job
						.getInstance(configurationPhase6,
								"FriendSter_hkshah_phase6_calculating_most_popular_users");

				jobPhase6.setJarByClass(MainJob.class);

				// jobPhase6.setNumReduceTasks(40);

				jobPhase6.setMapperClass(MapperWholePhase6.class);
				jobPhase6
						.setSortComparatorClass(LongWritable.DecreasingComparator.class);

				jobPhase6.setReducerClass(ReducerWholePhase6.class);

				jobPhase6.setMapOutputKeyClass(LongWritable.class);
				jobPhase6.setMapOutputValueClass(IntWritable.class);

				jobPhase6.setOutputKeyClass(NullWritable.class);
				jobPhase6.setOutputValueClass(Text.class);
				System.out.println("****Input Of Phase Six: " + outputOfPhase1
						+ "****");

				FileInputFormat.addInputPath(jobPhase6,
						new Path(outputOfPhase1));

				outputOfPhase6 = args[1] + "_FriendSter_Whole_Phase_Six";

				if (fileSystemPhase6.exists(new Path(outputOfPhase6)))
					fileSystemPhase6.delete(new Path(outputOfPhase6), true);

				FileOutputFormat.setOutputPath(jobPhase6, new Path(
						outputOfPhase6));
				jobPhase6.waitForCompletion(true);
				System.out.println("****Output Of Phase One: " + outputOfPhase6
						+ "****");
			} else {
				outputOfPhase6 = args[1] + "_FriendSter_Whole_Phase_Six";
			}

			System.out.println("****Output Of Phase 6 :" + outputOfPhase6
					+ "****");

			// System.out.println("****Output Of Phase 5:" + outputOfPhase5
			// + "****");

			System.out.println("\n\n");
			System.out.println("****Similarity Matrix Location: "
					+ outputOfPhase5 + "****");
			System.out.println("****User_Friend_Location: " + outputOfPhase2
					+ "****");
			System.out.println("\n\n");

			String userSimilarityMatrix = outputOfPhase5;
			String userFriendList = outputOfPhase2;

			boolean recommendPhase = true;
			System.out.println("Recommendation Phase: " + recommendPhase);

			if (recommendPhase) {
				System.out.println("****Running Recommend Phase****");
				boolean recommendPhase1 = true;
				boolean recommendPhase2 = true;
				boolean recommendPhase3 = true;
				boolean recommendPhase4 = true;
				boolean recommendPhase5 = true;
				boolean recommendPhase6 = true;

				String outputOfRecommendPhase1 = "";
				String outputOfRecommendPhase2 = "";
				String outputOfRecommendPhase3 = "";
				String outputOfRecommendPhase4 = "";
				String outputOfRecommendPhase5 = "";
				String outputOfRecommendPhase6 = "";

				System.out
						.println("****Recommend Phase One:" + recommendPhase1);
				System.out
						.println("****Recommend Phase Two:" + recommendPhase2);

				System.out.println("****Recommend Phase Three:"
						+ recommendPhase3);
				System.out.println("****Recommend Phase Four:"
						+ recommendPhase4);

				System.out.println("****Recommend Phase Five:"
						+ recommendPhase5);
				System.out
						.println("****Recommend Phase Six:" + recommendPhase6);

				if (recommendPhase1) {
					System.out.println("\n****Recommend Phase 1****");

					Configuration configurationRecommendPhase1 = new Configuration();
					FileSystem fileSystemRecommendPhase1 = FileSystem
							.get(configurationRecommendPhase1);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase1 = Job.getInstance(
							configurationRecommendPhase1,
							"FriendSter_hkshah_recommend_phase_1");

					jobRecommendPhase1.setJarByClass(MainJob.class);

					jobRecommendPhase1.setNumReduceTasks(200);

					jobRecommendPhase1
							.setMapperClass(MapperWholeRecommendPhase1.class);

					// jobRecommendPhase1.setInputFormatClass(NLineInputFormat.class);
					// jobRecommendPhase1.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap",
					// 15000);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);

					jobRecommendPhase1
							.setReducerClass(ReducerWholeRecommendPhase1.class);

					jobRecommendPhase1.setMapOutputKeyClass(IntWritable.class);
					jobRecommendPhase1.setMapOutputValueClass(Text.class);

					jobRecommendPhase1.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase1.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase One: "
							+ userSimilarityMatrix + "****");

					FileInputFormat.addInputPath(jobRecommendPhase1, new Path(
							userSimilarityMatrix));
					System.out.println("****Input Of Recommend Phase One: "
							+ userFriendList + "****");

					FileInputFormat.addInputPath(jobRecommendPhase1, new Path(
							userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase1 = args[1]
							+ "_FriendSter_Whole_Recommend_Phase_One";

					if (fileSystemRecommendPhase1.exists(new Path(
							outputOfRecommendPhase1)))
						fileSystemRecommendPhase1.delete(new Path(
								outputOfRecommendPhase1), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase1,
							new Path(outputOfRecommendPhase1));
					jobRecommendPhase1.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase One: "
							+ outputOfRecommendPhase1 + "****");
				} else {
					outputOfRecommendPhase1 = args[1]
							+ "_FriendSter_Whole_Recommend_Phase_One";

				}
				System.out.println("\nRecommend Phase One Output: "
						+ outputOfRecommendPhase1);

				if (recommendPhase2) {
					System.out.println("\n****Recommend Phase 2****");

					Configuration configurationRecommendPhase2 = new Configuration();
					FileSystem fileSystemRecommendPhase2 = FileSystem
							.get(configurationRecommendPhase2);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase2 = Job.getInstance(
							configurationRecommendPhase2,
							"FriendSter_hkshah_recommend_phase_2");

					jobRecommendPhase2.setJarByClass(MainJob.class);

					jobRecommendPhase2.setNumReduceTasks(600);

					jobRecommendPhase2
							.setMapperClass(MapperWholeRecommendPhase2.class);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);

					jobRecommendPhase2
							.setReducerClass(ReducerWholeRecommendPhase2.class);

					jobRecommendPhase2.setMapOutputKeyClass(IntWritable.class);
					jobRecommendPhase2.setMapOutputValueClass(Text.class);

					jobRecommendPhase2.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase2.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase Two: "
							+ outputOfRecommendPhase1 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase2, new Path(
							outputOfRecommendPhase1));
					System.out.println("****Input Of Recommend Phase Two: "
							+ userFriendList + "****");

					FileInputFormat.addInputPath(jobRecommendPhase2, new Path(
							userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase2 = args[1]
							+ "_FriendSter_Whole_Recommend_Phase_Two";

					if (fileSystemRecommendPhase2.exists(new Path(
							outputOfRecommendPhase2)))
						fileSystemRecommendPhase2.delete(new Path(
								outputOfRecommendPhase2), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase2,
							new Path(outputOfRecommendPhase2));
					jobRecommendPhase2.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase Two: "
							+ outputOfRecommendPhase2 + "****");

				} else {
					outputOfRecommendPhase2 = args[1]
							+ "_FriendSter_Whole_Recommend_Phase_Two";

				}
				System.out.println("\nRecommend Phase Two Output: "
						+ outputOfRecommendPhase2);

				recommendUsingCollaborative = outputOfRecommendPhase2;

				if (recommendPhase4) {
					System.out.println("\n****Recommend Phase 4****");

					Configuration configurationRecommendPhase4 = new Configuration();
					FileSystem fileSystemRecommendPhase4 = FileSystem
							.get(configurationRecommendPhase4);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase4 = Job.getInstance(
							configurationRecommendPhase4,
							"FriendSter_hkshah_recommend_phase_4");

					jobRecommendPhase4.setJarByClass(MainJob.class);

					jobRecommendPhase4.setNumReduceTasks(60);

					jobRecommendPhase4
							.setMapperClass(MapperWholeRecommendPhase4.class);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);
					// jobRecommendPhase3
					// .setPartitionerClass(PartitionerRecommendPhase3.class);
					jobRecommendPhase4
							.setReducerClass(ReducerWholeRecommendPhase4.class);

					jobRecommendPhase4.setMapOutputKeyClass(IntWritable.class);
					jobRecommendPhase4.setMapOutputValueClass(Text.class);

					jobRecommendPhase4.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase4.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase Four: "
							+ outputOfRecommendPhase2 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase4, new Path(
							outputOfRecommendPhase2));

					System.out.println("****Input Of Recommend Phase Four: "
							+ userFriendList + "****");

					FileInputFormat.addInputPath(jobRecommendPhase4, new Path(
							userFriendList));
					// System.out.println("****Input Of Recommend Phase Two: " +
					// userFriendList
					// + "****");
					//
					// FileInputFormat.addInputPath(jobRecommendPhase2, new
					// Path(userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase4 = args[1]
							+ "_FriendSter_Whole_Recommend_Phase_Four";

					if (fileSystemRecommendPhase4.exists(new Path(
							outputOfRecommendPhase4)))
						fileSystemRecommendPhase4.delete(new Path(
								outputOfRecommendPhase4), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase4,
							new Path(outputOfRecommendPhase4));
					jobRecommendPhase4.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase Four: "
							+ outputOfRecommendPhase4 + "****");

				} else {
					outputOfRecommendPhase4 = args[1]
							+ "_FriendSter_Whole_Recommend_Phase_Four";

				}
				System.out.println("\nRecommend Phase Four Output: "
						+ outputOfRecommendPhase4);

				if (recommendPhase5) {
					System.out.println("\n****Recommend Phase 5****");

					Configuration configurationRecommendPhase5 = new Configuration();
					FileSystem fileSystemRecommendPhase5 = FileSystem
							.get(configurationRecommendPhase5);

					// System.out.println("****DELETING PHASE ONE****" +
					// outputOfPhase1);
					// fileSystemPhase4.delete(new Path(outputOfPhase1), true);

					Job jobRecommendPhase5 = Job.getInstance(
							configurationRecommendPhase5,
							"FriendSter_hkshah_recommend_phase_5");

					jobRecommendPhase5.setJarByClass(MainJob.class);

					jobRecommendPhase5.setNumReduceTasks(60);

					jobRecommendPhase5
							.setMapperClass(MapperWholeRecommendPhase5.class);
					// jobRecommendPhase1.setCombinerClass(CombinerPhase4.class);
					// jobRecommendPhase3
					// .setPartitionerClass(PartitionerRecommendPhase3.class);
					jobRecommendPhase5
							.setReducerClass(ReducerWholeRecommendPhase5.class);

					jobRecommendPhase5.setMapOutputKeyClass(IntWritable.class);
					jobRecommendPhase5.setMapOutputValueClass(Text.class);

					jobRecommendPhase5.setOutputKeyClass(NullWritable.class);
					jobRecommendPhase5.setOutputValueClass(Text.class);
					System.out.println("****Input Of Recommend Phase Five: "
							+ outputOfRecommendPhase4 + "****");

					FileInputFormat.addInputPath(jobRecommendPhase5, new Path(
							outputOfRecommendPhase4));

					System.out.println("****Input Of Recommend Phase Five: "
							+ userFriendList + "****");

					FileInputFormat.addInputPath(jobRecommendPhase5, new Path(
							userFriendList));

					// System.out.println("****Input Of Recommend Phase Four: "
					// + userFriendList + "****");
					//
					// FileInputFormat.addInputPath(jobRecommendPhase5, new
					// Path(
					// userFriendList));
					// System.out.println("****Input Of Recommend Phase Two: " +
					// userFriendList
					// + "****");
					//
					// FileInputFormat.addInputPath(jobRecommendPhase2, new
					// Path(userFriendList));

					// System.out.println("****Input Of Phase Four: " +
					// temporaryInputPath2
					// + "****");
					//
					// FileInputFormat.addInputPath(jobPhase4, new
					// Path(temporaryInputPath2));

					outputOfRecommendPhase5 = args[1]
							+ "_FriendSter_Whole_Recommend_Phase_Five";

					if (fileSystemRecommendPhase5.exists(new Path(
							outputOfRecommendPhase5)))
						fileSystemRecommendPhase5.delete(new Path(
								outputOfRecommendPhase5), true);

					FileOutputFormat.setOutputPath(jobRecommendPhase5,
							new Path(outputOfRecommendPhase5));
					jobRecommendPhase5.waitForCompletion(true);
					System.out.println("****Output Of Recommend Phase Five: "
							+ outputOfRecommendPhase5 + "****");

				} else {
					outputOfRecommendPhase5 = args[1]
							+ "_FriendSter_Whole_Recommend_Phase_Five";

				}
				System.out.println("\nRecommend Phase Five Output: "
						+ outputOfRecommendPhase5);

			}

		}

		actualTrainingData = args[1] + "_FriendSter_Whole_Phase_Two";
		recommendUsingCollaborative = args[1]
				+ "_FriendSter_Whole_Recommend_Phase_Two";

		String recommendAfterCollaborative = args[1]
				+ "_FriendSter_Whole_Recommend_Phase_Five";
		String userFriendList = args[0];

		String candidateDataset = actualTrainingData;
		boolean analysisPhase0 = true;
		boolean analysisPhase1 = true;
		boolean analysisPhase2 = true;
		boolean analysisPhase3 = true;
		boolean analysisPhase4 = true;

		String outputPhaseAnalysisPhaseZero = "";
		String outputPhaseAnalysisPhaseOne = "";
		String outputPhaseAnalysisPhaseTwo = "";
		String outputPhaseAnalysisPhaseThree = "";
		String outputPhaseAnalysisPhaseFour = "";

		System.out.println("\nAnalysis Phase Zero: " + analysisPhase0);
		System.out.println("\nAnalysis Phase One: " + analysisPhase1);
		System.out.println("\nAnalysis Phase Two: " + analysisPhase2);
		System.out.println("\nAnalysis Phase Three: " + analysisPhase3);
		System.out.println("\nAnalysis Phase Four: " + analysisPhase4);

		// System.out.println();
		if (analysisPhase0) {
			System.out.println("****Analysis Phase Zero****");
			Configuration configurationAnalysisPhase0 = new Configuration();
			FileSystem fileSystemAnalysisPhase0 = FileSystem
					.get(configurationAnalysisPhase0);

			Job jobAnalysisPhase0 = Job.getInstance(
					configurationAnalysisPhase0,
					"FriendSter_hkshah_analysis_phase_zero");

			jobAnalysisPhase0.setJarByClass(MainJob.class);

			// jobPhase1.setNumReduceTasks(40);

			jobAnalysisPhase0.setMapperClass(AnalysisMapperPhase0.class);
			jobAnalysisPhase0.setCombinerClass(AnalysisCombinerPhase0.class);

			jobAnalysisPhase0.setReducerClass(AnalysisReducerPhase0.class);

			jobAnalysisPhase0.setMapOutputKeyClass(Text.class);
			jobAnalysisPhase0.setMapOutputValueClass(IntWritable.class);

			jobAnalysisPhase0.setOutputKeyClass(NullWritable.class);
			jobAnalysisPhase0.setOutputValueClass(Text.class);
			System.out.println("****Input Of Analysis Phase Zero: "
					+ userFriendList + "****");

			FileInputFormat.addInputPath(jobAnalysisPhase0, new Path(
					userFriendList));

			System.out.println("****Input Of Analysis Phase Zero: "
					+ candidateDataset + "****");

			FileInputFormat.addInputPath(jobAnalysisPhase0, new Path(
					candidateDataset));

			outputPhaseAnalysisPhaseZero = args[1]
					+ "_FriendSter_Analysis_Phase_Zero";

			if (fileSystemAnalysisPhase0.exists(new Path(
					outputPhaseAnalysisPhaseZero)))
				fileSystemAnalysisPhase0.delete(new Path(
						outputPhaseAnalysisPhaseZero), true);

			FileOutputFormat.setOutputPath(jobAnalysisPhase0, new Path(
					outputPhaseAnalysisPhaseZero));
			jobAnalysisPhase0.waitForCompletion(true);
			System.out.println("****Output Of Analysis Phase Zero: "
					+ outputPhaseAnalysisPhaseZero + "****");
		} else {
			outputPhaseAnalysisPhaseZero = args[1]
					+ "_FriendSter_Analysis_Phase_Zero";
		}

		System.out.println("****Analysis Phase Zero Location"
				+ outputPhaseAnalysisPhaseZero + "****");

		if (analysisPhase1) {
			System.out.println("****Analysis Phase One****");
			Configuration configurationAnalysisPhase1 = new Configuration();
			FileSystem fileSystemAnalysisPhase1 = FileSystem
					.get(configurationAnalysisPhase1);

			Job jobAnalysisPhase1 = Job.getInstance(
					configurationAnalysisPhase1,
					"FriendSter_hkshah_analysis_phase_one");

			jobAnalysisPhase1.setJarByClass(MainJob.class);

			// jobPhase1.setNumReduceTasks(40);

			jobAnalysisPhase1.setMapperClass(AnalysisMapperPhase1.class);
			 jobAnalysisPhase1.setCombinerClass(AnalysisCombinerPhase1.class);

			jobAnalysisPhase1.setReducerClass(AnalysisReducerPhase1.class);

			jobAnalysisPhase1.setMapOutputKeyClass(Text.class);
			jobAnalysisPhase1.setMapOutputValueClass(IntWritable.class);

			jobAnalysisPhase1.setOutputKeyClass(NullWritable.class);
			jobAnalysisPhase1.setOutputValueClass(Text.class);
			System.out.println("****Input Of Analysis Phase One: "
					+ recommendUsingCollaborative + "****");

			FileInputFormat.addInputPath(jobAnalysisPhase1, new Path(
					recommendUsingCollaborative));
//			System.out.println("****Input Of Analysis Phase One: "
//					+ userFriendList + "****");
//
//			FileInputFormat.addInputPath(jobAnalysisPhase1, new Path(
//					userFriendList));
			outputPhaseAnalysisPhaseOne = args[1]
					+ "_FriendSter_Analysis_Phase_One";

			if (fileSystemAnalysisPhase1.exists(new Path(
					outputPhaseAnalysisPhaseOne)))
				fileSystemAnalysisPhase1.delete(new Path(
						outputPhaseAnalysisPhaseOne), true);

			FileOutputFormat.setOutputPath(jobAnalysisPhase1, new Path(
					outputPhaseAnalysisPhaseOne));
			jobAnalysisPhase1.waitForCompletion(true);
			System.out.println("****Output Of Analysis Phase One: "
					+ outputPhaseAnalysisPhaseOne + "****");
		} else {
			outputPhaseAnalysisPhaseOne = args[1]
					+ "_FriendSter_Analysis_Phase_One";
		}

		System.out.println("****Analysis Phase One Location"
				+ outputPhaseAnalysisPhaseOne + "****");

		if (analysisPhase2) {
			System.out.println("****Analysis Phase Two****");
			Configuration configurationAnalysisPhase2 = new Configuration();
			FileSystem fileSystemAnalysisPhase2 = FileSystem
					.get(configurationAnalysisPhase2);

			Job jobAnalysisPhase2 = Job.getInstance(
					configurationAnalysisPhase2,
					"FriendSter_hkshah_analysis_phase_two");

			jobAnalysisPhase2.setJarByClass(MainJob.class);

			// jobPhase1.setNumReduceTasks(40);

			jobAnalysisPhase2.setMapperClass(AnalysisMapperPhase1.class);
			// jobAnalysisPhase1.setCombinerClass(AnalysisCombinerPhase1.class);

			jobAnalysisPhase2.setReducerClass(AnalysisReducerPhase1.class);

			jobAnalysisPhase2.setMapOutputKeyClass(Text.class);
			jobAnalysisPhase2.setMapOutputValueClass(IntWritable.class);

			jobAnalysisPhase2.setOutputKeyClass(NullWritable.class);
			jobAnalysisPhase2.setOutputValueClass(Text.class);
			System.out.println("****Input Of Analysis Phase Two: "
					+ recommendAfterCollaborative + "****");

			FileInputFormat.addInputPath(jobAnalysisPhase2, new Path(
					recommendAfterCollaborative));
			// System.out.println("****Input Of Analysis Phase One: "
			// + userFriendList + "****");
			//
			// FileInputFormat.addInputPath(jobAnalysisPhase1, new Path(
			// userFriendList));
			outputPhaseAnalysisPhaseTwo = args[1]
					+ "_FriendSter_Analysis_Phase_Two";

			if (fileSystemAnalysisPhase2.exists(new Path(
					outputPhaseAnalysisPhaseTwo)))
				fileSystemAnalysisPhase2.delete(new Path(
						outputPhaseAnalysisPhaseTwo), true);

			FileOutputFormat.setOutputPath(jobAnalysisPhase2, new Path(
					outputPhaseAnalysisPhaseTwo));
			jobAnalysisPhase2.waitForCompletion(true);
			System.out.println("****Output Of Analysis Phase Two: "
					+ outputPhaseAnalysisPhaseTwo + "****");
		} else {
			outputPhaseAnalysisPhaseTwo = args[1]
					+ "_FriendSter_Analysis_Phase_Two";
		}

		System.out.println("****Analysis Phase Two Location"
				+ outputPhaseAnalysisPhaseTwo + "****");
		
		
		if (analysisPhase3) {
			System.out.println("****Analysis Phase Three****");
			Configuration configurationAnalysisPhase3 = new Configuration();
			FileSystem fileSystemAnalysisPhase3 = FileSystem
					.get(configurationAnalysisPhase3);

			Job jobAnalysisPhase3 = Job.getInstance(
					configurationAnalysisPhase3,
					"FriendSter_hkshah_analysis_phase_three");

			jobAnalysisPhase3.setJarByClass(MainJob.class);

			// jobPhase1.setNumReduceTasks(40);

			jobAnalysisPhase3.setMapperClass(AnalysisMapperPhase3.class);
			// jobAnalysisPhase1.setCombinerClass(AnalysisCombinerPhase1.class);

			jobAnalysisPhase3.setReducerClass(AnalysisReducerPhase3.class);

			jobAnalysisPhase3.setMapOutputKeyClass(IntWritable.class);
			jobAnalysisPhase3.setMapOutputValueClass(Text.class);

			jobAnalysisPhase3.setOutputKeyClass(NullWritable.class);
			jobAnalysisPhase3.setOutputValueClass(Text.class);
			System.out.println("****Input Of Analysis Phase Three: "
					+ recommendAfterCollaborative + "****");

			FileInputFormat.addInputPath(jobAnalysisPhase3, new Path(
					recommendAfterCollaborative));
			
			System.out.println("****Input Of Analysis Phase Three: "
					+ recommendUsingCollaborative + "****");

			FileInputFormat.addInputPath(jobAnalysisPhase3, new Path(
					recommendUsingCollaborative));
			
			System.out.println("****Input Of Analysis Phase Three: "
					+ userFriendList + "****");

			FileInputFormat.addInputPath(jobAnalysisPhase3, new Path(
					userFriendList));
			// System.out.println("****Input Of Analysis Phase One: "
			// + userFriendList + "****");
			//
			// FileInputFormat.addInputPath(jobAnalysisPhase1, new Path(
			// userFriendList));
			outputPhaseAnalysisPhaseThree = args[1]
					+ "_FriendSter_Analysis_Phase_Three";

			if (fileSystemAnalysisPhase3.exists(new Path(
					outputPhaseAnalysisPhaseThree)))
				fileSystemAnalysisPhase3.delete(new Path(
						outputPhaseAnalysisPhaseThree), true);

			FileOutputFormat.setOutputPath(jobAnalysisPhase3, new Path(
					outputPhaseAnalysisPhaseThree));
			jobAnalysisPhase3.waitForCompletion(true);
			System.out.println("****Output Of Analysis Phase Three: "
					+ outputPhaseAnalysisPhaseThree + "****");
		} else {
			outputPhaseAnalysisPhaseThree = args[1]
					+ "_FriendSter_Analysis_Phase_Three";
		}

		System.out.println("****Analysis Phase Three Location"
				+ outputPhaseAnalysisPhaseThree + "****");
		
		
		if (analysisPhase4) {
			System.out.println("****Analysis Phase Four****");
			Configuration configurationAnalysisPhase4 = new Configuration();
			FileSystem fileSystemAnalysisPhase4 = FileSystem
					.get(configurationAnalysisPhase4);

			Job jobAnalysisPhase4 = Job.getInstance(
					configurationAnalysisPhase4,
					"FriendSter_hkshah_analysis_phase_three");

			jobAnalysisPhase4.setJarByClass(MainJob.class);

			// jobPhase1.setNumReduceTasks(40);

			jobAnalysisPhase4.setMapperClass(AnalysisMapperPhase4.class);
			// jobAnalysisPhase1.setCombinerClass(AnalysisCombinerPhase1.class);

			jobAnalysisPhase4.setReducerClass(AnalysisReducerPhase4.class);

			jobAnalysisPhase4.setMapOutputKeyClass(IntWritable.class);
			jobAnalysisPhase4.setMapOutputValueClass(IntWritable.class);

			jobAnalysisPhase4.setOutputKeyClass(NullWritable.class);
			jobAnalysisPhase4.setOutputValueClass(Text.class);
			System.out.println("****Input Of Analysis Phase Four: "
					+ outputPhaseAnalysisPhaseThree + "****");

			FileInputFormat.addInputPath(jobAnalysisPhase4, new Path(
					outputPhaseAnalysisPhaseThree));
			
//			System.out.println("****Input Of Analysis Phase Three: "
//					+ recommendUsingCollaborative + "****");
//
//			FileInputFormat.addInputPath(jobAnalysisPhase4, new Path(
//					recommendUsingCollaborative));
//			
//			System.out.println("****Input Of Analysis Phase Three: "
//					+ userFriendList + "****");
//
//			FileInputFormat.addInputPath(jobAnalysisPhase4, new Path(
//					userFriendList));
			// System.out.println("****Input Of Analysis Phase One: "
			// + userFriendList + "****");
			//
			// FileInputFormat.addInputPath(jobAnalysisPhase1, new Path(
			// userFriendList));
			outputPhaseAnalysisPhaseFour = args[1]
					+ "_FriendSter_Analysis_Phase_Four";

			if (fileSystemAnalysisPhase4.exists(new Path(
					outputPhaseAnalysisPhaseFour)))
				fileSystemAnalysisPhase4.delete(new Path(
						outputPhaseAnalysisPhaseFour), true);

			FileOutputFormat.setOutputPath(jobAnalysisPhase4, new Path(
					outputPhaseAnalysisPhaseFour));
			jobAnalysisPhase4.waitForCompletion(true);
			System.out.println("****Output Of Analysis Phase Four: "
					+ outputPhaseAnalysisPhaseFour + "****");
		} else {
			outputPhaseAnalysisPhaseFour = args[1]
					+ "_FriendSter_Analysis_Phase_Four";
		}

		System.out.println("****Analysis Phase Four Location"
				+ outputPhaseAnalysisPhaseFour + "****");
	}

}
