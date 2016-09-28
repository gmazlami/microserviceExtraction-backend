package services.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.beans.factory.annotation.Autowired;

import main.Hashing;
import models.LogicalCoupling;
import models.GitRepository;
import models.persistence.ClassRepository;
import models.persistence.RepositoryRepository;

public class LogicalCouplingService {
	
	private List<List<DiffEntry>> history = null;
	
	@Autowired
	private RepositoryRepository repoRepository;
	
	@Autowired
	private ClassRepository classRepository;
	
	public LogicalCouplingService(List<List<DiffEntry>> history, GitRepository repo){
		this.history = history;
	}
	
	public List<LogicalCoupling> computeLogicalCouplings(){
		
		for(List<DiffEntry> diff: history){
			List<String> diffSet = getStringSet(diff);
			List<String> powerSet = powerSet(diffSet);
			
		}
		
		return null;
	}
	
	private void processCommitCouplings(List<String> powerSet){
		Map<String, LogicalCoupling> resultMap = new HashMap<>();
		for(String element: powerSet){
			String[] elements = element.split("?");
			if (elements.length > 1){
				List<String> elementList = Arrays.asList(elements);
				Collections.sort(elementList);
				String key = String.join("?", elementList);
				String hash = Hashing.hash(key);
//				LogicalCoupling coupling =  LogicalCoupling.getInstance(hash, classList);
				
				//TODO: import RepositoryReposiory or ClassRepository to get classes from db for a given class name and repo
				//TODO: Create LogicalCoupling, add classes to it, add hash, increment score/set to 1
				//TODO: Add LogicalCoupling to resultMap with hash as key
				
			}
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
