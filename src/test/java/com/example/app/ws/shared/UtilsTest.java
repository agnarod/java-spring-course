package com.example.app.ws.shared;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.app.ws.security.SecurityConstants;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UtilsTest {

	@Autowired
	Utils utils;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testGenerateUserId() {
		String userId = utils.generateUserId(30);
		String userId2 = utils.generateUserId(30);

		assertNotNull(userId);
		assertNotNull(userId2);

		assertTrue(userId.length() == 30);
		assertTrue(!userId.equalsIgnoreCase(userId2));

	}

	@Test
	void testHasTokenNonExpired() {
		String token = utils.generateToken("3uoi2kjrfhfk23", SecurityConstants.DEFAULT_TYPE_TOKEN_STRING);

		assertNotNull(token);

		boolean hasExpired = utils.hasTokenExpired(token);
		assertFalse(hasExpired);
	}

	@Test
	void testHasTokenExpired() {
		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI2MDhMTVVJNHpCNzhJWVFyWHNBbkFZamRLem9xWjciLCJleHAiOjE2NzQwOTAzMDJ9.g-XLJkFJY4ySL9LwwNpQ7rupaGSWyP79BIbfyJQvVjd444hS7kiYSWORoDvGVHcHlwNXVRkIVHDYAdFlnH3J1Q";
		
		boolean hasExpired = utils.hasTokenExpired(token);
		assertTrue(hasExpired);
	}

}
