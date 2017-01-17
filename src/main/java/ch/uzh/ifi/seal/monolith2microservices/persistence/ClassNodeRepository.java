package ch.uzh.ifi.seal.monolith2microservices.persistence;

import ch.uzh.ifi.seal.monolith2microservices.models.graph.ClassNode;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by gmazlami on 1/12/17.
 */
public interface ClassNodeRepository extends CrudRepository<ClassNode,Long> {


}
