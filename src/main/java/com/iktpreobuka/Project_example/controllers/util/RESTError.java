package com.iktpreobuka.Project_example.controllers.util;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.Project_example.security.Views;

public class RESTError extends Exception {
	
	@JsonView(Views.Public.class)
	private Integer code;
	@JsonView(Views.Public.class)
	private String message;
	public RESTError(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public RESTError() {
		super();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
