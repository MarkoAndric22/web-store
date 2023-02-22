package com.iktpreobuka.Project_example.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.Project_example.entites.BillEntity;
@Repository

public interface BillRepository extends CrudRepository<BillEntity, Integer> {

	 Iterable<BillEntity> findByUserId (Integer id);
	 
	 List<BillEntity> findByBillCreated(Date billCreated);
}
