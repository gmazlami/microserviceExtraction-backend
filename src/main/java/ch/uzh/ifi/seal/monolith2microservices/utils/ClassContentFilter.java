package ch.uzh.ifi.seal.monolith2microservices.utils;

import ch.uzh.ifi.seal.monolith2microservices.services.decomposition.semanticcoupling.classprocessing.StopWords;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genc on 15.01.2017.
 */
public class ClassContentFilter implements FilterInterface {

    @Override
    public List<String> filterFileContent(String rawFileContent) {
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
