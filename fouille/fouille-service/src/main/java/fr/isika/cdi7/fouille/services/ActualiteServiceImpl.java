package fr.isika.cdi7.fouille.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.isika.cdi7.fouille.dao.ActualiteRepository;
import fr.isika.cdi7.fouille.dao.ContenuCollecteRepository;
import fr.isika.cdi7.fouille.dao.ContenuMissionRepository;
import fr.isika.cdi7.fouille.dao.MediaRepository;
import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.MediaActualite;
import fr.isika.cdi7.fouille.model.TypeContenu;


@Service
public class ActualiteServiceImpl implements ActualiteService {
	@Autowired
	private ActualiteRepository actualiteRepo;
	
	@Autowired
	private MediaRepository mediaRepo;
	
	@Autowired
	private ContenuCollecteRepository ccRepo;
	
	@Autowired
	private ContenuMissionRepository cmRepo;

	@Override
	public Actualite findById(Long idActualite) {
		return actualiteRepo.findById(idActualite).get();
	}


	@Override
	public List<MediaActualite> getListMediaByListeActualite(List<Actualite> listActualite) {
		List<MediaActualite> liste = new ArrayList();
		for (Actualite a : listActualite) {
			
			liste.add(mediaRepo.findByIdActualite(a.getId()));
			
		}
		return liste;
	}


	@Override
	public List<Actualite> getListActualiteByIdContenuEtTypeContenu(Long idContenu, TypeContenu etat) {
		return  actualiteRepo.getListeActualiteByEtatProjetEtContenue(idContenu,etat);
		
	}




	@Override
	public MediaActualite getMediaByIdActualite(Long idActualite) {
		
		return mediaRepo.getByIdActualite(idActualite);
	}


	@Override
	public ContenuCollecte findContenuCollecteBYRefMedia(Long refIdContenu) {
		return ccRepo.findContenuCollecteBYRefMedia(refIdContenu);
	}


	@Override
	public ContenuMission findContenuCollecteMissionBYRefMedia(Long refIdContenu) {
	
		return cmRepo.findContenuCollecteMissionBYRefMedia(refIdContenu);
	}
}
