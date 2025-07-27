package org.example.dailydiary.diary.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteDiaryResponseDto {

	private final String message = "성공적으로 삭제되었습니다.";

}
