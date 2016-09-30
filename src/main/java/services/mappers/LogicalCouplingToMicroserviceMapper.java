package services.mappers;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import models.LogicalCoupling;
import models.Microservice;

@Service
public class LogicalCouplingToMicroserviceMapper {

	public List<Microservice> mapToMicroservices(List<LogicalCoupling> couplings){
//		Collections.sort(couplings);
		for(LogicalCoupling coupling : couplings){
			//TODO: add mapping logic
		}
		return null;
	}
	
}
