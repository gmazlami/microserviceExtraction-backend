package ch.uzh.ifi.seal.monolith2microservices.git;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.git.Class;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

public class ClassVisitor extends SimpleFileVisitor<Path> {
	
	// Define a matcher that only matches on .java files
	private PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.java");
	
	private List<Class> classes;
	
	private GitRepository repo;
	
	private Configs config;
	
	public ClassVisitor(GitRepository repo, Configs config) {
		classes = new ArrayList<>();
		this.repo = repo;
		this.config = config;
	}
	
    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs)
        throws IOException
    {
    	Path name = path.getFileName();
    	if(matcher.matches(name)){
        	Class cls = new Class();
        	cls.setName(name.toString());
        	cls.setRepo(repo);
        	cls.setFilePath(path.toUri().toString());
        	cls.setPackageName(getPackageName(cls.getFilePath()));
        	cls.setRelativeFilePath(getRelativeFileName(cls.getFilePath()));
        	classes.add(cls);
    	}
    	return FileVisitResult.CONTINUE;
    }

    public List<Class> getClasses(){
    	return this.classes;
    }
    
    private String getRelativeFileName(String filePath){
    	String[] packageNameArray = filePath.split(config.localRepositoryDirectory);
    	String qualifiedPathName;
    	if(packageNameArray.length > 2){
    		qualifiedPathName = filePath.replace(packageNameArray[0]+config.localRepositoryDirectory, "");
    	}else{
    		qualifiedPathName = packageNameArray[1];
    	}
    	return qualifiedPathName.replace(this.repo.getDirectoryName(),"").substring(1);
    }
    
    /*
     * Takes in a full absolute path to a Class file on the local file system and returns the dot-separeted
     * package name as it would be displayed at the top of a java class source file.
     */
    private String getPackageName(String filePath){
    	// We don't want the whole absolute path, hence we split by the local directory name in which
    	// the repositories are cloned into locally
    	String[] packageNameArray = filePath.split(config.localRepositoryDirectory);
    	
    	// Then, there are two possibilities:
    	// 1) The usual case: The config.localRepositoryDirectory name occurs only once in the absolute path
    	//	  This means the length of the resulting packageNameArray is 2, hence we take the second part at index 1 	
    	// 2) The special case: The cloned repository or the file might have the same value as config.localRepositoryDirectory in its path, and hence
    	// 	  after splitting, there will be > 2 elements in the resulting packageNameArray, then we just remove the entire prefix (index 0) 
    	//    concatenated with the config.localRepositoryDirectory
    	
    	String qualifiedPathName;
    	if(packageNameArray.length > 2){
    		qualifiedPathName = filePath.replace(packageNameArray[0]+config.localRepositoryDirectory, "");
    	}else{
    		qualifiedPathName = packageNameArray[1];
    	}
    	
    	// Since we only want the actual package name, we are not interested in the main directory name
    	// of the repository and also not interestend in the /src folder inside it (if present), since these
    	// are never part of the package name. Hence we remove them
    	
    	String srcPathName = qualifiedPathName.replace(this.repo.getDirectoryName(),"");
    	
    	if(srcPathName.contains("/src/")){
    		srcPathName = srcPathName.replace("/src/", "");
    	}
    	
    	//	Now we have to get rid of the actual name of the class to only remain with the package path.
    	//	Hence, we remove everything after the last / separator to get rid of it.
    	//  Also, we replace all remaining "/" symbols with a dot, to represent the Java package name
    	int trimIndex = srcPathName.lastIndexOf("/");
    	String packageName = srcPathName.substring(0, trimIndex).replace("/", ".");
    	
    	return packageName.substring(1);
    }
    
}
