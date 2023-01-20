package com.example.app.ws.ui.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.app.ws.service.UserService;
import com.example.app.ws.service.Impl.UserServiceImpl;
import com.example.app.ws.shared.dto.AddressDto;
import com.example.app.ws.shared.dto.UserDto;
import com.example.app.ws.ui.model.response.UserRest;

class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	
	@Mock
	UserService userService;
	
	
	UserDto userDto;
	
	final String USER_ID= "kjhdfou3irjfo83u4jdndcb98"; 

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		userDto = new UserDto();
		userDto.setFirstName("Agustin");
		userDto.setLastName("Nampula");
		userDto.setEmail("test@test.com");
		userDto.setEmailVerificationStatus(false);
		userDto.setEmailVerificationToken(null);
		userDto.setUserId(USER_ID);
		userDto.setAddresses(getAddressesDto());
		userDto.setEncryptedPassword("7dfyaifh732");
	}

	@Test
	void testGetUser() {
		when(userService.getUserByUserId(anyString())).thenReturn(userDto);
		
		UserRest userRest = userController.getUser(USER_ID);
		assertNotNull(userRest);
		assertEquals(USER_ID, userRest.getUserId());
		assertEquals(userDto.getFirstName(), userRest.getFirstName());
		assertTrue(userDto.getAddresses().size() == userRest.getAddresses().size());
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

}
