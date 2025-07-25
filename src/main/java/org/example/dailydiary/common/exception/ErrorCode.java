package org.example.dailydiary.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	//common
	FORBIDDEN(HttpStatus.FORBIDDEN, "접근할 권한이 없습니다."),

	//user
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
	EXIST_USER(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다."),
	PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

	//diary
	DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 일기입니다.")
	;

	private final HttpStatus status;
	private final String message;
}
