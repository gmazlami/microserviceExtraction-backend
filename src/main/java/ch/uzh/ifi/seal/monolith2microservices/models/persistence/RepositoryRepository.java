package ch.uzh.ifi.seal.monolith2microservices.models.persistence;

import org.springframework.data.repository.CrudRepository;

import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

import java.util.List;

public interface RepositoryRepository extends CrudRepository<GitRepository,Long>{

	GitRepository findById(Long id);

	List<GitRepository> findAll();
}
