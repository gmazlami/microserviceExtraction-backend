package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.timeseries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.jgit.diff.DiffEntry;
import org.springframework.stereotype.Service;

import ch.uzh.ifi.seal.monolith2microservices.main.utils.Hash;
import ch.uzh.ifi.seal.monolith2microservices.models.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.LogicalCoupling;

@Service
public class LogicalCouplingEngine {
	
	private final String ESCAPED_SUBSET_DELIMITER = "\\?";
	
	private final String SUBSET_DELIMITER = "\\?";
	
	private Map<String, LogicalCoupling> resultMap;

	private int currentChangeEventCounter;

	public List<LogicalCoupling> computeCouplings(List<ChangeEvent> changeHistory, int intervalInSeconds){
		resultMap = new HashMap<>();
		
		//compute first timestamp in history
		int t_start = extractEarliestTimestamp(changeHistory);
		int t_end = extractLatestTimestamp(changeHistory);
		Collections.reverse(changeHistory);
		
		for(int t_current = t_start; t_current < t_end; t_current+= intervalInSeconds){
			//Extract changeEvents that happened in this current time interval [t_current, t_current + intervalInSeconds]
			List<ChangeEvent> changeEvents = extractChangeEvents(changeHistory, t_current, t_current + intervalInSeconds);
			
			if(changeEvents.isEmpty()){
				continue;
			}

			//Prepare and compute powerset of changed filenames in all the changeEvents in the current time interval
			List<DiffEntry> currentDiffEntries = new ArrayList<>();
			for(ChangeEvent changeEvent: changeEvents){
				changeEvent.getChangedfiles().forEach(diffEntry -> currentDiffEntries.add(diffEntry));
			}
			PowerSet powerSet =  new PowerSet(getStringSet(currentDiffEntries));
			List<String> powerSetOfFileNames = powerSet.compute();
			
			// for each pair of coupled files (A,B) in the powerset, create a logical coupling or increase score if coupling was already discovered 
			for(String element: powerSetOfFileNames){
				String[] files = element.split(ESCAPED_SUBSET_DELIMITER);
				
				//only consider subsets in the power set that have pair of 2 classes coupled together
				if (files.length == 2 ){
					List<String> fileList = Arrays.asList(files);
					LogicalCoupling coupling = generateLogicalCoupling(fileList, t_current, t_current + intervalInSeconds);
					resultMap.put(coupling.getHash(), coupling);
				}
			}
		}
		
		List<LogicalCoupling> couplings = resultMap.values().stream().collect(Collectors.toList());
		currentChangeEventCounter = 0;
		resultMap = null;
		
		return couplings;
	}
	
	private int extractEarliestTimestamp(List<ChangeEvent> changeHistory){
		return changeHistory.get(changeHistory.size() - 1).getTimestampInSeconds();
	}
	
	private int extractLatestTimestamp(List<ChangeEvent> changeHistory){
		return changeHistory.get(0).getTimestampInSeconds();
	}
	
	private List<ChangeEvent> extractChangeEvents(List<ChangeEvent> changeHistory, int t_start, int t_end){
		List<ChangeEvent> results = new ArrayList<>();
		int time = t_start;

		while(currentChangeEventCounter < changeHistory.size() && time < t_end){
			ChangeEvent currentEvent = changeHistory.get(currentChangeEventCounter);
			
			if((t_start <= currentEvent.getTimestampInSeconds()) && (currentEvent.getTimestampInSeconds() < t_end)){
				results.add(currentEvent);
				currentChangeEventCounter++;
			}else{
				time = currentEvent.getTimestampInSeconds();
			}
			
		}
		return results;
	}
	
	
	private LogicalCoupling generateLogicalCoupling(List<String> fileList, int t_start, int t_end){
		
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
			newCoupling.setStartTimestamp(t_start);
			newCoupling.setEndTimestamp(t_end);
			
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
