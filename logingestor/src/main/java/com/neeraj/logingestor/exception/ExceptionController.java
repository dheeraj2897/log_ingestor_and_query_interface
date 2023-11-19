package com.neeraj.logingestor.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mongodb.MongoException;
import com.neeraj.logingestor.constant.ErrorConstant;

@ControllerAdvice
public class ExceptionController {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		// System.out.println("Inside MethodArgumentNotValidException handling");
		BindingResult bindingResult = ex.getBindingResult();
		// Extract error messages from the BindingResult
		StringBuilder errorMessage = new StringBuilder("Validation failed. ");
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMessage.append(fieldError.getDefaultMessage()).append("; ");
		}
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.BAD_REQUEST.value());
		error.setErrorMsg(errorMessage.toString());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MongoException.class)
	public ResponseEntity<ErrorResponse> handleMongoException(Exception ex) {
		// System.out.println("Inside mongoException handler");
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setErrorMsg(ex.getMessage());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		// System.out.println("Inside General Exception handling");
		// Log the exception or perform additional handling
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setErrorMsg(ex.getLocalizedMessage());
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		// System.out.println("Inside General Exception handling");
		// Log the exception or perform additional handling
		ErrorResponse error = new ErrorResponse();
		error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setErrorMsg(ErrorConstant.INTERNAL_SERVER_ERROR_MSG);
		error.setTimestamp(LocalDateTime.now());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
