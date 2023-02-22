package com.iktpreobuka.Project_example.Service;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.VoucherEntity;
import com.iktpreobuka.Project_example.entites.dto.VoucherDTO;

public interface VoucherService {
	
	public VoucherEntity removeVoucher(Integer id) throws RESTError;
	
	public Iterable<VoucherEntity> getByUserId(Integer buyerId) throws RESTError;
	
	public VoucherEntity findOffer(Integer offerId) throws RESTError;
	
	public List<VoucherEntity> nonExpiredVoucher();

	VoucherEntity addVoucher(VoucherDTO voucher, Integer offerId, Integer buyerId) throws RESTError;
	
	boolean voucherForUser(Integer userId) ;

	VoucherEntity modifyVoucher(Integer id, VoucherDTO voucher) throws RESTError;
}
