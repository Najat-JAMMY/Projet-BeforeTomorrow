package fr.isika.cdi7.fouille.services;

import java.util.List;
import java.util.Optional;

import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.ContrePartie;

import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.MediaActualite;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Pays;
import fr.isika.cdi7.fouille.model.Personne;

import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.Saison;
import fr.isika.cdi7.fouille.model.TypeContenu;
import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.Commentaire;

public interface ConsulterProjetService {

	Optional<Projet> getProjetById(Long id);

	ContenuCollecte getContenuCollecteParCollecteId(Long idCollecte);
	
	ContenuCollecte getContenuCollecteParId(long id);

	List<ContrePartie> getListeContrePartieByCollecteId(Long idCollecte);
	
	Optional<Mission> getMissionParCycleId(Long idCycle);
	
	Optional<Cycle> getDernierCycle(Long idProjet);

	Optional<Saison> getDerniereSaison(Long idProjet);

	long getCompteARebours(Long id);

	long getCompteAReboursMission(Long idMission);
	
	Integer getPourcentageMontantAtteint(Long idCollecte);

	List<Personne> getListeContributeurs(Long idCollecte);

	List<Actualite> getListeActualites(Long idContenuCollecte, TypeContenu typeContenu);

	List<Commentaire> getListeCommentaires(Long idProjet);

	Optional<Pays> getLocalisationMission(Mission mission);

	Mission getMissionByCycleId(Long idCycle);
	
	List<Projet> findByProjetEtat(EtatProjet etat);

	ContenuMission getContenuMissionById(long idContenuMission);

	Cycle getDernierCycleprojet(Long idProjet);

	MediaActualite getActualiteImage(Long mediaActualitesId);

	List<Commentaire> getListeCommentairesParActualite(Long idActualite);




}
