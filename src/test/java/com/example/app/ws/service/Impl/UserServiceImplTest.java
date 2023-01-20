package com.example.app.ws.service.Impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.app.ws.exceptions.UserServiceException;
import com.example.app.ws.io.entity.AddressEntity;
import com.example.app.ws.io.entity.UserEntity;
import com.example.app.ws.io.repositories.UserRepository;
import com.example.app.ws.shared.AmazonSES;
import com.example.app.ws.shared.Utils;
import com.example.app.ws.shared.dto.AddressDto;
import com.example.app.ws.shared.dto.UserDto;

class UserServiceImplTest {

	@InjectMocks
	UserServiceImpl userService;

	@Mock
	UserRepository userRepository;

	@Mock
	Utils utils;

	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Mock
	AmazonSES amazonSES;

	String userId = "dfasdfsafs";
	String encryptedPassword = "dfasfdsafadsf";
	UserEntity userEntity;
	String verificationToken = "k45j31hj2l43hh";

	@BeforeEach
	void setUp() throws Exception {
		// MockitoAnnotations.initMocks(this);
		MockitoAnnotations.openMocks(this);

		userEntity = new UserEntity();
		userEntity.setId(1L);
		userEntity.setFirstName("Agustin");
		userEntity.setLastName("Nampula");
		userEntity.setUserId(userId);
		userEntity.setEncryptedPassword(encryptedPassword);
		userEntity.setEmail("agustin.nmf@gmail.com");
		userEntity.setEmailVerificationToken(verificationToken);
		userEntity.setAddresses(getAddressesEntity());

	}

	@Test
	void testGetUser() {


		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

		UserDto userDto = userService.getUser("test@test.com");

		assertNotNull(userDto);
		assertEquals("Agustin", userDto.getFirstName());

	}

	@Test
	final void testGetUser_usernameNotFoundException() {
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		
		assertThrows(UsernameNotFoundException.class, 
				()->{
					userService.getUser("test@test.com");
				});
	}

	@Test
	final void testCreateUser_userServiceException() {
		when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
		Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("Agustin");
		userDto.setLastName("nampula");
		userDto.setPassword("12345678");
		userDto.setEmail("agustin.nmf@gmail.com");
		assertThrows(UserServiceException.class, 
				()->{
				userService.createUser(userDto);
				});
	}

	@Test
	final void testCreateUser(){
		
		when(userRepository.findByEmail(anyString())).thenReturn(null);
		when(utils.generateUserId(anyInt())).thenReturn(userId);
		when(utils.generateAddressId(anyInt())).thenReturn("131lk4hjl4jhl3hlk1");
		when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
		when(utils.generateToken(anyString(), anyString())).thenReturn(verificationToken);
		Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));
		

		when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
		
		UserDto userDto = new UserDto();
		userDto.setAddresses(getAddressesDto());
		userDto.setFirstName("Agustin");
		userDto.setLastName("nampula");
		userDto.setPassword("12345678");
		userDto.setEmail("agustin.nmf@gmail.com");
		//userDto
		UserDto storedUserDetails= userService.createUser(userDto);
		
		assertNotNull(storedUserDetails);
		assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
		assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
		assertNotNull(storedUserDetails.getUserId());
		assertEquals(userEntity.getAddresses().size(), storedUserDetails.getAddresses().size());
		
		verify(utils, times(userEntity.getAddresses().size())).generateAddressId(30);
		verify(bCryptPasswordEncoder, times(1)).encode("12345678");
		verify(userRepository, times(1)).save(any(UserEntity.class));
		//utils.generateUserId(30);
		
	}

	private List<AddressDto> getAddressesDto() {
		AddressDto addressDto = new AddressDto();
		addressDto.setType("shipping");
		addressDto.setCity("Dublin");
		addressDto.setCountry("United States");
		addressDto.setPostalCode("12345");
		addressDto.setStreetName("123 village pwkwy");

		AddressDto billingAddressDto = new AddressDto();
		billingAddressDto.setType("billing");
		billingAddressDto.setCity("Dublin");
		billingAddressDto.setCountry("United States");
		billingAddressDto.setPostalCode("12345");
		billingAddressDto.setStreetName("123 village pwkwy");

		List<AddressDto> addresses = new ArrayList<AddressDto>();
		addresses.add(addressDto);
		addresses.add(billingAddressDto);
		return addresses;
	}

	private List<AddressEntity> getAddressesEntity() {
		List<AddressDto> addresses = getAddressesDto();

		Type listType = new TypeToken<List<AddressEntity>>() {
		}.getType();

		return new ModelMapper().map(addresses, listType);
	}
}
