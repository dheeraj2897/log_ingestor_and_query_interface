package com.neeraj.logingestor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neeraj.logingestor.entity.LogEntity;
import com.neeraj.logingestor.model.LogRequest;
import com.neeraj.logingestor.repo.LogRepository;
import com.neeraj.logingestor.service.LogService;
import com.neeraj.logingestor.utils.LogUtils;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogRepository logRepository;

	@Autowired
	private LogUtils logUtils;

	@Override
	public void saveLogData(LogRequest logRequest) {
		// mapping logRequest to entity
		LogEntity logEntity = logUtils.logModelToEntityMapper(logRequest);
		logRepository.save(logEntity);

	}

}
