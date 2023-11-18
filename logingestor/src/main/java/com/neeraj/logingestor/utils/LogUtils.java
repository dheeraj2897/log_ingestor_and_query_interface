package com.neeraj.logingestor.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.neeraj.logingestor.entity.LogEntity;
import com.neeraj.logingestor.model.LogRequest;

@Component
public class LogUtils {

	public LogEntity logModelToEntityMapper(LogRequest logRequest) {
		LogEntity logEntity = new LogEntity();
//		logEntity.setCommit(logRequest.getCommit());
//		logEntity.setLevel(logRequest.getLevel());
//		logEntity.setMessage(logRequest.getMessage());
//		MetaData metaDataEntity = new MetaData();
//		metaDataEntity.setParentResourceId(logRequest.getMetadata().getParentResourceId());
//		logEntity.setMetadata(metaDataEntity);
//		//Not working
		BeanUtils.copyProperties(logRequest, logEntity);
		return logEntity;
	}

}
