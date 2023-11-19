package com.neeraj.logingestor.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.neeraj.logingestor.constant.ErrorConstant;
import com.neeraj.logingestor.entity.LogEntity;
import com.neeraj.logingestor.exception.RecordNotSaveException;
import com.neeraj.logingestor.model.LogRequest;
import com.neeraj.logingestor.repo.LogRepository;
import com.neeraj.logingestor.service.LogService;
import com.neeraj.logingestor.utils.CommonUtils;

@Service
public class LogServiceImpl implements LogService {
	private static final Logger logger = LogManager.getLogger(LogServiceImpl.class);

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private CommonUtils commonUtils;

	@Override
	public void saveLogData(LogRequest logRequest) {
		try {
			// mapping logRequest to entity
			LogEntity logEntity = commonUtils.logModelToEntityMapper(logRequest);
			logRepository.save(logEntity);
		} catch (MongoException exp) {
			logger.error(ErrorConstant.RECORD_NOT_SAVE_EXCEPTION_MSG,exp);
			throw new RecordNotSaveException(ErrorConstant.RECORD_NOT_SAVE_EXCEPTION_MSG);
		} catch (Exception exp) {
			logger.error(ErrorConstant.INTERNAL_SERVER_ERROR_MSG,exp);
			throw exp;
		}

	}

}
