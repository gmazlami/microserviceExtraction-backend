package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.classprocessing;

import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.ClassContent;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;
import ch.uzh.ifi.seal.monolith2microservices.utils.ClassContentFilter;
import ch.uzh.ifi.seal.monolith2microservices.utils.FilterInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gmazlami on 11/30/16.
 */
public class ClassContentVisitor extends SimpleFileVisitor<Path> {

    // Define a matcher that only matches on .java, .rb. and .py files
    private PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.{java,py,rb}");

    private List<ClassContent> classes;

    private GitRepository repo;

    private Configs config;

    private FilterInterface filterInterface;

    public ClassContentVisitor(GitRepository repo, Configs config, FilterInterface filterInterface) {
        this.classes = new ArrayList<>();
        this.repo = repo;
        this.config = config;
        this.filterInterface = filterInterface;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        try{
            Path name = path.getFileName();

            if(matcher.matches(name)){
                BufferedReader reader = Files.newBufferedReader(path);
                StringBuilder sb = new StringBuilder();
                String currentLine;
                while((currentLine = reader.readLine()) != null){
                    //filter out import statements
                    for(String importStatement: StopWords.IMPORT_KEYWORDS){
                        if(currentLine.startsWith(importStatement)){
                            continue;
                        }
                    }
                    sb.append(currentLine);
                }
                this.classes.add(new ClassContent(getRelativeFileName(path.toUri().toString()),filterInterface.filterFileContent(sb.toString())));
            }
        }catch(MalformedInputException mE){
            System.out.println(path.getFileName());
        }
        return FileVisitResult.CONTINUE;
    }

    public List<ClassContent> getClasses(){
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



}
