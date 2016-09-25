package models.persistence;

import models.Repository;
import org.springframework.data.repository.CrudRepository;

public interface RepositoryRepository extends CrudRepository<Repository,Long>{

	Repository findById(Long id);
}
