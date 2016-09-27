package models;

import java.util.List;

public class LogicalCoupling {

	private List<Class> classes;
	
	private int score;
	
	private String hash;

	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	@Override
	public String toString() {
		return "LogicalCoupling [classes=" + classes + ", score=" + score + ", hash=" + hash + "]";
	}
	
}
