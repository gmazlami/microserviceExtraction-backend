package ch.uzh.ifi.seal.monolith2microservices.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GitRepository {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String remotePath;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRemotePath() {
		return remotePath;
	}

	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Repository [id=" + id + ", remotePath=" + remotePath + ", name=" + name
				+ "]";
	}

	
	public String getDirectoryName(){
		return this.name + "_" + this.id;
	}
	
}
