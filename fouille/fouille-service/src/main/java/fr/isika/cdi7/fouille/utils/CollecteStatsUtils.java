package fr.isika.cdi7.fouille.utils;

import java.util.Optional;

import fr.isika.cdi7.fouille.model.Collecte;

public class CollecteStatsUtils {

	private static final int POURCENTAGE_COLLECTE_DEFAULT = 0;

	public static Integer pourcentageMontantCollecte(Optional<Collecte> collecte) {
		return collecte.isPresent() && collecte.get().getMontantCollecte() != null
				? calculerPourcentage(collecte.get().getMontantCollecte(), collecte.get().getMontantDemande())
				: POURCENTAGE_COLLECTE_DEFAULT;
	}
	
	private static int calculerPourcentage(Integer montantCollecte, Integer montantDemande) {
		return montantCollecte * 100 / montantDemande;
	}
}
