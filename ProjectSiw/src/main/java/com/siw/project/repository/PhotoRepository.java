package com.siw.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.siw.project.models.Photo;

public interface PhotoRepository extends PagingAndSortingRepository<Photo, Long>{

}
