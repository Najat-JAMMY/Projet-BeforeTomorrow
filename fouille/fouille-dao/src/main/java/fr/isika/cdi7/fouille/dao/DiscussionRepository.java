package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.isika.cdi7.fouille.model.Discussion;

public interface DiscussionRepository extends CrudRepository<Discussion, Long> {

	@Query("SELECT d from Discussion d where d.utilisateur.id = :idEspacePorteur and d.staff.id = :idEspaceGestionnaire")
	Discussion getDiscussionGestionnairePorteur(Long idEspacePorteur, Long idEspaceGestionnaire);

	Discussion findById(long id);
	
	@Query("SELECT d from Discussion d where d.staff.id IS NULL")
	List<Discussion> getDiscussionSupport();
	
	@Query("SELECT d from Discussion d where d.utilisateur.id = :id")
	List<Discussion> findDiscussionUtilisateurLambda(Long id);

	@Query("SELECT d from Discussion d where d.utilisateur.id = :id")
	Discussion findByIdUtilisateur(Long id);

	@Query("SELECT d from Discussion d where d.utilisateur.id = :id and d.staff.id IS NOT NULL")
	Discussion findDiscussionConseillerByIdUtilisateur(Long id);
	
	@Query("SELECT d from Discussion d where d.utilisateur.id = :id and d.staff.id IS NULL")
	List<Discussion> findDiscussionSupportByIdUtilisateur(Long id);

}

