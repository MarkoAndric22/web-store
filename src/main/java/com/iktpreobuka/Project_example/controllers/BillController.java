package com.iktpreobuka.Project_example.controllers;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.Project_example.Service.BillService;
import com.iktpreobuka.Project_example.Service.OfferService;
import com.iktpreobuka.Project_example.Service.ReportService;
import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.BillEntity;
import com.iktpreobuka.Project_example.entites.dto.BillDTO;
import com.iktpreobuka.Project_example.repositories.BillRepository;
import com.iktpreobuka.Project_example.repositories.OfferRepository;
import com.iktpreobuka.Project_example.repositories.UserRepository;
import com.iktpreobuka.Project_example.security.Views;

@RestController
@RequestMapping(path = "/project/bills")
public class BillController {

	@Autowired
	BillRepository billRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	BillService billService;
	@Autowired
	OfferService offerService;
	@Autowired
	ReportService reportService;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<BillEntity> getAll() {
		return billRepository.findAll();

	}
	@JsonView(Views.Public.class)
	@RequestMapping(method = RequestMethod.GET,value="/public")
	public Iterable<BillEntity> getAllPrivate() {
		return billRepository.findAll();

	}
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.GET,value="/private")
	public Iterable<BillEntity> getAllPublic() {
		return billRepository.findAll();

	}
	@JsonView(Views.Administator.class)
	@RequestMapping(method = RequestMethod.GET,value="/admin")
	public Iterable<BillEntity> getAllAdmin() {
		return billRepository.findAll();

	}

	@RequestMapping(method = RequestMethod.POST, value = "/{offerId}/buyer/{buyerId}")
	public ResponseEntity<?> addBill(@Valid @RequestBody BillDTO bill,BindingResult result, @PathVariable Integer offerId,@PathVariable Integer buyerId)  {
		if(result.hasErrors()) {
			return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
		}
		billService.addBill(bill, offerId, buyerId);
		return new ResponseEntity<>(bill, HttpStatus.OK);
	}
		
	
	private String createErrorMessage(BindingResult result) {
			return result.getAllErrors().stream().map(ObjectError::getDefaultMessage)
			.collect(Collectors.joining(" "));

	}

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> modifyBill(@PathVariable Integer id,@Valid @RequestBody BillDTO bill,BindingResult result)  {
		try {
			if(result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result),HttpStatus.BAD_REQUEST);
			}
			billService.modifyBill(id, bill);
			return new ResponseEntity<>(bill, HttpStatus.OK);

		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> removeBill(@PathVariable Integer id)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(billService.removeBill(id));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/findByBuyerId/{buyerId}")
	public ResponseEntity<?> getByUserId(@PathVariable Integer buyerId)  {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(billService.getByUserId(buyerId));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/findByCategory/{categoryId}")
	public ResponseEntity<?> getByCategoryId(@PathVariable Integer categoryId)  {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(billService.getByCategoryId(categoryId));
		} catch (RESTError e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/findByDate/{startDate}/and/{endDate}")
	public ResponseEntity<?> findByDate(@PathVariable  @DateTimeFormat(pattern =  "yyyy-MM-dd") Date startDate, @PathVariable  @DateTimeFormat(pattern =  "yyyy-MM-dd") Date endDate) {

		try {
			return ResponseEntity.status(HttpStatus.OK).body(billService.findByDate(startDate, endDate));
		} catch (EntityExistsException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
//	@RequestMapping(method = RequestMethod.GET, value = "/generateReportByDate/{startDate}/and/{endDate}")
//	public ResponseEntity<?> SellByDays(@PathVariable Date startDate, @PathVariable Date endDate)throws ParseException{
//		
//		try {
//			return ResponseEntity.status(HttpStatus.OK).body(billService.findByDate(startDate, endDate));
//		} catch (EntityExistsException e) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//		}
//		
//	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/generateReportByDate/{startDate}/and/{endDate}")
	public ResponseEntity<?> generateReportItems(@PathVariable @DateTimeFormat(pattern =  "yyyy-MM-dd") Date startDate, @PathVariable @DateTimeFormat(pattern =  "yyyy-MM-dd") Date endDate){
		return ResponseEntity.status(HttpStatus.OK).body(reportService.generateReportByDatesBetween(startDate, endDate));
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/generateReportByDate/{startDate}/and/{endDate}/category/{categoryId}")
	public ResponseEntity<?> bill(@PathVariable @DateTimeFormat(pattern =  "yyyy-MM-dd") Date startDate, @PathVariable @DateTimeFormat(pattern =  "yyyy-MM-dd") Date endDate, @PathVariable Integer categoryId){
		return ResponseEntity.status(HttpStatus.OK).body(reportService.generateReportByDatesBetweenAndCategory(startDate, endDate, categoryId));
		
	}
}
