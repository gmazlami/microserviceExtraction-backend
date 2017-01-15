package ch.uzh.ifi.seal.monolith2microservices.services.evaluation;

import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by gmazlami on 1/12/17.
 */
public class ClassLOCVisitor extends SimpleFileVisitor<Path> {

    // Define a matcher that only matches on .java, .rb. and .py files
    private PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.{java,py,rb}");

    private GitRepository repo;

    private Configs config;

    private Map<String,Integer> filenameLOCMap = new HashMap<>();

    private List<String> classFilePaths;

    public ClassLOCVisitor(GitRepository repository, Configs c, List<String> classFiles){
        this.repo = repository;
        this.config = c;
        this.classFilePaths = classFiles;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        try{
            Path name = path.getFileName();

            if(matcher.matches(name)){
                int lineCount = 0;
                BufferedReader reader = Files.newBufferedReader(path);

                while(reader.readLine() != null){
                    lineCount++;
                }
                this.filenameLOCMap.put(name.toString(),lineCount);
            }
        }catch(MalformedInputException mE){
            System.out.println(path.getFileName());
        }
        return FileVisitResult.CONTINUE;
    }

    public Map<String,Integer> getLOCMap(){
        return this.filenameLOCMap;
    }
}
