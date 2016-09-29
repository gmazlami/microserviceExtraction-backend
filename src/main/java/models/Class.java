package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Class {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String packageName;
	
	@Column(unique=true)
	private String filePath;
	
	@ManyToOne
	private GitRepository repo;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GitRepository getRepo() {
		return repo;
	}

	public void setRepo(GitRepository repo) {
		this.repo = repo;
	}

	@Override
	public String toString() {
		return "Class [name=" + name + ", packageName=" + packageName + ", filePath=" + filePath + "]";
	}

	
}
