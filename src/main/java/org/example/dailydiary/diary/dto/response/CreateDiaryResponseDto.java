package org.example.dailydiary.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateDiaryResponseDto {

	private final Long diaryId;

	private final String message;


}
