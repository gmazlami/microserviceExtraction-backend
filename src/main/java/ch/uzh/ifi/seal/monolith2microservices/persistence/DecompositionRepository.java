package ch.uzh.ifi.seal.monolith2microservices.persistence;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by gmazlami on 1/12/17.
 */
public interface DecompositionRepository extends CrudRepository<Decomposition, Long> {

    Decomposition findById(long id);

}
