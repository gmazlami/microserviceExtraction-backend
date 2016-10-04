package models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Microservice {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToMany
	private List<Class> classes = new ArrayList<>();
	
	@ManyToMany
	private List<Microservice> relations = new ArrayList<>();
	
	@Column(unique=true)
	private String hash;
	
	private int score;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public void addRelation(Microservice m){
		this.relations.add(m);
	}
	
	public List<Microservice> getRelations() {
		return relations;
	}

	public void setRelations(List<Microservice> relations) {
		this.relations = relations;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void removeClass(Class cls){
		for(Iterator<Class> iterator = this.classes.iterator(); iterator.hasNext();){
			Class current = iterator.next();
			if(current.getFilePath().equals(cls.getFilePath())){
				iterator.remove();
				return;
			}
		}
	}
	
	

	@Override
	public String toString() {
		return "Microservice [id=" + id + ", classes=" + classes + ", hash=" + hash + "]";
	}
	
}
