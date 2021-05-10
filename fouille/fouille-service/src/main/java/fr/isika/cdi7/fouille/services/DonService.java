package fr.isika.cdi7.fouille.services;

import java.util.List;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContrePartie;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Don;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;

public interface DonService {

	Don save(Don don);

	List<ContrePartie> getListContrePartieTest(Integer montant, Long idCollecte);

	Personne getUtilisateur(Long i);

	Projet getProjet(Long i);

	Collecte getCollecteId(Long idCollecte);

	Cycle getCycleParProjetId(Long idProjet);

	Collecte getCollecteIdCycle(Long id);

	void IncrementationSommeCollecte(Don donSave, Collecte collecte);

}
