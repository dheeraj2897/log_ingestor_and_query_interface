package com.neeraj.logingestor.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.neeraj.logingestor.model.MetaData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "LOGS_DATA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogEntity {
	@Id
	private String documentId;
	private String level;
	private String message;
	private String resourceId;
	private LocalDateTime timestamp;
	private String traceId;
	private String spanId;
	private String commit;
	private MetaData metadata;
}
