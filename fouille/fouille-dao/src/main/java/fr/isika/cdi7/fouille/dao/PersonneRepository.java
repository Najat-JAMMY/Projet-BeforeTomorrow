package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.isika.cdi7.fouille.model.Personne;

public interface PersonneRepository extends CrudRepository<Personne, Long> {

	@Query("SELECT u from Personne u where u.nom = :usernames")
	Personne findByNom(String usernames);

	List<Personne> findAll();
	
	@Query("SELECT u from Personne u  where u.role = 'ROLE_UTILISATEUR' and u  not in (SELECT p.porteur from Projet p  )  ")
	List<Personne> getUtilisateur();

	@Query("SELECT u from Personne u  where u.role != 'ROLE_UTILISATEUR' and u.id != :id  ")
	List<Personne> getEmployes(Long id);
	


}
