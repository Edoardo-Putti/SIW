package repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import models.Photo;

public interface PhotoRepository extends PagingAndSortingRepository<Photo, Long>{

}
