package com.iktpreobuka.Project_example.controllers;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.Project_example.Service.BillService;
import com.iktpreobuka.Project_example.Service.OfferService;
import com.iktpreobuka.Project_example.Service.UserService;
import com.iktpreobuka.Project_example.Service.VoucherService;
import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.UserEntity;
import com.iktpreobuka.Project_example.entites.UserRole;
import com.iktpreobuka.Project_example.entites.dto.UseEntityrDto;
import com.iktpreobuka.Project_example.entites.dto.UserDTO;
import com.iktpreobuka.Project_example.repositories.UserRepository;
import com.iktpreobuka.Project_example.security.Views;
import com.iktpreobuka.Project_example.utils.Encryption;
import com.iktpreobuka.Project_example.utils.UserCustomValidator;

import io.jsonwebtoken.Jwts;

@RestController
//@RequestMapping(path = "/project/users")
public class UserController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	@Autowired
	OfferService offerService;
	@Autowired
	VoucherService voucherService;
	@Autowired
	BillService billService;
	@Autowired
	UserCustomValidator userValidator;
	@Autowired
	private SecretKey secretKey;
	@Value("${spring.security.token-duration}")
	private Integer tokenDuration;
	
	@InitBinder
	protected void initBinder(final WebDataBinder binder)
	{
	binder.addValidators(userValidator);
	}

	private String getJWTToken(UserEntity userEntity) {
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
		.commaSeparatedStringToAuthorityList(userEntity.getRole().getName());
		String token = Jwts.builder().setId("softtekJWT").setSubject(userEntity.getEmail())
		.claim("authorities", grantedAuthorities.stream()
		.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
		.setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + this.tokenDuration))
		.signWith(this.secretKey).compact();
		return "Bearer " + token;
		}
	
	@RequestMapping(path = "/api/v1/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestParam("user") String email, @RequestParam("password")
	String pwd) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity != null && Encryption.validatePassword(pwd, userEntity.getPassword())) {
			String token = getJWTToken(userEntity);
			UserDTO user = new UserDTO();
			user.setUser(email);
			user.setToken(token);
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}
	
	@Secured("ROLE_USER")
	@RequestMapping(path = "/api/v1/users", method = RequestMethod.GET)
	public ResponseEntity<?> listUsers() {
		return new ResponseEntity<List<UserEntity>>((List<UserEntity>) userRepository.findAll(),HttpStatus.OK);
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> getAll() {
		return userRepository.findAll();
	}
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET,value="/public")
	public Iterable<UserEntity> getAllPublic() {
		return userRepository.findAll();
	}
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET,value="/private")
	public Iterable<UserEntity> getAllPrivate() {
		return userRepository.findAll();
	}
	@JsonView(Views.Administator.class)
	@RequestMapping(method = RequestMethod.GET,value="/admin")
	public Iterable<UserEntity> getAllPublicAdmin() {
		return userRepository.findAll();
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> user(@PathVariable Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.user(id));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@Valid @RequestBody UseEntityrDto users, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} else {
				userValidator.validate(users, result);
				}
			userService.addUser(users);
			return new ResponseEntity<>(users, HttpStatus.OK);
		}catch(RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modify(@PathVariable Integer id,@Valid @RequestBody UseEntityrDto user,BindingResult result) {
		
		try {
			if(result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
			}
			userService.modify(id, user);
			return new ResponseEntity<>(user, HttpStatus.OK);
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/change/{id}/role/{role}")
	public ResponseEntity<?> change(@PathVariable Integer id, @PathVariable UserRole role) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.change(id, role));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@RequestMapping(method = RequestMethod.PUT, value = "/changePassword/{id}")
	public ResponseEntity<?> password(@PathVariable Integer id, @RequestParam String oldPassword,@RequestParam String newPassword) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(userService.password(id, oldPassword, newPassword));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		try {
			if(offerService.offerForUser(id) || billService.billForUser(id) || voucherService.voucherForUser(id)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete user (foreign key) ");
			}else {
				return ResponseEntity.status(HttpStatus.OK).body(userService.delete(id));
			}
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/by-username/{username}")
	public UserEntity usernames(@PathVariable String username) {
		
		return userService.usernames(username);
	}
}
