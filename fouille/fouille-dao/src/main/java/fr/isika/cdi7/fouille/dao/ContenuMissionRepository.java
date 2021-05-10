package fr.isika.cdi7.fouille.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.cdi7.fouille.model.ContenuMission;

public interface ContenuMissionRepository extends CrudRepository<ContenuMission, Long>{
	 @Query("SELECT a FROM ContenuMission a where a.mission.id = :idMission")
	public ContenuMission findByMissionId(@Param("idMission")Long idMission);

	 @Query("SELECT a FROM ContenuMission a where a.id = :refIdContenu")
	public ContenuMission findContenuCollecteMissionBYRefMedia(Long refIdContenu);
	
}
