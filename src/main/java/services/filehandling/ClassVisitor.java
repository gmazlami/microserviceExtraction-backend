package services.filehandling;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

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
        	cls.setPackageName(getPackageName(cls.getFilePath()));
        	classes.add(cls);
    	}
    	return FileVisitResult.CONTINUE;
    }

    public List<Class> getClasses(){
    	return this.classes;
    }
    
    private String getPackageName(String filePath){
    	String[] packageNameArray = filePath.split(config.localRepositoryDirectory);
    	
    	String qualifiedPathName;
    	if(packageNameArray.length > 2){
    		qualifiedPathName = filePath.replace(packageNameArray[0]+config.localRepositoryDirectory, "");
    	}else{
    		qualifiedPathName = packageNameArray[1];
    	}
    	
    	String srcPathName = qualifiedPathName.replace(this.repo.getDirectoryName(),"");
    	
    	if(srcPathName.contains("/src/")){
    		srcPathName = srcPathName.replace("/src/", "");
    	}
    	
    	int trimIndex = srcPathName.lastIndexOf("/");
    	String packageName = srcPathName.substring(0, trimIndex).replace("/", ".");
    	
    	return packageName.substring(1);
    }
    
}
