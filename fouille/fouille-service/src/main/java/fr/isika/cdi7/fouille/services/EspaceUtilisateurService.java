package fr.isika.cdi7.fouille.services;

import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Personne;

public interface EspaceUtilisateurService {

	
	public EspaceUtilisateur save(EspaceUtilisateur espaceUtilisateur);
	public	Personne save(Personne personne);
	public EspaceUtilisateur findByEmail(String email); //axelle
	public EspaceUtilisateur getEspaceUserParIdPersonne(Long idPersonne);
}
