package fr.isika.cdi7.fouille.presentation.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.presentation.ProjetController;
import fr.isika.cdi7.fouille.services.CollectesEtMissionsEnCoursService;

@RestController
public class SiteFouilleRestController {

	private static final Logger LOGGER = Logger.getLogger(ProjetController.class.getSimpleName());

	@Autowired
	private CollectesEtMissionsEnCoursService service;

	@Autowired
	private SiteFouilleFactory siteFactory;

	public SiteFouilleRestController() {
		LOGGER.info("SiteFouilleRestController instance created ...");
	}

	@GetMapping("/map/coordsSitesFouille")
	public List<SiteFouille> all() {

		List<SiteFouille> listeSites = new ArrayList<SiteFouille>();

		List<Collecte> collectes = service.getCollectesEnCoursSurDernierCycle();
		List<Mission> missions = service.getMissionsEnCoursSurDernierCycle();
		listeSites.addAll(createListSitesCollecte(collectes));
		listeSites.addAll(createListSitesMission(missions));

		return listeSites;
	}

	private List<SiteFouille> createListSitesCollecte(List<Collecte> collectes) {
		return collectes.stream().map(c -> siteFactory.creerPointLocalisationCollecte(c)).collect(Collectors.toList());
	}

	private List<SiteFouille> createListSitesMission(List<Mission> missions) {
		return missions.stream().map(c -> siteFactory.creerPointLocalisationMission(c)).collect(Collectors.toList());
	}

}
