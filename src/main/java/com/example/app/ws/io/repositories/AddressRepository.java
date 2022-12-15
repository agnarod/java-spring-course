package com.example.app.ws.io.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.app.ws.io.entity.AddressEntity;
import com.example.app.ws.io.entity.UserEntity;



@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
	
	AddressEntity findByAddressId(String addressId);
	List<AddressEntity> findAllByUserDetails(UserEntity userDetails);

}
