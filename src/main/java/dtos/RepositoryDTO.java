package dtos;

public class RepositoryDTO {

	private String uri;
	
	private String name;
	
	
	public String getUri(){
		return this.uri;
	}
	
	public void setUri(String uri){
		this.uri = uri;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}

	@Override
	public String toString() {
		return "RepositoryDTO [uri=" + uri + ", name=" + name + "]";
	}
	
	
	
}
