package com.iktpreobuka.Project_example.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.Project_example.entites.dto.UseEntityrDto;

@Component
public class UserCustomValidator implements Validator {

	@Override
	public boolean supports(Class<?> myClass) {
		return UseEntityrDto.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UseEntityrDto user = (UseEntityrDto) target;
		if(!user.getPassword().equals(user.getRepeatedPassword())) {
		errors.reject("400", "Passwords must be the same");
		}
		
	}

}
