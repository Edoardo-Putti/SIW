package repository;

import org.springframework.data.repository.CrudRepository;

import models.Photographer;

public interface PhotographerRepository extends CrudRepository<Photographer, Long>{

}
