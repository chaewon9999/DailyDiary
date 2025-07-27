package org.example.dailydiary.common.security;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.example.dailydiary.user.entity.User;
import org.example.dailydiary.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtFilterTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@MockitoBean
	private CustomUserDetailsService userDetailsService;

	private User user;

	@BeforeEach
	void setUp() {
		this.user = User.builder()
			.email("test@test.com")
			.password(passwordEncoder.encode("password"))
			.nickname("nickname")
			.build();
		ReflectionTestUtils.setField(user, "id", 1L);
		ReflectionTestUtils.setField(user,"role", UserRole.USER);
	}

	@Test
	void 필터가_정상_작동한다() throws Exception {
		//given
		String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getRole());

		when(userDetailsService.loadUserByUsername(String.valueOf(user.getId())))
			.thenReturn(new CustomUserPrincipal(user));

		//when & then
		mockMvc.perform(get("/diary")
			.header("Authorization",accessToken))
			.andExpect(status().isOk());
	}
}
