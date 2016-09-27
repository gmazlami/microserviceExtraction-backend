package analysis;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.DiffEntry;

import models.LogicalCoupling;

public class LogicalCouplingEngine {
	
	private List<List<DiffEntry>> history;
	
	public LogicalCouplingEngine(List<List<DiffEntry>> history){
		this.history = history;
	}
	
	
	public List<LogicalCoupling> computeLogicalCouplings(){
		
		for(List<DiffEntry> diff: history){
			List<String> diffSet = getStringSet(diff);
			List<String> powerSet = powerSet(diffSet);
			
			System.out.println(powerSet);
		}
		
		return null;
	}
	
	private List<String> getStringSet(List<DiffEntry> diffList){
		List<String> set = new ArrayList<>();
		for(DiffEntry entry: diffList){
			set.add(entry.getNewPath());
		}
		return set;
	}
	
	private List<String> powerSet(List<String> set){
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
