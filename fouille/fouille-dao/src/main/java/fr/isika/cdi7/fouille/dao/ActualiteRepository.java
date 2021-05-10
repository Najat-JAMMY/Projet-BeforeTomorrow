package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.TypeContenu;

public interface ActualiteRepository extends CrudRepository<Actualite, Long> {
	
	List<Actualite> findAll();
	
	@Query("SELECT a FROM Actualite a where a.typeContenu = :typeContenuParam")
	List<Actualite> findActualiteParTypeContenu(@Param("typeContenuParam") TypeContenu typeContenu);

	@Query("SELECT a FROM Actualite a where a.typeContenu = :etat and a.refIdContenu = :idContenu")
	List<Actualite> getListeActualiteByEtatProjetEtContenue(Long idContenu, TypeContenu etat);

	@Query("SELECT a FROM Actualite a where a.typeContenu = :etat")
	List<Actualite> getListeActualiteByEtatProjetEtContenue2(TypeContenu etat);


}
