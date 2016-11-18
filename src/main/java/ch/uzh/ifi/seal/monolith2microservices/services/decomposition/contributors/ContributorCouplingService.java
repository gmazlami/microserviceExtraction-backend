package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ch.uzh.ifi.seal.monolith2microservices.models.Class;
import org.eclipse.jgit.diff.DiffEntry;

import ch.uzh.ifi.seal.monolith2microservices.models.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.logicalcoupling.graph.ClassNode;

public class ContributorCouplingService {
	
	public List<ClassNode> computeContributorGraph(List<ChangeEvent> changeHistory){
		Map<String, List<String>> fileAuthorMap = getFileAuthorMap(changeHistory);
		return computeContributorGraph(changeHistory);
	}
	
	
	private List<ClassNode> computeGraph(Map<String, List<String>> fileAuthorMap){
		List<String> fileNames = fileAuthorMap.keySet().stream().collect(Collectors.toList());

		Map<String,ClassNode> nodeMap = new HashMap<>();

		for(int i = 0; i < fileNames.size(); i++){
			String currentFileName = fileNames.get(i);
			
			List<String> currentFileAuthors = fileAuthorMap.get(currentFileName);
			
			for(String secondFileName : fileNames.subList(i+1, fileNames.size())){
				int similarity = computeAuthorSimilarity(currentFileAuthors, fileAuthorMap.get(secondFileName));

				ClassNode firstNode, secondNode;

				if((firstNode = nodeMap.get(currentFileName)) == null){
					//create node for first file in pair
					firstNode = new ClassNode(currentFileName);

				}
				if ((secondNode = nodeMap.get(secondFileName)) == null){
					//create node for the second file in the pair
					secondNode = new ClassNode(secondFileName);
				}

				//link both nodes together as new neighbors with their similarity
				firstNode.addNeighborWithWeight(secondNode,similarity);
				secondNode.addNeighborWithWeight(firstNode,similarity);

				nodeMap.put(currentFileName, firstNode);
				nodeMap.put(secondFileName, secondNode);
			}

		}
		return nodeMap.values().stream().collect(Collectors.toList());
	}
	
	private int computeAuthorSimilarity(List<String> firstFileAuthors, List<String> secondFileAuthors){
		Map<String,?> map = new HashMap<>();
		int numberOfSharedAuthors = 0;
		
		for(String author: firstFileAuthors){
			map.put(author, null);
		}

		for(String otherAuthor : secondFileAuthors){
			if(map.containsKey(otherAuthor)){
				numberOfSharedAuthors++;
			}
		}
		
		return numberOfSharedAuthors;
	}
	
	private Map<String, List<String>> getFileAuthorMap(List<ChangeEvent> changeHistory){
		Map<String,List<String>> fileAuthorMap = new HashMap<>();
		
		for(ChangeEvent event : changeHistory){
			for(DiffEntry diffEntry: event.getChangedfiles()){
				String fileName = diffEntry.getNewPath();
				
				if(fileAuthorMap.get(fileName) == null){
					List<String> list = new ArrayList<>();
					list.add(event.getAuthorEmailAddress());
					fileAuthorMap.put(fileName, list);
					
				}else{
					List<String> list = fileAuthorMap.get(fileName);
					list.add(event.getAuthorEmailAddress());
					fileAuthorMap.put(fileName, list);
				}
			}
		}
		
		return fileAuthorMap;
	}

}
