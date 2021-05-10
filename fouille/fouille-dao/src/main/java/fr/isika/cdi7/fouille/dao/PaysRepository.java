package fr.isika.cdi7.fouille.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.isika.cdi7.fouille.model.Pays;

public interface PaysRepository extends CrudRepository<Pays, Integer> {
	
	@Query("SELECT p FROM Pays p where p.nomPays_fr = :nomPays_fr")
	Pays findByNomfr(String nomPays_fr);
	
	List<Pays> findAll() ;
	
	Optional<Pays> findById(Long id);
}
