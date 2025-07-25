package org.example.dailydiary.diary.service;

import org.example.dailydiary.diary.dto.request.CreateDiaryRequestDto;
import org.example.dailydiary.diary.dto.response.CreateDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.GetDiaryResponseDto;
import org.example.dailydiary.diary.entity.Diary;
import org.example.dailydiary.diary.repository.DiaryRepository;
import org.example.dailydiary.user.entity.User;
import org.example.dailydiary.user.service.UserServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

	private final DiaryRepository diaryRepository;
	private final UserServiceImpl userService;

	@Override
	@Transactional
	public CreateDiaryResponseDto saveDiary(Long userId, CreateDiaryRequestDto requestDto) {

		User user = userService.findUserById(userId);

		Diary diary = Diary.builder()
			.title(requestDto.getTitle())
			.contents(requestDto.getContents())
			.feeling(requestDto.getFeeling())
			.user(user)
			.build();

		Diary savedDiary = diaryRepository.save(diary);

		return new CreateDiaryResponseDto(savedDiary.getId(), "일기를 저장했습니다");
	}

	@Override
	@Transactional(readOnly = true)
	public Page<GetDiaryResponseDto> getAllDiary(Long userId, Pageable pageable) {

		Page<Diary> diaries = diaryRepository.findByUserId(userId, pageable);

		return diaries.map(GetDiaryResponseDto::new);
	}
}
