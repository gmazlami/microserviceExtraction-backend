package ch.uzh.ifi.seal.monolith2microservices.models.persistence;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import ch.uzh.ifi.seal.monolith2microservices.models.git.Class;

public interface ClassRepository extends CrudRepository<Class, Long> {

	@Query(value="SELECT * FROM class WHERE relative_file_path = ?1 AND repo_id = ?2", nativeQuery = true)
	Class findByFilePath(String relativePath, Long repoId);
}
