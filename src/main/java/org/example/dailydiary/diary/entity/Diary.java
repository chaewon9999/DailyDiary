package org.example.dailydiary.diary.entity;

import org.example.dailydiary.common.entity.BaseEntity;
import org.example.dailydiary.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_diary")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Diary extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Feeling feeling;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Builder
	public Diary(String title, String contents, Feeling feeling, User user) {
		this.title = title;
		this.contents = contents;
		this.feeling = feeling;
		this.user = user;
	}
}
