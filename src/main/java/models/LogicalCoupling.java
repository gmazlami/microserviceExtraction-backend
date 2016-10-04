package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class LogicalCoupling {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToMany
	private List<String> classFiles = new ArrayList<>();
	
	private int score;
	
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

	@Override
	public String toString() {
		return "LogicalCoupling [classes=" + classFiles + ", score=" + score + ", hash=" + hash + "]";
	}
	
}
