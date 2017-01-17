package ch.uzh.ifi.seal.monolith2microservices.persistence;

import ch.uzh.ifi.seal.monolith2microservices.models.DecompositionParameters;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by gmazlami on 1/17/17.
 */
public interface ParametersRepository extends CrudRepository<DecompositionParameters, Long> {

}
