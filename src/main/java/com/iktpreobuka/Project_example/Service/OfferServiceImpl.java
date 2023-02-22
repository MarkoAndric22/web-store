package com.iktpreobuka.Project_example.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.Project_example.controllers.OfferController;
import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.CategoryEntity;
import com.iktpreobuka.Project_example.entites.OfferEntity;
import com.iktpreobuka.Project_example.entites.OfferStatus;
import com.iktpreobuka.Project_example.entites.UserEntity;
import com.iktpreobuka.Project_example.entites.UserRole;
import com.iktpreobuka.Project_example.repositories.CategoryRepository;
import com.iktpreobuka.Project_example.repositories.OfferRepository;
import com.iktpreobuka.Project_example.repositories.UserRepository;

@Service
public class OfferServiceImpl implements OfferService {
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public OfferEntity add(OfferEntity offer){
		return offerRepository.save(offer);
	}

	@Override
	public OfferEntity modify(Integer id, OfferEntity of) throws RESTError {
		for (OfferEntity offerEn : offerRepository.findAll()) {
			if (offerEn.getId().equals(id)) {
				offerEn.setOffer_name(of.getOffer_name());
				offerEn.setOffer_description(of.getOffer_description());
				offerEn.setOffer_created(of.getOffer_created());
				offerEn.setOffer_expires(of.getOffer_expires());
				offerEn.setRegular_price(of.getRegular_price());
				offerEn.setAction_price(of.getAction_price());
				offerEn.setImage_path(of.getImage_path());
				offerEn.setAvailable_offer(of.getAvailable_offer());
				offerEn.setBought_offers(of.getBought_offers());
				return offerRepository.save(offerEn);
			}
		}
		
		throw new RESTError(1,"Offer not exists");
	}

	@Override
	public OfferEntity delete(Integer id) throws RESTError {
		for (OfferEntity off : offerRepository.findAll()) {
			if (off.getId().equals(id)) {
				offerRepository.deleteById(id);
				return off;
			}
		}throw new RESTError(1,"Offer not exists");
	}

	@Override
	public Optional<OfferEntity> id(Integer id) throws RESTError {
		for (OfferEntity of : offerRepository.findAll()) {
			if (of.getId().equals(id))
				return offerRepository.findById(id);
		}
		throw new RESTError(1,"Offer not exists");
	}

	@Override
	public OfferEntity status(Integer id, OfferStatus status) throws RESTError {
		for (OfferEntity of :offerRepository.findAll()) {
			if (of.getId().equals(id)) {
				of.setOffer_status(status);
				return offerRepository.save(of);
			}
		}
		throw new RESTError(1,"Offer not exists");
	}

	@Override
	public OfferEntity between(Double lowerPrice, Double upperPrice) throws RESTError {
		for (OfferEntity of : offerRepository.findAll()) {
			if (of.getAction_price() > lowerPrice && of.getAction_price() < upperPrice) {
				return offerRepository.save(of);
			}
		}
		throw new RESTError(1,"Offer not beetwen");
		
	}

	@Override
	public OfferEntity addCategoryAndSeller(Integer categoryId, Integer sellerId, OfferEntity offer)
			throws RESTError {
		
		if(userRepository.findById(sellerId).isEmpty()) {
			throw new RESTError(1,"user does not exist");
		}
		else if(categoryRepository.findById(categoryId).isEmpty()) {
			throw new RESTError(2,"category does not exist");
		}
		
		if (offerRepository.existsById(offer.getId())) {
			throw new RESTError(3,"offer is exists");
		}
		
		UserEntity user = userRepository.findById(sellerId).get();
		CategoryEntity category = categoryRepository.findById(categoryId).get();
		
		if(!user.getUser_role().equals(UserRole.ROLE_SELLER)) {
			throw new RESTError(4,"user is not roll seller");
		}
		offer.setOffer_created(new Date());
		Calendar c = Calendar.getInstance(); 
		c.setTime(offer.getOffer_created()); 
		c.add(Calendar.DATE, 10);
		offer.setOffer_expires(c.getTime());
		offer.setUser(user);
		offer.setCategory(category);
		
		return offerRepository.save(offer);
	}

	@Override
	public OfferEntity modifyKateggory(Integer id, Integer categoryId) throws RESTError {
		if(!offerRepository.existsById(id)) {
			throw new RESTError(1,"Offer not exists");
		}
		CategoryEntity category = categoryRepository.findById(categoryId).get();
		OfferEntity offer= offerRepository.findById(id).get();

		offer.setCategory(category);
		return offerRepository.save(offer);
		
	}

	@Override
	public void availableAndBoughtOffers(Integer offerId) {
		OfferEntity offer = offerRepository.findById(offerId).get();

		if (offer.getOffer_status()!=OfferStatus.DECLINED) {
			offer.setBought_offers(offer.getBought_offers() + 1);
			offer.setAvailable_offer(offer.getAvailable_offer() - 1);
		}
		else {
			offer.setBought_offers(offer.getBought_offers() - 1);
			offer.setAvailable_offer(offer.getAvailable_offer() + 1);
		}
	}

	@Override
	public boolean offerForCategory(Integer categoryId) {
		for(OfferEntity offer:offerRepository.findAll()) {
			if(offer.getCategory() != null) {
				if(offer.getCategory().getId()==categoryId) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean offerForUser(Integer userId) {
		for(OfferEntity offer:offerRepository.findAll()) {
			if(offer.getUser() != null) {
				if(offer.getUser().getId()==userId) {
					return true;
				}
			}
		}
		return false;
	}
	

}
