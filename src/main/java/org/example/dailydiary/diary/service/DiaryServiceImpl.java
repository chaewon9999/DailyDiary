package org.example.dailydiary.diary.service;

import org.example.dailydiary.diary.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService{

	private final DiaryRepository diaryRepository;
}
