package fr.isika.cdi7.fouille.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.ActualiteRepository;
import fr.isika.cdi7.fouille.dao.CommentaireRepository;
import fr.isika.cdi7.fouille.dao.EspaceUtilisateurRepository;
import fr.isika.cdi7.fouille.dao.FavorisRepository;
import fr.isika.cdi7.fouille.dao.ProjetRepository;
import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.Commentaire;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Favoris;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.utils.DateUtils;

@Service
public class UtilisateurFonctionsServiceImpl implements UtilisateurFonctionsService {

	@Autowired
	private EspaceUtilisateurRepository espaceRepository;
	@Autowired
	private FavorisRepository favorisRepository;
	@Autowired
	private ProjetRepository projetRepository;
	@Autowired
	private CommentaireRepository commentaireRepository;
	@Autowired
	private ActualiteRepository actualiteRepository;

	@Override
	public void ajouterAuxFavoris(Long idEspaceUtilisateur, Long idProjet) {
		Favoris favoriAAjouter = new Favoris();
		Optional<EspaceUtilisateur> eu = espaceRepository.findById(idEspaceUtilisateur);
		Projet projet = projetRepository.findById(idProjet);
		if (eu.isPresent()) {
			favoriAAjouter.setEspaceUtilisateur(eu.get());
			favoriAAjouter.setProjet(projet);
		}
		save(favoriAAjouter);
	}

	@Override
	public void retirerDesFavoris(Long idEspaceUtilisateur, Long idProjet) {
		Favoris favoriASupprimer = favorisRepository.findByProjetIdAndPersonneId(idEspaceUtilisateur, idProjet);
		delete(favoriASupprimer);
	}
	
	@Override
	public Favoris verifierSiFavoriDansEspaceUser(Long idEspaceUtilisateur, Long idProjet) {
		return favorisRepository.findByProjetIdAndPersonneId(idEspaceUtilisateur, idProjet);
	}

	@Override
	public Favoris save(Favoris favori) {
		return favorisRepository.save(favori);
	}

	@Override
	public void delete(Favoris favori) {
		favorisRepository.delete(favori);
	}
	
	@Override
	public void posterUnCommentaire(Long idEspaceUtilisateur, Long idProjet, String text) {
		save(obtenirCommentaire(idEspaceUtilisateur, idProjet, text));
	}
	
	@Override
	public void posterUnCommentaireSurActualite(Long idEspaceUtilisateur, Long idProjet, Long idActualite, String text) {
		Commentaire commentaire = obtenirCommentaire(idEspaceUtilisateur, idProjet, text);
		if (idActualite != null) {
			Optional<Actualite> actualite = actualiteRepository.findById(idActualite);
			if(actualite.isPresent()) {
				commentaire.setActualite(actualite.get());
			}
		}
		save(commentaire);
	}
	
	private Commentaire obtenirCommentaire(Long idEspaceUtilisateur, Long idProjet, String text) {
		Commentaire commentaire = new Commentaire();
		Optional<EspaceUtilisateur> eu = espaceRepository.findById(idEspaceUtilisateur);
		if (eu.isPresent()) {
			commentaire.setTextCommentaire(text);
			commentaire.setDate(DateUtils.obtenirDateDuJour());
			commentaire.setEspaceUtilisateur(espaceRepository.findById(idEspaceUtilisateur).get());
			commentaire.setProjet(projetRepository.findById(idProjet));
		}
		return commentaire;
	}
	
	
	

	@Override
	public Commentaire save(Commentaire commentaire) {
		return commentaireRepository.save(commentaire);
	}


	
	
	

}
