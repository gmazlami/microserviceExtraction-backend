package ch.uzh.ifi.seal.monolith2microservices.persistence;

import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.MicroserviceMetrics;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Genc on 15.01.2017.
 */
public interface MicroserviceMetricsRepository extends CrudRepository<MicroserviceMetrics,Long> {

}
