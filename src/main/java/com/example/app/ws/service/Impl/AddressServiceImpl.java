package com.example.app.ws.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.ws.io.entity.AddressEntity;
import com.example.app.ws.io.entity.UserEntity;
import com.example.app.ws.io.repositories.AddressRepository;
import com.example.app.ws.io.repositories.UserRepository;
import com.example.app.ws.service.AddressService;
import com.example.app.ws.shared.dto.AddressDto;


@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	

	@Override
	public List<AddressDto> getAddresses(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		List<AddressDto> returnValue = new ArrayList<AddressDto>();
		if(userEntity == null ) return returnValue;
		
		ModelMapper modelMapper = new ModelMapper();
		Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
		
		for(AddressEntity addressEntity:addresses) {
			returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
		}
		
		return returnValue;
	}


	@Override
	public AddressDto getAddress(String addressId) {
		AddressDto returnValue = null;
		
		AddressEntity addressEntity = addressRepository.findByAddressId(addressId);
		
		if(addressEntity != null) {
			returnValue = new ModelMapper().map(addressEntity, AddressDto.class);
		}
		
		return returnValue;
		
		
	}

}
