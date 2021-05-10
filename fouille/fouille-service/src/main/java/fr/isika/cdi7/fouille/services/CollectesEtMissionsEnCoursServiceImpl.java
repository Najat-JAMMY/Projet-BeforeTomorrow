package fr.isika.cdi7.fouille.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Projet;

@Service
public class CollectesEtMissionsEnCoursServiceImpl implements CollectesEtMissionsEnCoursService {
	
	@Autowired
	private ConsulterProjetService consulterProjetService;

	@Override
	public List<Mission> getMissionsEnCoursSurDernierCycle() {
		List<Cycle> cyclesEnCours = tousLesDerniersCyclesEnCours(EtatProjet.EN_MISSION);
		return cyclesEnCours
				.stream()
				.map(Cycle::getMission)
				.collect(Collectors.toList());
	}
	
	
	@Override
	public List<Collecte> getCollectesEnCoursSurDernierCycle() {
		List<Cycle> cyclesEnCours = tousLesDerniersCyclesEnCours(EtatProjet.EN_CAMPAGNE_DE_COLLECTE);
		return cyclesEnCours
				.stream()
				.map(Cycle::getCollecte)
				.collect(Collectors.toList());
	}
	

	@Override

	public List<Cycle> tousLesDerniersCyclesEnCours(EtatProjet etatProjet) {
		List<Projet> projetsEnCollecte = consulterProjetService.findByProjetEtat(etatProjet);
		return projetsEnCollecte
				.stream()
				.map(p -> {
					Optional<Cycle> optionalCollecte = consulterProjetService.getDernierCycle(p.getId());
					return optionalCollecte.get();
				})
				.collect(Collectors.toList());
	}

}
