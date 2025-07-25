package org.example.dailydiary.diary.service;

import org.example.dailydiary.diary.dto.request.CreateDiaryRequestDto;
import org.example.dailydiary.diary.dto.response.CreateDiaryResponseDto;

public interface DiaryService {

	CreateDiaryResponseDto saveDiary(Long userId, CreateDiaryRequestDto requestDto);
}
