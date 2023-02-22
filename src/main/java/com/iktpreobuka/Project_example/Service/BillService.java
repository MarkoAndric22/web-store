package com.iktpreobuka.Project_example.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.BillEntity;
import com.iktpreobuka.Project_example.entites.dto.BillDTO;

public interface BillService {
	
	public BillEntity addBill(BillDTO bill,Integer offerId,Integer buyerId);
	
	public BillEntity modifyBill(Integer id,BillDTO bill) throws RESTError;
	
	public BillEntity removeBill(Integer id) throws RESTError;
	
	public Iterable<BillEntity> getByUserId(Integer userId) throws RESTError;
	
	public List<BillEntity> getByCategoryId(Integer categoryId) throws RESTError;
	
	public List<BillEntity> findByDate(Date startDate,Date endDate);
	
	public boolean billForCategory(Integer categoryId) throws RESTError; 
	
	boolean billForUser(Integer userId);
}
