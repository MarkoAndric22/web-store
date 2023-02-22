package com.iktpreobuka.Project_example.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.Project_example.entites.OfferEntity;

public interface OfferRepository extends CrudRepository<OfferEntity, Integer> {

	List<OfferEntity> findByCategoryId(Integer categoryId);
}
