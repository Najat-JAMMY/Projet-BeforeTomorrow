package fr.isika.cdi7.fouille.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class EspaceUtilisateur {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String motDePasse;
	private byte[] avatar;

	@Temporal(TemporalType.DATE)
	private Date dateCreation;

	@OneToOne
	@JoinColumn(name = "idPersonne")
	private Personne personne;

	@OneToOne
	@JoinColumn(name = "idAdresse")
	private Adresse adresse;

	@OneToMany
	@JoinColumn(name = "idEspaceUtilisateur")
	public List<Favoris> listeDeFavoris;

	
	public Long getId() {
		return id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public String getEmail() {
		return email;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public Personne getPersonne() {
		return personne;
	}

	public Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	
	
	
	

}
