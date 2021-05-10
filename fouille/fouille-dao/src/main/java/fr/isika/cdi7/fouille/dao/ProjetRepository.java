
package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Projet;

public interface ProjetRepository extends CrudRepository<Projet, Integer> {

	List<Projet> findAll();

	Projet findById(long id);

	@Query("SELECT pr from Projet pr where pr.etat = :etatParam")
	List<Projet> findProjetByEtat(@Param("etatParam") EtatProjet etat);

	@Query("SELECT pr from Projet pr where pr.gestionnaire.id = :idAdmin")
	List<Projet> getProjetGestionnaire(Long idAdmin);

	@Query("SELECT pr from Projet pr where pr.gestionnaire.id = :idAdmin and pr.etat = :etat")
	List<Projet> getProjetParEtat(EtatProjet etat, Long idAdmin);

	@Query("SELECT pr from Projet pr where pr.etat = :etat")
	List<Projet> getProjetEnAttente(EtatProjet etat);

	@Query("SELECT pr from Projet pr")
	List<Projet> getNbProjetByEtat3();

	@Query("SELECT pr from Projet pr where pr.porteur.id = :idPersonne")
	Projet getProjetIdPorteur(Long idPersonne);

}
