package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.isika.cdi7.fouille.model.Don;

public interface DonRepository extends CrudRepository<Don, Integer> {

	@Query("SELECT d from Don d")
	List<Don> getNbDon();

	@Query("SELECT d from Don d where d.personne.id =:id group by d.collecte.cycle.projet.id")
	List<Don> getCollecteContributionDunePersonne(Long id);

}
