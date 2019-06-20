package com.siw.project.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.siw.project.models.Photo;
import com.siw.project.repository.PhotoRepository;

@Service
public class PhotoService {
	
	@Autowired
	private PhotoRepository pr;
	
	@Transactional
	public Photo salva(Photo p) {
		return this.pr.save(p);
	}
	
	public Iterable<Photo> findAllById(Iterable<Long> ids) {
		return this.pr.findAllById(ids);
	}
	
	public List<Photo> tutte() {
		return (List<Photo>) pr.findAll();
	}
	
	public Page<Photo> photoPage(int page) {
		return pr.findAll(PageRequest.of(page, 4));
	}

}
