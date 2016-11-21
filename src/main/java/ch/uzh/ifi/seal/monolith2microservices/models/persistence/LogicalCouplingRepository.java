package ch.uzh.ifi.seal.monolith2microservices.models.persistence;

import org.springframework.data.repository.CrudRepository;

import ch.uzh.ifi.seal.monolith2microservices.models.couplings.LogicalCoupling;

public interface LogicalCouplingRepository extends CrudRepository<LogicalCoupling, Long> {

	LogicalCoupling findById(Long id);
	
	LogicalCoupling findByHash(String hash);
}
