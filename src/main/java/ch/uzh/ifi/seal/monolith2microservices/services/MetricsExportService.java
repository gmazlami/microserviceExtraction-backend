package ch.uzh.ifi.seal.monolith2microservices.services;

import ch.uzh.ifi.seal.monolith2microservices.git.GitClient;
import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.evaluation.EvaluationMetrics;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.models.graph.Decomposition;
import ch.uzh.ifi.seal.monolith2microservices.persistence.DecompositionMetricsRepository;
import ch.uzh.ifi.seal.monolith2microservices.persistence.DecompositionRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gmazlami on 1/26/17.
 */
@Service
public class MetricsExportService {

    private final String newLine = "\n";

    @Autowired
    private DecompositionRepository decompositionRepository;

    @Autowired
    private DecompositionMetricsRepository decompositionMetricsRepository;

    @Autowired
    private Configs configs;


    public String exportQualityMetrics() throws Exception {
        StringBuilder sb = new StringBuilder();

        List<EvaluationMetrics> metrics = decompositionMetricsRepository.findAll();

        for(EvaluationMetrics metric: metrics){
            Decomposition decomposition = decompositionRepository.findById(metric.getDecomposition().getId());
            sb.append(createQualityMetricsRow(metric,decomposition,','));
            sb.append(newLine);
        }

        return sb.toString();
    }

    public String exportLogicalCouplingPerformanceMetrics() throws Exception {
        List<Decomposition> decompositions = decompositionRepository.findAll().stream().filter(decomposition -> {
                    return (decomposition.getParameters().isLogicalCoupling() &&
                    !decomposition.getParameters().isContributorCoupling() &&
                    !decomposition.getParameters().isSemanticCoupling());}).collect(Collectors.toList());

        return createPerformanceTable(decompositions);
    }

    public String exportSemanticCouplingPerformanceMetrics() throws Exception {
        StringBuilder sb = new StringBuilder();

        List<Decomposition> decompositions = decompositionRepository.findAll().stream().filter(decomposition -> {
            return (!decomposition.getParameters().isLogicalCoupling() &&
                    !decomposition.getParameters().isContributorCoupling() &&
                    decomposition.getParameters().isSemanticCoupling());}).collect(Collectors.toList());

        for(Decomposition decomposition: decompositions){
            EvaluationMetrics metrics = decompositionMetricsRepository.findByDecompositionId(decomposition.getId());
            String row = createSemanticCouplingPerformanceRow(decomposition, metrics, ',');

            sb.append(row);
            sb.append(newLine);
        }
        return sb.toString();
    }

    public String exportContributorCouplingPerformanceMetrics() throws Exception {
        List<Decomposition> decompositions = decompositionRepository.findAll().stream().filter(decomposition -> {
            return (!decomposition.getParameters().isLogicalCoupling() &&
                    decomposition.getParameters().isContributorCoupling() &&
                    !decomposition.getParameters().isSemanticCoupling());}).collect(Collectors.toList());

        return createPerformanceTable(decompositions);
    }

    private String createQualityMetricsRow(EvaluationMetrics metrics, Decomposition decomposition, char separator){
        StringBuilder sb = new StringBuilder();
        sb.append(decomposition.getRepository().getName());
        sb.append(separator);
        sb.append(decomposition.getParameters().isLogicalCoupling() ? 'x':'o');
        sb.append(separator);
        sb.append(decomposition.getParameters().isContributorCoupling() ? 'x':'o');
        sb.append(separator);
        sb.append(decomposition.getParameters().isSemanticCoupling() ? 'x':'o');
        sb.append(separator);
        sb.append(metrics.getAverageClassNumber());
        sb.append(separator);
        sb.append(metrics.getAverageLoc());
        sb.append(separator);
        sb.append(metrics.getContributorOverlapping());
        sb.append(separator);
        sb.append(metrics.getContributorsPerMicroservice());
        sb.append(separator);
        sb.append(metrics.getSimilarity());
        return sb.toString();
    }

    private String createPerformanceTable(List<Decomposition> decompositions) throws Exception{
        StringBuilder sb = new StringBuilder();
        for(Decomposition decomposition : decompositions){
            EvaluationMetrics metrics = decompositionMetricsRepository.findByDecompositionId(decomposition.getId());
            int historyLength = computeHistoryLengthInDays(decomposition.getRepository());
            int commitCount = computeCommitCount(decomposition.getRepository());

            String row = createLogicalCouplingPerformanceRow(decomposition, metrics, historyLength, commitCount, ',');

            sb.append(row);
            sb.append(newLine);
        }
        return sb.toString();
    }


    private String createSemanticCouplingPerformanceRow(Decomposition decomposition, EvaluationMetrics metrics, char separator){
        StringBuilder sb = new StringBuilder();
        sb.append(decomposition.getRepository().getName());
        sb.append(separator);
        sb.append(metrics.getExecutionTimeMillisStrategy());
        return sb.toString();
    }

    private String createLogicalCouplingPerformanceRow(Decomposition decomposition, EvaluationMetrics metrics, int historyLength, int commitCount, char separator){
        StringBuilder sb = new StringBuilder();
        sb.append(decomposition.getRepository().getName());
        sb.append(separator);
        sb.append(commitCount);
        sb.append(separator);
        sb.append(historyLength);
        sb.append(separator);
        sb.append(metrics.getExecutionTimeMillisStrategy());
        return sb.toString();
    }

    private int computeHistoryLengthInDays(GitRepository repo) throws Exception{
        GitClient gitClient = new GitClient(repo, configs);
        List<RevCommit> log = gitClient.getCommitLog();
        int startTime = log.get(log.size()-1).getCommitTime();
        int endTime = log.get(0).getCommitTime();
        int historyLengthSeconds = endTime - startTime;
        return historyLengthSeconds / (60*60*24);
    }

    private int computeCommitCount(GitRepository repo) throws Exception{
        GitClient gitClient = new GitClient(repo, configs);
        return gitClient.getCommitLog().size();
    }


}
