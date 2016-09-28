package models.persistence;

import models.GitRepository;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryRepository extends CrudRepository<GitRepository,Long>{

	GitRepository findById(Long id);
}
