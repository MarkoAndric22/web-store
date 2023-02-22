package com.iktpreobuka.Project_example.Service;

import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.UserEntity;
import com.iktpreobuka.Project_example.entites.UserRole;
import com.iktpreobuka.Project_example.entites.dto.UseEntityrDto;

public interface UserService {
	
	public Optional<UserEntity> user(Integer id) throws RESTError;
	
	public UserEntity addUser(UseEntityrDto users) throws RESTError;
	
	public UserEntity change(Integer id,UserRole role) throws RESTError;
	
	public UserEntity password(Integer id,String oldPassword,String newPassword) throws RESTError;
	
	public UserEntity delete(Integer id) throws RESTError;
	
	public UserEntity usernames(String username);

	UserEntity modify(Integer id, UseEntityrDto user) throws RESTError;

	public UserEntity userForVoucher(Integer userId);

}
