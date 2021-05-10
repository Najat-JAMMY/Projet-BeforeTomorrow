package fr.isika.cdi7.fouille.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Personne {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenom;
	private String numeroTel;

	@Enumerated(EnumType.STRING)
	private TypeRole role;
	
	@OneToOne(mappedBy="personne")
	private EspaceUtilisateur espaceUtilisateur;

	public Long getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getNumeroTel() {
		return numeroTel;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setNumeroTel(String numeroTel) {
		this.numeroTel = numeroTel;
	}

	public void setRole(TypeRole role) {
		this.role = role;
	}

	public TypeRole getRole() {
		return role;
	}


	
	



}
