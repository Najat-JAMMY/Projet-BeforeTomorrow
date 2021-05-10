package fr.isika.cdi7.fouille.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.EtatProjet; 

public interface CollecteRepository extends CrudRepository<Collecte, Long> {

	@Query("SELECT a FROM Collecte a where a.cycle.id = :idCycle")
	public Collecte getCollecteIdCycle(@Param("idCycle") Long idCycle);

	Collecte findById(long id);

	
	List<Collecte>findAll();

	@Query("SELECT a FROM Collecte a where a.dateCloture < :dateDuJour and a.cycle.projet.etat = :etat")
	public List<Collecte> getProjetEnFinDeCollecte(Date dateDuJour, EtatProjet etat);



}
