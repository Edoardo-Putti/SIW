package com.siw.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siw.project.models.Photographer;
import com.siw.project.repository.PhotographerRepository;

@Service
public class PhotographerService {
	
	@Autowired
	private PhotographerRepository photographerRepository;
	
	@Transactional
	public Photographer inserisci(Photographer photographer) {
		return this.photographerRepository.save(photographer);
	}
	
	public List<Photographer> all() {
		return (List<Photographer>) photographerRepository.findAll();
	}

	public Photographer findById(Long id) {
		return photographerRepository.findById(id).get();
	}
}
