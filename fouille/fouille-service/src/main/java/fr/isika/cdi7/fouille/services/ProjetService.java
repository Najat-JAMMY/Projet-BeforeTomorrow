
package fr.isika.cdi7.fouille.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Don;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Favoris;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.form_objects.ProjetCollecteContenuForm;
import fr.isika.cdi7.fouille.model.form_objects.ProjetForm;

public interface ProjetService {
	public void editerProjet(ProjetCollecteContenuForm projetCollecteContenueForm) throws IOException;

	public Projet findById(long id);

	public Projet save(Projet projet);

	Optional<Projet> getProjetParId(Long id);

	void add(Projet projet);

	void addProjet(Personne porteur, ProjetForm projetForm);

	List<Projet> listDeProjetDepose(EtatProjet etat);

	public Projet getProjetParIdPersonne(Long idPersonne);

	public List<Favoris> getProjetFavoris(Long id);

	public List<Don> getDonDunePersonne(Long id);

	public List<Cycle> getCycleByListFavoris(List<Favoris> listeDeProjetEnFavoris);

	public void MiseEnLigneDuneMission(Long idCycle);

	public List<Projet> findAll();

}
