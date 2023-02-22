package com.iktpreobuka.Project_example.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.BillEntity;
import com.iktpreobuka.Project_example.entites.OfferEntity;
import com.iktpreobuka.Project_example.entites.OfferStatus;
import com.iktpreobuka.Project_example.entites.UserEntity;
import com.iktpreobuka.Project_example.entites.VoucherEntity;
import com.iktpreobuka.Project_example.entites.dto.BillDTO;
import com.iktpreobuka.Project_example.entites.dto.ReportDto;
import com.iktpreobuka.Project_example.entites.dto.ReportItem;
import com.iktpreobuka.Project_example.repositories.BillRepository;
import com.iktpreobuka.Project_example.repositories.CategoryRepository;
import com.iktpreobuka.Project_example.repositories.OfferRepository;
import com.iktpreobuka.Project_example.repositories.UserRepository;

@Service
public class BillServiceImpl implements BillService {
	@Autowired
	BillRepository billRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	OfferService offerService;

	@Override
	public BillEntity addBill(BillDTO bill, Integer offerId, Integer buyerId)  {

		UserEntity user = userRepository.findById(buyerId).get();
		OfferEntity offer = offerRepository.findById(offerId).get();
		BillEntity billEntity=new BillEntity();
		billEntity.setUser(user);
		billEntity.setOffer(offer);
//		if (bill.isPaymentCanceled()) {
//			billEntity.setPaymentMade(false);
//		}else {
//			billEntity.setPaymentMade(true);
//		}
		billEntity.setBillCreated(bill.getBillCreated());
		offerService.availableAndBoughtOffers(offerId);
		return billRepository.save(billEntity);

	}

	@Override
	public BillEntity modifyBill(Integer id, BillDTO bill) throws RESTError{
		
		for (BillEntity billE : billRepository.findAll()) {
			if (billE.getId().equals(id)) {
				billE.setBillCreated(bill.getBillCreated());
				if (bill.isPaymentCanceled()) {
					bill.setPaymentMade(false);
				}else {
					bill.setPaymentMade(true);
					
				}
				BillEntity b = new BillEntity();
				b.setId(id);
				b.setPaymentMade(bill.isPaymentMade());
				b.setPaymentCanceled(bill.isPaymentCanceled());
				b.setBillCreated(bill.getBillCreated());
				b.setOffer(billE.getOffer());
				b.setUser(billE.getUser());
				offerService.availableAndBoughtOffers(billE.getOffer().getId());
				return billRepository.save(b);
			}
		}
			throw new RESTError(1,"Bill not exist");
	}

	@Override
	public BillEntity removeBill(Integer id) throws RESTError{
		for (BillEntity billE : billRepository.findAll()) {
			if (billE.getId().equals(id)) {
				billRepository.delete(billE);
				return billE;
			}
		}
		throw new RESTError(1,"Bill not exists");
	}

	@Override
	public Iterable<BillEntity> getByUserId(Integer buyerId) throws RESTError{
		if (userRepository.findById(buyerId).isEmpty()) {
			throw new RESTError(1,"Buyer not exists");
		} else
			return billRepository.findByUserId(buyerId);
	}

	@Override
	public List<BillEntity> getByCategoryId(Integer categoryId) throws RESTError{
		List<OfferEntity> offers = offerRepository.findByCategoryId(categoryId);
		List<BillEntity> bills = new ArrayList<BillEntity>();
		for (BillEntity b : billRepository.findAll()) {
			if(offers.contains(b.getOffer())) {
				bills.add(b);
			}
		}
		if(bills.isEmpty()) {
			throw new RESTError(1,"Doesnt exist bill in this category!");
		}
		return bills;
	}

	@Override
	public List<BillEntity> findByDate(Date startDate, Date endDate){
		List<BillEntity> billBetween =new ArrayList<BillEntity>();
		
		for (BillEntity billE : billRepository.findAll()) {
			if (billE.getBillCreated().after(startDate) && billE.getBillCreated().before(endDate)) {
				billBetween.add(billE);
			}
		}
		return billBetween;
	}

	@Override
	public boolean billForCategory(Integer categoryId) throws RESTError {
		for(BillEntity bill: billRepository.findAll()) {
			if(bill.getOffer().getCategory() != null) {
				if(bill.getOffer().getCategory().getId()==categoryId || bill.getOffer().getOffer_status()==OfferStatus.APPROVED) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean billForUser(Integer userId) {
		for(BillEntity bill : billRepository.findAll()) {
			if(bill.getUser() != null) {
				if(bill.getUser().getId()==userId) {
					return true;
				}
			}
		}
		return false;
	}
	
}
