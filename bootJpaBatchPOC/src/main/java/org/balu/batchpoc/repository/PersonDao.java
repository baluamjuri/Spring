package org.balu.batchpoc.repository;

import org.balu.batchpoc.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends CrudRepository<Person, String> {

}
