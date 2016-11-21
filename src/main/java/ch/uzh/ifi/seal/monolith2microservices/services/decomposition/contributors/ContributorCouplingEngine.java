package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.contributors;

import ch.uzh.ifi.seal.monolith2microservices.models.git.ChangeEvent;
import ch.uzh.ifi.seal.monolith2microservices.models.couplings.ContributorCoupling;
import org.eclipse.jgit.diff.DiffEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContributorCouplingEngine {
	
	public List<ContributorCoupling> computeContributorGraph(List<ChangeEvent> changeHistory){
		Map<String, List<String>> fileAuthorMap = getFileAuthorMap(changeHistory);
		return computeGraph(fileAuthorMap);
	}


	private List<ContributorCoupling> computeGraph(Map<String, List<String>> fileAuthorMap){
		List<String> fileNames = fileAuthorMap.keySet().stream().collect(Collectors.toList());

        List<ContributorCoupling> couplings = new ArrayList<>();

		for(int i = 0; i < fileNames.size(); i++){
			String currentFileName = fileNames.get(i);

			List<String> currentFileAuthors = fileAuthorMap.get(currentFileName);

			for(String secondFileName : fileNames.subList(i+1, fileNames.size())){
				List<String> secondFileAuthors = fileAuthorMap.get(secondFileName);
				int similarity = computeAuthorSimilarity(currentFileAuthors, secondFileAuthors);

				ContributorCoupling coupling = new ContributorCoupling();
				coupling.setFirstFileName(currentFileName);
				coupling.setFirstFileAuthors(currentFileAuthors);
				coupling.setSecondFileName(secondFileName);
				coupling.setSecondFileAuthors(secondFileAuthors);
				coupling.setScore(similarity);

                couplings.add(coupling);
			}

		}
		return couplings;
	}

//	private List<ClassNode> computeGraph(Map<String, List<String>> fileAuthorMap){
//		List<String> fileNames = fileAuthorMap.keySet().stream().collect(Collectors.toList());
//
//		Map<String,ClassNode> nodeMap = new HashMap<>();
//
//		for(int i = 0; i < fileNames.size(); i++){
//			String currentFileName = fileNames.get(i);
//
//			List<String> currentFileAuthors = fileAuthorMap.get(currentFileName);
//
//			for(String secondFileName : fileNames.subList(i+1, fileNames.size())){
//				int similarity = computeAuthorSimilarity(currentFileAuthors, fileAuthorMap.get(secondFileName));
//
//				ClassNode firstNode, secondNode;
//
//				if((firstNode = nodeMap.get(currentFileName)) == null){
//					//create node for first file in pair
//					firstNode = new ClassNode(currentFileName);
//
//				}
//				if ((secondNode = nodeMap.get(secondFileName)) == null){
//					//create node for the second file in the pair
//					secondNode = new ClassNode(secondFileName);
//				}
//
//				//link both nodes together as new neighbors with their similarity
//				firstNode.addNeighborWithWeight(secondNode,similarity);
//				secondNode.addNeighborWithWeight(firstNode,similarity);
//
//				nodeMap.put(currentFileName, firstNode);
//				nodeMap.put(secondFileName, secondNode);
//			}
//
//		}
//		return nodeMap.values().stream().collect(Collectors.toList());
//	}
	
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
