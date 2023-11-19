package com.neeraj.logingestor.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.logingestor.constant.LogConstant;
import com.neeraj.logingestor.model.LogRequest;
import com.neeraj.logingestor.model.LogSearchCriteriaRequest;
import com.neeraj.logingestor.service.LogService;

@RestController
@RequestMapping("v1/api/logs")
public class LogSearchController {
	private static final Logger logger = LogManager.getLogger(LogSearchController.class);

	@Autowired
	private LogService logService;

	@PostMapping(LogConstant.SEARCH_LOGS_BY_CRITERIA_URI)
	public ResponseEntity<List<LogRequest>> searchLogsByCriteria(
			@RequestBody LogSearchCriteriaRequest logSearchCriteriaRequest) {
		long startTime = System.currentTimeMillis();
		logger.info("Entering Inside searchLogsByCriteria api of LogSearchController");
		List<LogRequest> searchResultList = logService.searchLogsByCriteria(logSearchCriteriaRequest);
		long elapsedTime = System.currentTimeMillis() - startTime;
		logger.info("Exiting searchLogsByCriteria api of LogSearchController, time Elapsed : " + elapsedTime + "ms");
		return new ResponseEntity<>(searchResultList, HttpStatus.OK);
	}

	@GetMapping(LogConstant.SEARCH_ALL_LOGS_URI)
	public ResponseEntity<List<LogRequest>> findAllLogs() {
		long startTime = System.currentTimeMillis();
		logger.info("Entering Inside findAllLogs api of LogSearchController");
		List<LogRequest> searchResultList = logService.findAll();
		long elapsedTime = System.currentTimeMillis() - startTime;
		logger.info("Exiting findAllLogs api of LogSearchController, time Elapsed : " + elapsedTime + "ms");
		return new ResponseEntity<>(searchResultList, HttpStatus.OK);
	}
}
