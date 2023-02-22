package com.iktpreobuka.Project_example.controllers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.iktpreobuka.Project_example.Service.VoucherService;
import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.BillEntity;
import com.iktpreobuka.Project_example.entites.CategoryEntity;
import com.iktpreobuka.Project_example.entites.OfferEntity;
import com.iktpreobuka.Project_example.entites.UserEntity;
import com.iktpreobuka.Project_example.entites.UserRole;
import com.iktpreobuka.Project_example.entites.VoucherEntity;
import com.iktpreobuka.Project_example.entites.dto.VoucherDTO;
import com.iktpreobuka.Project_example.repositories.OfferRepository;
import com.iktpreobuka.Project_example.repositories.UserRepository;
import com.iktpreobuka.Project_example.repositories.VoucherRepository;
import com.iktpreobuka.Project_example.security.Views;
import com.iktpreobuka.Project_example.utils.UserCustomValidator;

@RestController
@RequestMapping(path = "/project/vouchers")
public class VoucherController {

	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	VoucherService voucherService;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<VoucherEntity> GetAll() {
		return voucherRepository.findAll();

	}

	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public Iterable<VoucherEntity> GetAllPublic() {
		return voucherRepository.findAll();

	}

	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	public Iterable<VoucherEntity> GetAllPrivate() {
		return voucherRepository.findAll();

	}

	@JsonView(Views.Administator.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public Iterable<VoucherEntity> GetAllAdmin() {
		return voucherRepository.findAll();

	}

	@RequestMapping(method = RequestMethod.POST, value = "/{offerId}/buyer/{buyerId}")

	public ResponseEntity<?> addVoucher(@Valid @RequestBody VoucherDTO voucher, BindingResult result,@PathVariable Integer offerId, @PathVariable Integer buyerId) {
		
				try {
					if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		
				voucherService.addVoucher(voucher, offerId, buyerId);
		
				return new ResponseEntity<>(voucher, HttpStatus.OK);
				
			}catch (RESTError e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		
		}
			
	}
	

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifyVoucher(@PathVariable Integer id, @Valid @RequestBody VoucherDTO voucher,
			BindingResult result) {
		try {
			if (result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
	
			voucherService.modifyVoucher(id, voucher);
			return new ResponseEntity<>(voucher, HttpStatus.OK);
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> removeVoucher(@PathVariable Integer id) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(voucherService.removeVoucher(id));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findByBuyer/{buyerld}")
	public ResponseEntity<?> getByUserId(@PathVariable Integer buyerId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(voucherService.getByUserId(buyerId));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findByBuOffer/{offerld}")
	public ResponseEntity<?> findOffer(@PathVariable Integer offerId) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(voucherService.findOffer(offerId));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findNonExpiredVoucher")
	public List<VoucherEntity> nonExpiredVoucher() {

		return voucherService.nonExpiredVoucher();
	}
}
