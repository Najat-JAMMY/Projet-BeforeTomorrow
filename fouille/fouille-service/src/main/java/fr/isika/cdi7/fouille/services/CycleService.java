package fr.isika.cdi7.fouille.services;

import java.io.IOException;
import java.util.Comparator;

import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.form_objects.ActualiteDto;
import fr.isika.cdi7.fouille.model.form_objects.MissionContenueMissionDto;

public interface CycleService {

	public Cycle findById(long id);

	public Cycle save(Cycle cycle);

	public void remove(Long cycleId);
	
	
	 ContenuMission findByMissionId(long missionId);

	public void editerMission(MissionContenueMissionDto missionContenueMissionDto,Long projetId) throws IOException;
	
	Cycle getLastCycleFromProjet(Projet projet);

	public void editerActualite(ActualiteDto actualiteDto) throws IOException;

	
	
}
