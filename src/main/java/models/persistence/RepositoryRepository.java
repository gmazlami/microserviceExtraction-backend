package models.persistence;

import org.springframework.data.repository.CrudRepository;

import models.GitRepository;

public interface RepositoryRepository extends CrudRepository<GitRepository,Long>{

	GitRepository findById(Long id);
}
