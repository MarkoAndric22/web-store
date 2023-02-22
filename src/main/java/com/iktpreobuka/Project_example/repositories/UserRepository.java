package com.iktpreobuka.Project_example.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.Project_example.entites.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer>{

	UserEntity findByusername(String username);
	UserEntity findByEmail(String email);
	
}
