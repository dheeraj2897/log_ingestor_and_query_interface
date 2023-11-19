package com.neeraj.logingestor.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neeraj.logingestor.constant.LogConstant;
import com.neeraj.logingestor.model.LogRequest;
import com.neeraj.logingestor.service.LogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("v1/api/logs")
public class LogController {
	private static final Logger logger = LogManager.getLogger(LogController.class);

	@Autowired
	private LogService logService;

	@PostMapping(LogConstant.SAVE_LOG_DATA_URI)
	public ResponseEntity<String> saveLogData(@RequestBody @Valid LogRequest logRequest) {
		long startTime = System.currentTimeMillis();
		logger.info("Entering Inside saveLogData api of LogController");
		logService.saveLogData(logRequest);
		long elapsedTime = System.currentTimeMillis() - startTime;
		logger.info("Exiting saveLogData api of LogController, time Elapsed : " + elapsedTime + "ms");

		return new ResponseEntity<>(LogConstant.SAVE_LOG_DATA_SUCCESS_MSG, HttpStatus.CREATED);
	}
}
