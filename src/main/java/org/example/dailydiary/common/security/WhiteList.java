package org.example.dailydiary.common.security;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class WhiteList {

	private final List<String> urls = List.of(
		"/user/signup",
		"/user/login",
		"/auth/reissue",

		//swagger
		"/swagger-ui/**",
		"/v3/api-docs/**"

	);
}
