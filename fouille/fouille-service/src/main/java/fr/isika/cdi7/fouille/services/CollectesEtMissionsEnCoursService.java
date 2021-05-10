package fr.isika.cdi7.fouille.services;

import java.util.Collection;
import java.util.List;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Mission;

public interface CollectesEtMissionsEnCoursService {

	List<Collecte> getCollectesEnCoursSurDernierCycle();

	List<Mission> getMissionsEnCoursSurDernierCycle();

//	List<Cycle> tousLesDerniersCyclesEnCours(EtatProjet etatProjet);

	Collection<? extends Cycle> tousLesDerniersCyclesEnCours(EtatProjet enCampagneDeCollecte);

	

}
