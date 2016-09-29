package models.persistence;

import org.springframework.data.repository.CrudRepository;

import models.LogicalCoupling;

public interface LogicalCouplingRepository extends CrudRepository<LogicalCoupling, Long> {

	LogicalCoupling findById(Long id);
	
	LogicalCoupling findByHash(String hash);
}
