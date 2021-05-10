package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.cdi7.fouille.model.ContrePartie;

public interface ContrePartieRepository extends CrudRepository<ContrePartie, Long> {

	@Query("SELECT a FROM ContrePartie a where a.montant < :montant and a.collecte.id = :idCollecte")
	public List<ContrePartie> getListContrePartieParMontantTest(@Param("montant") Integer montant,
			@Param("idCollecte") Long idCollecte);

	@Query("SELECT cp FROM ContrePartie cp where cp.collecte.id = :idCollecteParam")
	List<ContrePartie> getListeContrePartieByCollecteId(@Param("idCollecteParam") Long idCollecte);

}
