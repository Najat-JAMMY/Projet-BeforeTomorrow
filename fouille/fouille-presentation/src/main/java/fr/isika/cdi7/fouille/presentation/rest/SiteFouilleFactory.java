package fr.isika.cdi7.fouille.presentation.rest;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Mission;

public interface SiteFouilleFactory {
	
	SiteFouille creerPointLocalisationCollecte(Collecte collecte);

	SiteFouille creerPointLocalisationMission(Mission mission);

}
