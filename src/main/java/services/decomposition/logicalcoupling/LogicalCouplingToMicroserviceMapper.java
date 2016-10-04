package services.decomposition.logicalcoupling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import main.utils.Comparators;
import models.LogicalCoupling;
import models.Microservice;

@Service
public class LogicalCouplingToMicroserviceMapper {

 
	// holds @link{Class} filePath strings as keys
	// and points to @link{Microservice} as value
	// helps constructing the microservice graph
	private Map<String, Microservice> mapping;
	
	public List<Microservice> mapToMicroservices(List<LogicalCoupling> couplings){
		mapping = new HashMap<>();
		Map<String, Microservice> microservices = new HashMap<>(); 
		
		//Sort ASC
		Collections.sort(couplings, Comparators.LOGICAL_COUPLING_SCORE);
		
		// reverse so they are in DESC order
		Collections.reverse(couplings);
		
		//compute lower quartile threshold q1, so we can filter out @link{LogicalCoupling} instances which have to small score
		int q1 = computeLowerQuartile(couplings);
		
		for(LogicalCoupling coupling : couplings){
			//only take @link{LogicalCoupling}s with high enough score into consideration 
			if(coupling.getScore() > q1){
				
				Microservice microservice = new Microservice();
				microservice.setClasses(new ArrayList<>(coupling.getClasses()));
				microservice.setHash(coupling.getHash());
				
				Microservice existing;
				
				for(String cls : coupling.getClasses()){
					if((existing = mapping.get(cls)) != null){ //class was already encountered in another microservice (named existing) with a higher score
						microservice.removeClass(cls);
						microservice.addRelation(existing);
					}
					else{
						mapping.put(cls, microservice);
						microservices.put(microservice.getHash(), microservice);
					}
				}
			}
		}
		return new ArrayList<Microservice>(microservices.values());
	}
	
	private int computeLowerQuartile(List<LogicalCoupling> couplings){
		Collections.sort(couplings, Comparators.LOGICAL_COUPLING_SCORE);
		int q1Index = (int) Math.round(couplings.size() * 0.25f);
		return couplings.get(q1Index).getScore();
	}
	
}
