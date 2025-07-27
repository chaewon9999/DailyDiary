package org.example.dailydiary.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteUserResponseDto {

	private final String message = "정상적으로 삭제되었습니다.";

}