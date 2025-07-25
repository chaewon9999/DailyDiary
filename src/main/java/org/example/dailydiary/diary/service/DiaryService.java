package org.example.dailydiary.diary.service;

import org.example.dailydiary.diary.dto.request.CreateDiaryRequestDto;
import org.example.dailydiary.diary.dto.request.UpdateDiaryRequestDto;
import org.example.dailydiary.diary.dto.response.CreateDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.DeleteDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.GetDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.UpdateDiaryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DiaryService {

	CreateDiaryResponseDto saveDiary(Long userId, CreateDiaryRequestDto requestDto);

	Page<GetDiaryResponseDto> getAllDiary(Long userId, Pageable pageable);

	GetDiaryResponseDto getDiaryById(Long userId, Long diaryId);

	UpdateDiaryResponseDto updateDiary(Long userId, Long diaryId, UpdateDiaryRequestDto requestDto);

	DeleteDiaryResponseDto deleteDiary(Long userId, Long diaryId);
}
