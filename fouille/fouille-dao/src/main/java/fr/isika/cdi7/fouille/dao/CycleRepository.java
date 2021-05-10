package fr.isika.cdi7.fouille.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.EtatProjet;

public interface CycleRepository extends CrudRepository<Cycle, Long> {
	List<Cycle> findAll();

	Cycle findById(long id);

	@Modifying
	@Transactional
	@Query("delete from Cycle where id=(:id)")
	void deleteById(@Param("id") long id);

	@Query("SELECT a FROM Cycle a where a.projet.id = :idProjet ")
	public Cycle getCycleIdProjet(@Param("idProjet") Long idProjet);

	@Query("SELECT c FROM Cycle c where c.projet.id = :idProjetParam")
	public LinkedList<Cycle> findAllCyclesOfProjet(@Param("idProjetParam") Long idProjet);

//	@Query("SELECT c FROM Cycle c where c.mission.dateFin  < :dateDuJour and  c.projet.gestionnaire = :idAdmin")
//	public List<Cycle> getCycleFinDeMission(List<Cycle> listecycle);
	
	@Query("SELECT c FROM Cycle c where c.mission.dateFin  < :dateDuJour and  c.projet.gestionnaire.id = :idAdmin and c.projet.etat = :enMission and c.saison != 'DIX' group by c.projet.id")
	public List<Cycle> getCycleFinDeMission(Date dateDuJour,Long idAdmin, EtatProjet enMission );

}
