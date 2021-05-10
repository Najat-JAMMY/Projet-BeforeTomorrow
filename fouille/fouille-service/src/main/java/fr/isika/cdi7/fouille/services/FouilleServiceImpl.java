package fr.isika.cdi7.fouille.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.ActualiteRepository;
import fr.isika.cdi7.fouille.dao.PaysRepository;
import fr.isika.cdi7.fouille.dao.ProjetRepository;
import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.Pays;
import fr.isika.cdi7.fouille.model.Projet;

@Service
public class FouilleServiceImpl implements FouilleService {

	@Autowired
	private ActualiteRepository actualiteRepository;
	@Autowired
	private PaysRepository paysRepository;
	@Autowired
	private ProjetRepository projetRepository;


	@Override
	public Actualite saveActualite(Actualite actualite) {
		return actualiteRepository.save(actualite);
	}

	@Override
	public List<Actualite> listActualite() {
		return actualiteRepository.findAll();
	}

	@Override
	public Projet saveProjet(Projet projet) {
		return projetRepository.save(projet);
	}

	@Override
	public List<Projet> listProjet() {
		return projetRepository.findAll();
	}

	@Override
	public List<Pays> getlistPays() {
		return paysRepository.findAll();
		}


}
