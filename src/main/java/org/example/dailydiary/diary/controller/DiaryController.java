package org.example.dailydiary.diary.controller;

import org.example.dailydiary.common.security.CustomUserPrincipal;
import org.example.dailydiary.diary.dto.request.CreateDiaryRequestDto;
import org.example.dailydiary.diary.dto.request.UpdateDiaryRequestDto;
import org.example.dailydiary.diary.dto.response.CreateDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.GetDiaryResponseDto;
import org.example.dailydiary.diary.dto.response.UpdateDiaryResponseDto;
import org.example.dailydiary.diary.service.DiaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

	private final DiaryService diaryService;

	@PostMapping
	public ResponseEntity<CreateDiaryResponseDto> createDiary(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@RequestBody @Valid CreateDiaryRequestDto requestDto
	) {
		CreateDiaryResponseDto response = diaryService.saveDiary(principal.userIdConverter(), requestDto);

		return ResponseEntity.status(HttpStatus.CREATED)
			.body(response);
	}

	@GetMapping
	public ResponseEntity<Page<GetDiaryResponseDto>> getAllDiary(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
	) {
		Page<GetDiaryResponseDto> diaries = diaryService.getAllDiary(principal.userIdConverter(), pageable);

		return ResponseEntity.status(HttpStatus.OK)
			.body(diaries);
	}

	@GetMapping("/{diaryId}")
	public ResponseEntity<GetDiaryResponseDto> getDiaryById(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PathVariable Long diaryId
	) {
		GetDiaryResponseDto responseDto = diaryService.getDiaryById(principal.userIdConverter(), diaryId);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}

	@PutMapping("/{diaryId}")
	public ResponseEntity<UpdateDiaryResponseDto> updateDiary(
		@AuthenticationPrincipal CustomUserPrincipal principal,
		@PathVariable Long diaryId,
		@RequestBody @Valid UpdateDiaryRequestDto requestDto
	) {
		UpdateDiaryResponseDto responseDto = diaryService.updateDiary(principal.userIdConverter(), diaryId, requestDto);

		return ResponseEntity.status(HttpStatus.OK)
			.body(responseDto);
	}
}
