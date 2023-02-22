package com.iktpreobuka.Project_example.Service;

import java.text.ParseException;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.iktpreobuka.Project_example.controllers.util.RESTError;
import com.iktpreobuka.Project_example.entites.OfferEntity;
import com.iktpreobuka.Project_example.entites.OfferStatus;

public interface OfferService {
	
	public OfferEntity add(OfferEntity offer);
	
	public OfferEntity modify(Integer id,OfferEntity of) throws RESTError;
	
	public OfferEntity delete(Integer id) throws RESTError;
	
	public Optional<OfferEntity> id( Integer id) throws RESTError;
	
	public OfferEntity status(Integer id,OfferStatus status) throws RESTError;
	
	public OfferEntity between(Double lowerPrice,Double upperPrice) throws RESTError;
	
	public OfferEntity addCategoryAndSeller(Integer categoryId,Integer sellerId,OfferEntity offer) throws RESTError;

	public OfferEntity modifyKateggory(Integer id,Integer categoryId) throws RESTError;
	
	public void availableAndBoughtOffers(Integer offerId);
	
	public boolean offerForCategory(Integer categoryId) throws RESTError;
	
	boolean offerForUser(Integer userId);
}
