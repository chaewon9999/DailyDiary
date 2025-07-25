package org.example.dailydiary.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ErrorResponse> customException(CustomException e) {

		ErrorCode errorCode = e.getErrorCode();

		return ResponseEntity.status(errorCode.getStatus())
			.body(new ErrorResponse(errorCode));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> validException(MethodArgumentNotValidException e) {

		String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			.body(new ErrorResponse("INVALID_INPUT", message));
	}
}
