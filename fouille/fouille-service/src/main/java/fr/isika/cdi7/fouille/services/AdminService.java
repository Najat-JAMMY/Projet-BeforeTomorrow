package fr.isika.cdi7.fouille.services;

import java.util.List;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Commentaire;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Message;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.Saison;
import fr.isika.cdi7.fouille.model.TypeRole;

public interface AdminService {
	
	EspaceUtilisateur findIdAdmin(String email);
	Long getIdByEmail(String email);
	
	List<Projet> getProjetEnAttnte();
	List<Projet> getProjetgestionnaireEnPrepaCollecte(Long idAdmin);
	List<Projet> getProjetGestionnaireCollecte(Long idAdmin);
	List<Projet> getProjetSuiviParGestionnaire(Long idAdmin);

	void accepterprojet(String Projet, EspaceUtilisateur espace);
	void rejeterProjet(String idProjet);
	void accepterProjetEnCollecte(String idProjet,String idCollecte);
	void refuserProjetEnCollecte(String idProjet);
	List<Projet> getProjetGestionnaireParEtat(EtatProjet etat, Long idAdmin);
	List<Projet> getProjetGestionnaireEnAttente(EtatProjet etat);
	void envoyerMessage(String message, String idDiscussion, EspaceUtilisateur user);
	void MettreMessageEnlus(Discussion discussion);
	
	List<Collecte> getProjetFinDeCollecte(EtatProjet enCampagneDeCollecte);
	void changementEtatProjetById(String idProjet, EtatProjet etat);
	List<ContenuCollecte> getListContenuCollecte(List<Collecte> listeDeProjetEnFinDeCollecte);

	List<Discussion> getDiscussionSupport();
	List<Message> getMessageDiscussionSupport(Long idStaff);
	List<Message> getMessageDiscussion(Long idDiscussion);
	List<Message> getMessageDiscussionSupportNonLus(Long id);
	void SetMessageEnLus(List<Message> listeMessage, EspaceUtilisateur user);
	List<Personne> getListeUtilisateur();
	List<EspaceUtilisateur> getEspaceUtilisateurParListePersonne(List<Personne> listePersonne);
	void SupprimerCompte(Long idPersonne);
	List<Commentaire> getListeCommentaires();
	void SupprimerCommentaire(Long idCommentaire);
	List<Personne> getListeEmployes(Long id);
	void changementRolePersonneById(String idProjet, TypeRole role);
	List<Cycle> getProjetGestionnaireFinDeMission(EtatProjet enMission, Long idAdmin);

	 List<Cycle> getLastCycleFromProjet(List<Projet> Listeprojet);
	void Reconduirprojet(String idProjet, EspaceUtilisateur user, Saison saison);
	
}
