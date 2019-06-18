package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import models.Photographer;
import repository.PhotographerRepository;

@Service
public class PhotographerService {
	
	@Autowired
	private PhotographerRepository photographerRepository;
	
	@Transactional
	public Photographer inserisci(Photographer photographer) {
		return this.photographerRepository.save(photographer);
	}
	
	@Transactional 
	public List<Photographer> tutte() {
		return (List<Photographer>) photographerRepository.findAll();
	}
}
