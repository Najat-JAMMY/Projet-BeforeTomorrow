package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.cdi7.fouille.model.Mission;;

public interface MissionRepository extends CrudRepository<Mission, Integer> {

	List<Mission> findAll();
	Mission findById(Long idMission);

	@Query("SELECT a FROM Mission a where a.cycle.id = :idCycle")
	public Mission getMissionParCycleId(@Param("idCycle") Long idCycle);
//Methode  Lu
	@Query("SELECT c FROM Mission c WHERE c.cycle.id = :idCycle")
	Mission getMissionByCycleId(@Param("idCycle") Long idCycle);


}
