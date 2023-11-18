package com.neeraj.logingestor.controller;

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

@RestController
@RequestMapping("/logs")
public class LogController {
	@Autowired
	private LogService logService;
	
	@PostMapping(LogConstant.SAVE_LOG_DATA_URI)
	public ResponseEntity<String> saveLogData(@RequestBody LogRequest logRequest){
			logService.saveLogData(logRequest);
			return new ResponseEntity<>(LogConstant.SAVE_LOG_DATA_SUCCESS_MSG,HttpStatus.OK);
	}
}
