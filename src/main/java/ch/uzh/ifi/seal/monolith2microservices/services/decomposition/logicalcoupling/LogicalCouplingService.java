package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.main.utils.Hash;
import ch.uzh.ifi.seal.monolith2microservices.models.LogicalCoupling;

@Service
public class LogicalCouplingService {
	
	private final String ESCAPED_SUBSET_DELIMITER = "\\?";
	private final String SUBSET_DELIMITER = "\\?";
	
	private Map<String, LogicalCoupling> resultMap;
	
	
	public List<LogicalCoupling> computeLogicalCouplings(List<List<DiffEntry>> history){
		resultMap = new HashMap<>();
		for(List<DiffEntry> diff: history){
			PowerSet powerSet = new PowerSet(getStringSet(diff));
			List<String> powerSetOfFiles = powerSet.compute();  
			
			for(String element: powerSetOfFiles){
				String[] files = element.split(ESCAPED_SUBSET_DELIMITER);
				
				//only consider subsets in the power set that have more than one file/element for logical coupling analysis
				if (files.length > 1){
					List<String> fileList = Arrays.asList(files);

					LogicalCoupling coupling = generateLogicalCoupling(fileList);
					resultMap.put(coupling.getHash(), coupling);
				}
			}
		}
		
		return new ArrayList<>(resultMap.values());
	}
	
	
	private LogicalCoupling generateLogicalCoupling(List<String> fileList){
		
		Collections.sort(fileList);
		String key = String.join(SUBSET_DELIMITER, fileList);
		String hash = new Hash(key).get();
		
		LogicalCoupling existingCoupling = resultMap.get(hash);
		
		//check if this coupling was already discovered
		if(existingCoupling != null){
			//if so, we only have to increment the score
			existingCoupling.incrementScore();
			return existingCoupling;
		}else{
			// if the coupling is a new one (new file combination that changed together), we have to create it
			LogicalCoupling newCoupling = new LogicalCoupling();
			newCoupling.setHash(hash);
			newCoupling.setScore(1);
			
			for(String fileName : fileList){
				newCoupling.addClass(fileName);
			}
			return newCoupling;
		}
	}
	
	
	/*
	 * Converts a list of DiffEntry instances to a list of the path strings
	 * of the file changes in the DiffEntry instance.
	 */
	private List<String> getStringSet(List<DiffEntry> diffList){
		List<String> set = new ArrayList<>();
		for(DiffEntry entry: diffList){
			set.add(entry.getNewPath());
		}
		return set;
	}
	
	
	
	/*
	 * Class that computes the power set of a given Set of strings.
	 */
	private class PowerSet{
		
		private List<String> originalSet;
		
		public PowerSet(List<String> set){
			this.originalSet = set;
		}
		
		public List<String> compute(){
			return powerset(this.originalSet);
		}
		
		private List<String> powerset(List<String> set){
			List<String> powerset = new ArrayList<>();

			for(String binary: getList(set.size())){
				String element = "";
				List<Integer> indexes = getIndexes(binary);
				
				if(indexes.size() == 0){
					continue;
				}
				if(indexes.size() == 1){
					element = set.get(indexes.get(0));
				}else{
					for(int index: getIndexes(binary)){
						element += set.get(index) + ESCAPED_SUBSET_DELIMITER; 
					}
				}
				
				powerset.add(element);
			}
			return powerset;
			
		}
		
		private List<Integer> getIndexes(String binary){
			char one = '1';
			List<Integer> list = new ArrayList<>();
			
			int index = binary.indexOf(one);
			
			while(index >= 0) {
			   list.add(index);
			   index = binary.indexOf(one, index+1);
			}
			return list;
		}
		
		private List<String> getList(int size){
			List<String> list = new ArrayList<>();
			int possibilities = (int) Math.pow(2, size);
			String formatString = "%" + size + "s";
			
			for(int i = 0; i < possibilities; i++){
				list.add(String.format(formatString, Integer.toBinaryString(i)).replace(' ', '0'));
			}
			
			return list;
		}

	
	}
	
}
