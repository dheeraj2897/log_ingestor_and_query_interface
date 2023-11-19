package com.neeraj.logingestor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neeraj.logingestor.model.LogRequest;
import com.neeraj.logingestor.model.LogSearchCriteriaRequest;

@Service
public interface LogService {

	public void saveLogData(LogRequest logRequest);
	
	public List<LogRequest> searchLogsByCriteria(LogSearchCriteriaRequest logSearchCriteriaRequest);

	public List<LogRequest> findAll();

}
