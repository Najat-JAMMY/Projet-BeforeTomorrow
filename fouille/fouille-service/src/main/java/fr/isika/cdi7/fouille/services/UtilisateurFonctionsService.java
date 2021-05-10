package fr.isika.cdi7.fouille.services;

import fr.isika.cdi7.fouille.model.Commentaire;
import fr.isika.cdi7.fouille.model.Favoris;

public interface UtilisateurFonctionsService {
	
	Favoris save(Favoris favori);
	
	void delete(Favoris favori);
	
	void ajouterAuxFavoris(Long idEspaceUtilisateur, Long idProjet);
		
	void retirerDesFavoris(Long idEspaceUtilisateur, Long idProjet);

	Favoris verifierSiFavoriDansEspaceUser(Long idEspaceUtilisateur, Long idProjet);
	
	Commentaire save(Commentaire commentaire);

	void posterUnCommentaireSurActualite(Long idEspaceUtilisateur, Long idProjet, Long idActualite, String text);

	void posterUnCommentaire(Long id, Long idProjet, String commentaire);


}
