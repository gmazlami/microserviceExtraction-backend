package ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.classprocessing;

import ch.uzh.ifi.seal.monolith2microservices.main.Configs;
import ch.uzh.ifi.seal.monolith2microservices.models.ClassContent;
import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

import java.io.BufferedReader;
import java.io.IOException;
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

    public ClassContentVisitor(GitRepository repo, Configs config) {
        this.classes = new ArrayList<>();
        this.repo = repo;
        this.config = config;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        Path name = path.getFileName();

        if(matcher.matches(name)){
            BufferedReader reader = Files.newBufferedReader(path);
            StringBuilder sb = new StringBuilder();
            String currentLine;
            while((currentLine = reader.readLine()) != null){
                sb.append(currentLine);
            }

            this.classes.add(new ClassContent(getRelativeFileName(path.toUri().toString()),filter(sb.toString())));
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

    private List<String> filter(String rawFileContent){

        //remove all special symbols
        for(char specialCharacter : StopWords.SPECIAL_SYMBOLS){
            rawFileContent = rawFileContent.replace(specialCharacter,' ');
        }

        List<String> filteredContent = new ArrayList<>();

        //tokenize
        String[] tokens = rawFileContent.split("\\s+");

        //filter out reserved keywords for programming languages
        for(String token: tokens){
            if(!StopWords.JAVA_KEYWORDS.contains(token) && !StopWords.RUBY_KEYWORDS.contains(token) && !StopWords.PYTHON_KEYWORDS.contains(token) && (token.length() > 1)){
                filteredContent.add(token);
            }
        }

        return filteredContent;
    }

}
