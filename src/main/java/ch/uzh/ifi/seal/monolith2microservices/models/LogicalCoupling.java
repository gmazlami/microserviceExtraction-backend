package ch.uzh.ifi.seal.monolith2microservices.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LogicalCoupling {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ElementCollection
	private List<String> classFiles = new ArrayList<>();
	
	private int score;
	
	private int startTimestamp;
	
	private int endTimestamp;
	
	@Column(unique=true)
	private String hash;

	public List<String> getClasses() {
		return classFiles;
	}

	public void setClasses(List<String> classes) {
		this.classFiles = classes;
	}
	
	public void addClass(String cls){
		if (cls != null){
			this.classFiles.add(cls);
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void incrementScore(){
		this.score++;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getClassFiles() {
		return classFiles;
	}

	public void setClassFiles(List<String> classFiles) {
		this.classFiles = classFiles;
	}

	public int getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(int startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public int getEndTimestamp() {
		return endTimestamp;
	}

	public void setEndTimestamp(int endTimestamp) {
		this.endTimestamp = endTimestamp;
	}

	@Override
	public String toString() {
		return "LogicalCoupling [classes=" + classFiles + ", score=" + score + ", hash=" + hash + "]";
	}
	
}
