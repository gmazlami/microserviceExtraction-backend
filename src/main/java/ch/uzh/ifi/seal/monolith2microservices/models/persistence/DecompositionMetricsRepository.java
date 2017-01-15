package ch.uzh.ifi.seal.monolith2microservices.models.persistence;

import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.EvaluationMetrics;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Genc on 15.01.2017.
 */
public interface DecompositionMetricsRepository extends CrudRepository<EvaluationMetrics,Long> {

}
