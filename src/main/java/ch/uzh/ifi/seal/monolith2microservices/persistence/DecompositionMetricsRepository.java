package ch.uzh.ifi.seal.monolith2microservices.persistence;

import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.EvaluationMetrics;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by Genc on 15.01.2017.
 */
public interface DecompositionMetricsRepository extends CrudRepository<EvaluationMetrics,Long> {

    EvaluationMetrics findByDecompositionId(long decompositionId);

    List<EvaluationMetrics> findAll();

}
