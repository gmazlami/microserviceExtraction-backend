package services.filehandling;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.Configs;
import models.Class;
import models.Repository;

public class ClassVisitor extends SimpleFileVisitor<Path> {
	
	private PathMatcher matcher;
	
	private List<Class> classes;
	
	private Repository repo;
	
	private Configs config;
	
	public ClassVisitor(Repository repo, Configs config) {
		matcher = FileSystems.getDefault().getPathMatcher("glob:*.java");
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
        	classes.add(cls);
    	}
    	return FileVisitResult.CONTINUE;
    }

    public List<Class> getClasses(){
    	return this.classes;
    }
    
    private String getPackageName(String filePath){
    	
    	//FIXME: CAUTION: What if config.localRepositoryDirectory is also a valid name for a repo?? --> splitting will produce invalid result!
    	//IDEA: Take only the first if there are multiple occurences of config.localRepositoryDirectory
    	String[] packageNameArray = filePath.split(config.localRepositoryDirectory);
    	
    	
    	//TODO: implement parsing of package name like so..
    	// 1) Split on 'configs.localRepositoryDirectory'
    	// 2) Take second part from split
    	// 3) Remove regex "{repo_name}_{id_num}" from remaining string
    	// 4) Remove "src" if it exists
    	// 5) Remaining part of the string: Replace "/" with "." --> packageName
    	
    	return null;
    }
}
