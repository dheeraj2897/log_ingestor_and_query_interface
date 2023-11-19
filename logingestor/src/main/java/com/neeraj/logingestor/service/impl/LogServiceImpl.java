package com.neeraj.logingestor.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.neeraj.logingestor.constant.ErrorConstant;
import com.neeraj.logingestor.entity.LogEntity;
import com.neeraj.logingestor.exception.RecordNotSaveException;
import com.neeraj.logingestor.model.LogRequest;
import com.neeraj.logingestor.model.LogSearchCriteriaRequest;
import com.neeraj.logingestor.repo.LogRepository;
import com.neeraj.logingestor.service.LogService;
import com.neeraj.logingestor.utils.CommonUtils;

@Service
public class LogServiceImpl implements LogService {
	private static final Logger logger = LogManager.getLogger(LogServiceImpl.class);

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CommonUtils commonUtils;

	@Override
	public void saveLogData(LogRequest logRequest) {
		try {
			// mapping logRequest to entity
			LogEntity logEntity = commonUtils.logModelToEntityMapper(logRequest);
			logRepository.save(logEntity);
		} catch (MongoException exp) {
			logger.error(ErrorConstant.RECORD_NOT_SAVE_EXCEPTION_MSG, exp);
			throw new RecordNotSaveException(ErrorConstant.RECORD_NOT_SAVE_EXCEPTION_MSG);
		} catch (Exception exp) {
			logger.error(ErrorConstant.INTERNAL_SERVER_ERROR_MSG, exp);
			throw exp;
		}

	}

	@Override
	public List<LogRequest> findAll() {
		List<LogRequest> logRequestList = null;
		try {
			List<LogEntity> entityList = logRepository.findAll();
			logRequestList = commonUtils.getModelListFromEntityList(entityList);
		} catch (MongoException exp) {
			logger.error(ErrorConstant.RECORD_NOT_SAVE_EXCEPTION_MSG, exp);
			throw new RecordNotSaveException(ErrorConstant.RECORD_NOT_SAVE_EXCEPTION_MSG);
		} catch (Exception exp) {
			logger.error(ErrorConstant.INTERNAL_SERVER_ERROR_MSG, exp);
			throw exp;
		}
		return logRequestList;
	}

	@Override
	public List<LogRequest> searchLogsByCriteria(LogSearchCriteriaRequest logSearchCriteriaRequest) {
		List<LogRequest> logRequestList = null;
		try {
			List<LogEntity> entityList = getResultByCriteria(logSearchCriteriaRequest);
			logRequestList = commonUtils.getModelListFromEntityList(entityList);
		} catch (MongoException exp) {
			logger.error(ErrorConstant.RECORD_NOT_SAVE_EXCEPTION_MSG, exp);
			throw new RecordNotSaveException(ErrorConstant.RECORD_NOT_SAVE_EXCEPTION_MSG);
		} catch (Exception exp) {
			logger.error(ErrorConstant.INTERNAL_SERVER_ERROR_MSG, exp);
			throw exp;
		}
		return logRequestList;
	}

	private List<LogEntity> getResultByCriteria(LogSearchCriteriaRequest logSearchCriteriaRequest) {
		Query query = new Query();
		// Add filters based on the search criteria
		if (logSearchCriteriaRequest.getLevel() != null) {
			query.addCriteria(Criteria.where("level").is(logSearchCriteriaRequest.getLevel()));
		}
		if (logSearchCriteriaRequest.getMessage() != null) {
			query.addCriteria(Criteria.where("message").regex(logSearchCriteriaRequest.getMessage(), "i"));
		}
		if (logSearchCriteriaRequest.getResourceId() != null) {
			query.addCriteria(Criteria.where("resourceId").is(logSearchCriteriaRequest.getResourceId()));
		}
		if (logSearchCriteriaRequest.getTimestamp() != null) {
			query.addCriteria(Criteria.where("timestamp").is(logSearchCriteriaRequest.getTimestamp()));
		}
		if (logSearchCriteriaRequest.getTraceId() != null) {
			query.addCriteria(Criteria.where("traceId").is(logSearchCriteriaRequest.getTraceId()));
		}
		if (logSearchCriteriaRequest.getSpanId() != null) {
			query.addCriteria(Criteria.where("spanId").is(logSearchCriteriaRequest.getSpanId()));
		}
		if (logSearchCriteriaRequest.getCommit() != null) {
			query.addCriteria(Criteria.where("commit").is(logSearchCriteriaRequest.getCommit()));
		}
		if (logSearchCriteriaRequest.getParentResourceId() != null) {
			query.addCriteria(
					Criteria.where("metadata.parentResourceId").is(logSearchCriteriaRequest.getParentResourceId()));
		}
		if (logSearchCriteriaRequest.getFromDate() != null && logSearchCriteriaRequest.getToDate() != null) {
			LocalDateTime from = logSearchCriteriaRequest.getFromDate();
			LocalDateTime to = logSearchCriteriaRequest.getToDate().plusDays(1); // Include the end of the day
			query.addCriteria(Criteria.where("timestamp").gte(from).lt(to));
		}
		return mongoTemplate.find(query, LogEntity.class);
	}

}
