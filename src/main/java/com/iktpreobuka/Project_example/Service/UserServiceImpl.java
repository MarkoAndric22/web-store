package com.iktpreobuka.Project_example.Service;

import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.CategoryEntity;
import com.iktpreobuka.Project_example.entites.UserEntity;
import com.iktpreobuka.Project_example.entites.UserRole;
import com.iktpreobuka.Project_example.entites.VoucherEntity;
import com.iktpreobuka.Project_example.entites.dto.UseEntityrDto;
import com.iktpreobuka.Project_example.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	

	@Override
	public Optional<UserEntity> user(Integer id) throws RESTError {
		if (userRepository.existsById(id)) {
			return userRepository.findById(id);
		}
		throw new RESTError(1, "user is not exists");
	}

	@Override
	public UserEntity addUser(UseEntityrDto users) throws RESTError {
		UserEntity user = new UserEntity();
		user.setFirst_name(users.getFirst_name());
		user.setLast_name(users.getLast_name());
		user.setUsername(users.getUsername());
		user.setEmail(users.getEmail());

		if (users.getPassword().equals(users.getRepeatedPassword())) {
			user.setPassword(users.getPassword());
		} else {
			throw new RESTError(1, "Password not match!");
		}

		if (user.getUser_role() == null) {
			user.setUser_role(UserRole.ROLE_CUSTOMER);
		}
		return userRepository.save(user);
	}

	@Override
	public UserEntity modify(Integer id, UseEntityrDto user) throws RESTError {

		for (UserEntity userE : userRepository.findAll()) {
			if (userE.getId().equals(id)) {
				userE.setFirst_name(user.getFirst_name());
				userE.setLast_name(user.getLast_name());
				userE.setUsername(user.getUsername());
				userE.setEmail(user.getEmail());
				userE.setPassword(user.getPassword());

				return userRepository.save(userE);
			}
		}
		throw new RESTError(1, "user is not exists");
	}

	@Override
	public UserEntity change(Integer id, UserRole role) throws RESTError {
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(id)) {
				for (UserRole userRole : UserRole.values()) {
					if (userRole.equals(role)) {
						user.setUser_role(role);
						return user;
					}
					throw new RESTError(1, "role is not exists");
				}
			}
		}
		throw new RESTError(1, "user is not exists");
	}

	@Override
	public UserEntity password(Integer id, String oldPassword, String newPassword) throws RESTError {
		for (UserEntity user : userRepository.findAll()) {
			if (user.getId().equals(id)) {
				if (user.getPassword().equals(oldPassword)) {

					user.setPassword(newPassword);
					return user;
				}
				throw new RESTError(1, "password dont match");
			}
		}
		throw new RESTError(1, "id not exist");
	}

	@Override
	public UserEntity delete(Integer id) throws RESTError {
//		for (UserEntity user : userRepository.findAll()) {
//			if (user.getId().equals(id)) {
//				userRepository.delete(user);
//			}
//		}
//		throw new RESTError(1, "user is not exists");
		
		if(!userRepository.findById(id).isPresent()) {
			throw new RESTError(1,"user not exists");
		}UserEntity user = userRepository.findById(id).get();
			userRepository.deleteById(id);
			return user;
	}

	@Override
	public UserEntity usernames(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserEntity userForVoucher(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
