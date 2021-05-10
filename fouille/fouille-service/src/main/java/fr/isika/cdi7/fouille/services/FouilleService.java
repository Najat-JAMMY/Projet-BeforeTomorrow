package fr.isika.cdi7.fouille.services;

import java.util.List;

import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.Pays;
import fr.isika.cdi7.fouille.model.Projet;

public interface FouilleService {
	public List<Actualite> listActualite();

	public Actualite saveActualite(Actualite actualite);

	public List<Projet> listProjet();

	public Projet saveProjet(Projet projet);

	
	public List<Pays> getlistPays();



}
