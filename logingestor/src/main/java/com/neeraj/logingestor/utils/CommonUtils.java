package com.neeraj.logingestor.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.neeraj.logingestor.entity.LogEntity;
import com.neeraj.logingestor.model.LogRequest;

@Component
public class CommonUtils {

	public LogEntity logModelToEntityMapper(LogRequest logRequest) {
		LogEntity logEntity = new LogEntity();
		BeanUtils.copyProperties(logRequest, logEntity);
		return logEntity;
	}

	public List<LogRequest> getModelListFromEntityList(List<LogEntity> entityList) {
		return entityList.stream()
                .map(this::convertToLogRequest)
                .collect(Collectors.toList());
	}
	
	private LogRequest convertToLogRequest(LogEntity logEntity) {
        LogRequest logRequest = new LogRequest();
        BeanUtils.copyProperties(logEntity, logRequest);
        return logRequest;
    }

}
