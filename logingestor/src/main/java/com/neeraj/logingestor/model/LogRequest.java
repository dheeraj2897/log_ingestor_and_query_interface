package com.neeraj.logingestor.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.neeraj.logingestor.constant.LogConstant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogRequest {
	@JsonProperty("level")
	private String level;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("resourceId")
	private String resourceId;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = LogConstant.TIMESTAMP_FORMAT)
	private LocalDateTime timestamp;
	
	@JsonProperty("traceId")
	private String traceId;
	
	@JsonProperty("spanId")
	private String spanId;
	
	@JsonProperty("commit")
	private String commit;
	
	@JsonProperty("metadata")
	private MetaData metadata;
}
