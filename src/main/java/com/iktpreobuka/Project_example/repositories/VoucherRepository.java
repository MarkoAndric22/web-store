package com.iktpreobuka.Project_example.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.Project_example.entites.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer>{
	 Iterable<VoucherEntity> findByUserId(Integer id);

	 VoucherEntity findByOffer(Integer id);
	 
}
