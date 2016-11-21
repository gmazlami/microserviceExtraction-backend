package ch.uzh.ifi.seal.monolith2microservices.models.persistence;

import org.springframework.data.repository.CrudRepository;

import ch.uzh.ifi.seal.monolith2microservices.models.git.GitRepository;

public interface RepositoryRepository extends CrudRepository<GitRepository,Long>{

	GitRepository findById(Long id);
}
