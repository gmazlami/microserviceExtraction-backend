package ch.uzh.ifi.seal.monolith2microservices.controllers;

import ch.uzh.ifi.seal.monolith2microservices.services.MetricsExportService;
import ch.uzh.ifi.seal.monolith2microservices.services.reporting.TextFileReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gmazlami on 1/26/17.
 */
@Configuration
@EnableAutoConfiguration
@RestController
@Component
public class ExportController {

    @Autowired
    private MetricsExportService metricsExportService;

    @CrossOrigin
    @RequestMapping(value="/export/performance", method= RequestMethod.GET)
    public ResponseEntity<String> exportPerformanceMetrics() throws Exception{
        TextFileReport.writeToFile(metricsExportService.exportLogicalCouplingPerformanceMetrics(), "logicalCouplingPerformance.txt");
        TextFileReport.writeToFile(metricsExportService.exportSemanticCouplingPerformanceMetrics(), "semanticCouplingPerformance.txt");
        TextFileReport.writeToFile(metricsExportService.exportContributorCouplingPerformanceMetrics(), "contributorCouplingPerformance.txt");
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value="/export/quality", method= RequestMethod.GET)
    public ResponseEntity<String> exportQualityMetrics() throws Exception{
        TextFileReport.writeToFile(metricsExportService.exportQualityMetrics(), "qualityMetrics.txt");
        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}
