package fr.isika.cdi7.fouille.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.isika.cdi7.fouille.model.MediaActualite;

public interface MediaRepository extends CrudRepository<MediaActualite, Long> {

	@Query("SELECT u from EspaceUtilisateur u where u.email = :email and u.personne.id = :id")
	MediaActualite findByEmails(String email, Long id);
	
	@Query("SELECT m from MediaActualite m where m.actualite.id = :idActualite")
	MediaActualite findByIdActualite(Long idActualite);
	

	
	@Query("SELECT m from MediaActualite m where m.actualite.id = :idActualite")
	MediaActualite getByIdActualite(Long idActualite);

}
