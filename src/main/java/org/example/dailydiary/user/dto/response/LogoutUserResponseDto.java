package org.example.dailydiary.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutUserResponseDto {

	private final String message = "로그아웃을 완료했습니다.";
}
