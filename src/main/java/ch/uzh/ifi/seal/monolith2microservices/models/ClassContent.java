package ch.uzh.ifi.seal.monolith2microservices.models;

import java.util.List;

/**
 * Created by gmazlami on 11/30/16.
 */
public class ClassContent {

    private String filePath;

    private List<String> tokenizedContent;

    public ClassContent(String filePath, List<String> tokenizedContent){
        this.filePath = filePath;
        this.tokenizedContent = tokenizedContent;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public List<String> getTokenizedContent() {
        return tokenizedContent;
    }

    public void setTokenizedContent(List<String> tokenizedContent) {
        this.tokenizedContent = tokenizedContent;
    }
}
