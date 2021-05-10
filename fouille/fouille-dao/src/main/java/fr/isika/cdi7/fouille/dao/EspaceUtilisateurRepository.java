package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.isika.cdi7.fouille.model.EspaceUtilisateur;

public interface EspaceUtilisateurRepository extends CrudRepository<EspaceUtilisateur, Long> {

	List<EspaceUtilisateur> findAll();

	@Query("SELECT u.personne.id from EspaceUtilisateur u where u.email = :email")
	Long getIdByEmail(String email);

	@Query("SELECT u from EspaceUtilisateur u where u.email = :email")
	EspaceUtilisateur findByEmail(String email);

	@Query("SELECT u from EspaceUtilisateur u where u.email = :email and u.personne.id = :id")
	EspaceUtilisateur findByEmails(String email, Long id);

	@Query("SELECT u from EspaceUtilisateur u where u.personne.id = :id")
	EspaceUtilisateur getEspaceUserParIdPersonne(Long id);

}
