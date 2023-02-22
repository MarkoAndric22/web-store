package com.iktpreobuka.Project_example.entites.dto;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.Project_example.security.Views;

public class UseEntityrDto {
	
	@Column(nullable=false)
	@JsonView(Views.Private.class)
	@NotNull(message="First name must be provided")
	
	@Size(min=2,max=30, message= "First name must be beetwen {min} and {max} characters long.")
	protected String first_name;
	
	@Column(nullable=false)
	@JsonView(Views.Private.class)
	@NotNull(message="Last name must be provided")
	@Size(min=2,max=30, message= "Last name must be beetwen {min} and {max} characters long.")
	protected String last_name;
	
	
	@Column(nullable=false)
	@JsonView(Views.Public.class)
	@NotNull(message="username must be provided")
	@Size(min=5,max=20, message= "username must be beetwen {min} and {max} characters long.")
	private String username;
	
	@Column(nullable=false)
	@JsonView(Views.Private.class)
	@NotNull(message="Password must be provided")
	@Min(value =5, message= "Password must have 5 or more letters")
	private String password;
	
	private String repeatedPassword;
	
	@Column(nullable=false)
	@JsonView(Views.Private.class)
	@NotNull(message="Email must be provided")
	@Size(min=2,max=30, message= "Email must be beetwen {min} and {max} characters long.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",
	message="Email is not valid.")
	private String email;
	
	public UseEntityrDto(String first_name, String last_name, String username,
			@NotNull(message = "Password must be provided") @Min(value = 5, message = "Password must have 5 or more letters") String password,
			String repeatedPassword, String email) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.username = username;
		this.password = password;
		this.repeatedPassword = repeatedPassword;
		this.email = email;
	}
	
	
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}


}
