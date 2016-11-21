package ch.uzh.ifi.seal.monolith2microservices.models.git;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Class {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@Lob
	private String packageName;
	
	@Column(unique=true)
	@Lob
	private String filePath;
	
	@Column(unique=true)
	@Lob
	private String relativeFilePath;
	
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
	
	public String getRelativeFilePath() {
		return relativeFilePath;
	}

	public void setRelativeFilePath(String relativeFilePath) {
		this.relativeFilePath = relativeFilePath;
	}

	@Override
	public String toString() {
		return this.relativeFilePath;
	}

	
}
