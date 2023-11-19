package com.neeraj.logingestor.exception;

import com.mongodb.MongoException;

public class RecordNotSaveException extends MongoException{
	public RecordNotSaveException(String msg) {
		super(msg);
	}
}
