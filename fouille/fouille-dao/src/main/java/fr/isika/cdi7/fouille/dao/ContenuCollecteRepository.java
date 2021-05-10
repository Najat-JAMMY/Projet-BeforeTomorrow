package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.cdi7.fouille.model.ContenuCollecte;

public interface ContenuCollecteRepository extends CrudRepository<ContenuCollecte, Long> {

	@Query("SELECT c FROM ContenuCollecte c WHERE c.collecte.id = :idCollecteParam")
	ContenuCollecte getContenuCollecteByCollecteId(@Param("idCollecteParam") Long idCollecte);

	List<ContenuCollecte>findAll();
	
	@Query("SELECT c FROM ContenuCollecte c WHERE c.id = :refIdContenu")
	ContenuCollecte findContenuCollecteBYRefMedia(Long refIdContenu);

	
}