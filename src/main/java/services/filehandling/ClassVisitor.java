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

import models.Class;
import models.Repository;

public class ClassVisitor extends SimpleFileVisitor<Path> {
	
	private PathMatcher matcher;
	
	private List<Class> classes;
	
	private Repository repo;
	
	public ClassVisitor(Repository repo) {
		matcher = FileSystems.getDefault().getPathMatcher("glob:*.java");
		classes = new ArrayList<>();
		this.repo = repo;
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
}
