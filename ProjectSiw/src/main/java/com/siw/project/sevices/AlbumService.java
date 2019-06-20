package com.siw.project.sevices;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.siw.project.models.Album;
import com.siw.project.repository.AlbumRepository;

@Service
public class AlbumService {
	
	@Autowired
	private AlbumRepository ar;
	
	@Transactional
	public Album inserisci(Album album) {
		return this.ar.save(album);
	}
	
	public List<Album> all() {
		return (List<Album>) ar.findAll();
	}

	public Album findById(Long id) {
		return ar.findById(id).get();
	}

	public Page<Album> albumPage(int page) {
		return ar.findAll(PageRequest.of(page, 6));
	}

}
