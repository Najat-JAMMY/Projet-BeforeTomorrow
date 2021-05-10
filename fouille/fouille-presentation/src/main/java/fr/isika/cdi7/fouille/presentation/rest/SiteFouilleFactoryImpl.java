package fr.isika.cdi7.fouille.presentation.rest;

import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.model.Adresse;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Mission;

@Service
public class SiteFouilleFactoryImpl implements SiteFouilleFactory {

	@Override
	public SiteFouille creerPointLocalisationCollecte(Collecte collecte) {
		SiteFouille site = new SiteFouille();
		site.setLatitude(getLocalisationCollecte(collecte).getLatitude());
		site.setLongitude(getLocalisationCollecte(collecte).getLongitude());
		site.setTitle(collecte.getContenuCollecte().getNomProjet());
		site.setColor("#009F4D");
		site.setUrl("");
		return site;
	}

	@Override
	public SiteFouille creerPointLocalisationMission(Mission mission) {
		SiteFouille site = new SiteFouille();
		site.setLatitude(mission.getLocalisation().getLatitude());
		site.setLongitude(mission.getLocalisation().getLongitude());
		site.setTitle(mission.getContenuMission().getNomMission());
		site.setColor("#4C12A1");
		site.setUrl("");
		return site;
	}

	private Adresse getLocalisationCollecte(Collecte collecte) {
		return collecte.getCycle().getMission().getLocalisation();
	}

}
