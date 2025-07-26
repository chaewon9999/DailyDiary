package org.example.dailydiary.diary.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateDiaryResponseDto {

	@Schema(description = "다이어리 아이디", example = "1")
	private final Long diaryId;

	private final String message;
}
