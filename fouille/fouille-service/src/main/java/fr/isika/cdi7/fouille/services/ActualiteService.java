package fr.isika.cdi7.fouille.services;

import java.util.List;

import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.MediaActualite;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.TypeContenu;

public interface ActualiteService {

	
	Actualite findById(Long idActualite);


	List<MediaActualite> getListMediaByListeActualite(List<Actualite> listActualite);

	List<Actualite> getListActualiteByIdContenuEtTypeContenu(Long idContenu, TypeContenu etat);

	MediaActualite getMediaByIdActualite(Long idActualite);

	ContenuCollecte findContenuCollecteBYRefMedia(Long refIdContenu);


	ContenuMission findContenuCollecteMissionBYRefMedia(Long refIdContenu);
}

