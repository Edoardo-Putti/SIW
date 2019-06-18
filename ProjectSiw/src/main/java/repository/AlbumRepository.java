package repository;

import org.springframework.data.repository.CrudRepository;

import models.Album;

public interface AlbumRepository extends CrudRepository<Album, Long>{

}
