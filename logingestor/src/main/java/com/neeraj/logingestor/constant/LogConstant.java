package com.neeraj.logingestor.constant;

public class LogConstant {
	private LogConstant() {

	}

	public static final String SAVE_LOG_DATA_URI = "/saveLogData";
	public static final String SEARCH_LOGS_BY_CRITERIA_URI = "/searchByCriteria";
	public static final String SEARCH_ALL_LOGS_URI = "";

	public static final String SAVE_LOG_DATA_SUCCESS_MSG = "Log data saved successfully in database.";
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	// Spring Validation messages
	public static final String TIMESTAMP_CANNOT_BE_NULL = "Date time must not be null";
}
