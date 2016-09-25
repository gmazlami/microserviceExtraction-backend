package services;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import main.Configs;
import models.Repository;

@Service
public class AnalysisService {
	
	@Autowired
	Configs config;
	
	
	@Async
	public void processRepository(Repository repo) throws Exception{
		Git git = new Git(buildRepo(repo));
		Iterable<RevCommit> log = git.log().call();
		log.forEach(l -> System.out.println(l.getShortMessage()));
		git.close();
	}
	
	
	private org.eclipse.jgit.lib.Repository buildRepo(Repository repo) throws IOException{
		FileRepositoryBuilder builder = new FileRepositoryBuilder();
		org.eclipse.jgit.lib.Repository repository = builder.setGitDir(new File(config.localRepositoryDirectory + "/" + repo.getName()+"_"+repo.getId()+"/.git"))
		  .readEnvironment() // scan environment GIT_* variables
		  .findGitDir() // scan up the file system tree
		  .build();
		return repository;
	}

}
