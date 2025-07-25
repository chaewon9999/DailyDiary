package org.example.dailydiary.diary.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Feeling {

	HAPPY("기쁨"),
	SAD("슬픔"),
	ANGRY("화남"),
	EXCITED("즐거움"),
	TIRED("피곤함"),
	NEUTRAL("평범함");

	private final String description;
}
