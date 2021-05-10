package fr.isika.cdi7.fouille.services;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.ActualiteRepository;
import fr.isika.cdi7.fouille.dao.AdresseRepository;
import fr.isika.cdi7.fouille.dao.ContenuMissionRepository;
import fr.isika.cdi7.fouille.dao.CycleRepository;
import fr.isika.cdi7.fouille.dao.MediaRepository;
import fr.isika.cdi7.fouille.dao.MissionRepository;
import fr.isika.cdi7.fouille.dao.PaysRepository;
import fr.isika.cdi7.fouille.dao.ProjetRepository;
import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.Adresse;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.MediaActualite;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Pays;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.form_objects.ActualiteDto;
import fr.isika.cdi7.fouille.model.form_objects.MissionContenueMissionDto;

@Service
public class CycleServiceImpl implements CycleService {
	@Autowired
	private ProjetRepository projetRepository;
	@Autowired
	private ActualiteRepository actualiteRepository;
	@Autowired
	private CycleRepository cycleRepository;
	@Autowired
	private ContenuMissionRepository contenuMissionRepository;
	@Autowired
	private MissionRepository missionRepository;
	@Autowired
	private MediaRepository mediaRepository;
	@Autowired
	private AdresseRepository adresseRepository;
	@Autowired
	private PaysRepository paysRepository;

	public Cycle findById(long id) {
		return cycleRepository.findById(id);
	}

	@Override
	public Cycle save(Cycle projet) {
		return cycleRepository.save(projet);
	}

	@Override
	public void remove(Long cycleId) {
		cycleRepository.deleteById(cycleId.longValue());
	}

	@Override
	public ContenuMission findByMissionId(long missionId) {
		return contenuMissionRepository.findByMissionId(missionId);
	}

	@Override
	public void editerMission(MissionContenueMissionDto missionContenueMissionDto, Long projetId) throws IOException {
		Projet projet = projetRepository.findById(projetId);
		Cycle lastcycle = projet.getCycle().stream().max(Comparator.comparingLong(e -> e.getId())).get();

		Mission mission = lastcycle.getMission();
		mission.setDuree(missionContenueMissionDto.getDuree());
		missionRepository.save(mission);

		ContenuMission cm = mission.getContenuMission();
		cm.setDescCourteMission(missionContenueMissionDto.getDescriptionCourteMission());
		cm.setDescLongueMission(missionContenueMissionDto.getDescriptionLongMission());
		cm.setNomMission(missionContenueMissionDto.getNomMission());
		if (missionContenueMissionDto.getImage().getBytes() != null
				&& missionContenueMissionDto.getImage().getBytes().length > 0) {
			cm.setImage(missionContenueMissionDto.getImage().getBytes());
		}
		contenuMissionRepository.save(cm);
	}

	@Override
	public Cycle getLastCycleFromProjet(Projet projet) {
		Cycle cycle = projet.getCycle().stream().max(Comparator.comparingLong(e -> e.getId())).get();
		return cycle;
	}

	@Override
	public void editerActualite(ActualiteDto actualiteDto) throws IOException {
		Projet projet = projetRepository.findById(actualiteDto.getProjetId());
		Cycle lastcycle = projet.getCycle().stream().max(Comparator.comparingLong(e -> e.getId())).get();

		Actualite actualite = new Actualite();
		actualite.setDate(new Date());
		MediaActualite ma = new MediaActualite();

		if (projet.getEtat().equals(EtatProjet.EN_MISSION)) {
			Mission mission = lastcycle.getMission();
			ContenuMission cm = mission.getContenuMission();

			actualite.setContenu(actualiteDto.getTypeContenu().MISSION);
			actualite.setDescription(actualiteDto.getDescription());
			actualite.setRefIdContenu(cm.getId());
			actualiteRepository.save(actualite);
			ma.setMedia(actualiteDto.getImage().getBytes());
			ma.setActualite(actualite);
			mediaRepository.save(ma);
			

		}

		else if (projet.getEtat().equals(EtatProjet.EN_CAMPAGNE_DE_COLLECTE)) {
			Collecte collect = lastcycle.getCollecte();
			ContenuCollecte cc = collect.getContenuCollecte();

			actualite.setContenu(actualiteDto.getTypeContenu().COLLECTE);
			actualite.setDescription(actualiteDto.getDescription());
			actualite.setRefIdContenu(cc.getId());
			actualiteRepository.save(actualite);
			ma.setMedia(actualiteDto.getImage().getBytes());
			ma.setActualite(actualite);
			mediaRepository.save(ma);
			
		}
		
	}

}
