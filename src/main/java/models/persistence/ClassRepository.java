package models.persistence;

import org.springframework.data.repository.CrudRepository;

import models.Class;

public interface ClassRepository extends CrudRepository<Class, Long> {

}
