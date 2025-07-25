package org.example.dailydiary.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	;

	private final HttpStatus status;
	private final String message;
}
