package utils;

import org.apache.hadoop.io.Text;

public class Protocols {
	final public static String FRIEND_EDGE_SPLIT = ":";

	final public static String VALID_FRIEND_CONTAINS = ",";

	final public static Text IGNORE_ID = new Text("%&IGNORE&%");
	final public static Text CONSIDER_ID = new Text("%&CONSIDER&%");

	final public static String FRIEND_LIST_SEPARATOR = ",";

	final public static Float TRAINING_TESTING = 0.7f;

	public static final String TRAINING_DATASET_STARTS_WITH = "#TRAIN#";

	public static final String TESTING_DATASET_STARTS_WITH = "#TEST#";

	public static final String USER_USER_SEPARATOR = "#&#";
	public static final String USER_USER_COUNT_SEPARATOR = "%%";

	public static final int THRESHOLD_ON_COUNT_OF_USERS = 100;

	public static final String USER_WEIGHT = "#";

	public static final int MINIMUM_WEIGHT_FOR_USER_REQUIRED = 0;

	public static final String USER_FRIEND_LIST_STARTS_WITH = "#FRIENDLIST#";

	public static final String VALUE_SEPARATOR = "%%";

	public static final String RECOMMEND_PHASE_2_FRIENDS_STARTS_WITH = "#";

	public static final String RECOMMEND_PHASE_2_TEST_FRIENDS_STARTS_WITH = "%";

	public static final String ACCURACY_STARTS_WITH = "#ACCURACY#";
	public static final String RECOMMEND_LIST_STARTS_WITH = "#RECOMMEND#";
	public static final String COLD_START_STARTS_WITH = "#COLDSTART#";
	public static final String NO_TEST_DATA = "NODATA";

	public static final String ACCURACY_COUNT = "ACC";

	public static final String COLD_START_COUNT = "COLD";
	public static final String COLD_START_USERS_STARTS_WITH = "USERCOLD";

	public static final String COUNT_OF_FINAL_COLD_START_USERS = "Final Cold Start Users Count";

	public static final String RECOMMEND_TO_NON_RECOMMENDED = "#BEST_LIST#";

	public static final String PRIVATE_USER = "private";
	public static final String NOT_FOUND_USER = "notfound";
	
		


}
