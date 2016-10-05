package services.decomposition.logicalcoupling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.stereotype.Service;

import main.utils.Hashing;
import models.GitRepository;
import models.LogicalCoupling;

@Service
public class LogicalCouplingService {
	
	private final String ESCAPED_SUBSET_DELIMITER = "\\?";
	private final String SUBSET_DELIMITER = "\\?";
	
	private Map<String, LogicalCoupling> resultMap;
	
	
	public List<LogicalCoupling> computeLogicalCouplings(List<List<DiffEntry>> history, GitRepository repo){
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
		String hash = Hashing.hash(key);
		
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
			return powerSet(this.originalSet);
		}
		
		/*
		 * Uses the following recursive algorithm:
		 * https://en.wikipedia.org/wiki/Power_set#Algorithms
		 * 
		 * Returns the power set of the set of strings given as an input list.
		 * Elements in the resulting powerset that are themselves sets of multiple elements
		 * are expressed through string concatenation using the symbol "?", since it is not possible 
		 * to nest Sets with the chosen data structures.
		 * 
		 * Example: Input set: {a,b,c} --> Theoretical Power set: {a,b,c,{a,b},{a,c},{b,c},{a,b,c}} --> 
		 * --> String concatenated representation of it: {a, b, c, a?b, a?c, b?c, a?b?c}
		 */
		private List<String> powerSet(List<String> set){
			if(set.size() == 1){
				return new ArrayList<String>();
			}
			if(set.size() == 2){
				List<String> temp = new ArrayList<>(set);
				temp.add(merge(set.get(0),set.get(1)));
				return temp;
			}
			
			String element = set.remove(0);
			
			return union(powerSet(set), permute(element, powerSet(set)));
		}
		
		private  List<String> permute(String element, List<String> set){
			List<String> permutatedSet = new ArrayList<>();
			permutatedSet.add(element);
			
			for(String existingElement: set){
				permutatedSet.add(merge(element, existingElement));
			}
			
			return permutatedSet;
		}
		
		private List<String> union(List<String> set1, List<String> set2){
			set1.addAll(set2);
			return set1;
		}
		
		private String merge(String str1, String str2){
			return str1 + "?" + str2;
		}
	
	}
	
}
