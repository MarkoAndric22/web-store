package com.iktpreobuka.Project_example.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.CategoryEntity;
import com.iktpreobuka.Project_example.entites.OfferEntity;
import com.iktpreobuka.Project_example.entites.UserEntity;
import com.iktpreobuka.Project_example.entites.UserRole;
import com.iktpreobuka.Project_example.entites.VoucherEntity;
import com.iktpreobuka.Project_example.entites.dto.VoucherDTO;
import com.iktpreobuka.Project_example.repositories.OfferRepository;
import com.iktpreobuka.Project_example.repositories.UserRepository;
import com.iktpreobuka.Project_example.repositories.VoucherRepository;

@Service

public class VoucherServiceImpl implements VoucherService {

	@Autowired
	VoucherRepository voucherRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferRepository offerRepository;

	@Override
	public VoucherEntity addVoucher(VoucherDTO voucher, Integer offerId, Integer buyerId) throws RESTError{
		UserEntity user = userRepository.findById(buyerId).get();
		OfferEntity offer = offerRepository.findById(offerId).get();
		VoucherEntity voucherEntity=new VoucherEntity();
		if(!user.getUser_role().equals(UserRole.ROLE_CUSTOMER)) {
			throw new RESTError(1,"User_role must be ROLE_CUSTOMER");
		}

		voucherEntity.setUser(user);
		voucherEntity.setOffer(offer);
		voucherEntity.setExpirationDate(voucher.getExpirationDate());
		voucherEntity.setUsed(false);
		
		
		return voucherRepository.save(voucherEntity);

	}

	@Override
	public VoucherEntity modifyVoucher(Integer id, VoucherDTO voucher) throws RESTError{
		for (VoucherEntity voucherE : voucherRepository.findAll()) {
			if (voucherE.getId().equals(id)) {
				voucherE.setExpirationDate(voucher.getExpirationDate());
				voucherE.setUsed(voucher.isUsed());
			return voucherRepository.save(voucherE);
			}
			
		}
		throw new RESTError(1,"voucher is not exists");
	}

	@Override
	public VoucherEntity removeVoucher(Integer id) throws RESTError{
			for (VoucherEntity voucher : voucherRepository.findAll()) {
				if (voucher.getId().equals(id)) {
					voucherRepository.deleteById(id);
					return voucher;
				}
			}throw new RESTError(1,"Voucher not exists");

	}

	@Override
	public Iterable<VoucherEntity> getByUserId(Integer buyerId) throws RESTError{
		if ((userRepository.findById(buyerId).isEmpty())) {
			throw new RESTError(1,"buyerId is not exists");
		} else
			return voucherRepository.findByUserId(buyerId);

	}

	@Override
	public VoucherEntity findOffer(Integer offerId) throws RESTError {
		if(offerRepository.findById(offerId).isEmpty()) {
			throw new RESTError(1,"offerId is not exists");
		}else
			return voucherRepository.findByOffer(offerId);
	}

	@Override
	public List<VoucherEntity> nonExpiredVoucher(){
		List<VoucherEntity> vouchers = new ArrayList<>();
		Date date=new Date();
		for(VoucherEntity voucherE:voucherRepository.findAll()) {
			if(voucherE.getExpirationDate().before(date)) {
				vouchers.add(voucherE);
			}
		}
		return vouchers;
	}


	
	public boolean voucherForUser(Integer userId) {
		for(VoucherEntity voucher : voucherRepository.findAll()) {
			if(voucher.getUser() != null) {
				if(voucher.getUser().getId()==userId) {
					return true;
				}
			}
		}
		return false;
	}
	

}
