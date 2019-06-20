package com.siw.project.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.siw.project.models.Album;

public interface AlbumRepository extends PagingAndSortingRepository<Album, Long> {
	
}
