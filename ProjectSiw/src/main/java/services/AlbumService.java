package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.Album;
import repository.AlbumRepository;

@Service
public class AlbumService {
	
	@Autowired
	private AlbumRepository ar;
	
	@Transactional
	public Album inserisci(Album album) {
		return this.ar.save(album);
	}
	
	@Transactional
	public List<Album> tutti(){
		return (List<Album>) ar.findAll();
	}

}
