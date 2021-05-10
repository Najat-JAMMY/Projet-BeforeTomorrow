package fr.isika.cdi7.fouille.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Commentaire {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 100)
	private String textCommentaire;
	private Long refComParent;
	@Temporal(TemporalType.DATE)
	private Date dateCommentaire;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Commentaire_EspaceUtilisateur"), name = "idEspaceUtilisateur")
	private EspaceUtilisateur espaceUtilisateur;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Commentaire_projet"), name = "idProjet")
	private Projet projet;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Commentaire_Actualite"), name = "idActualite")
	private Actualite actualite;

	public Long getId() {
		return id;
	}
	

	public void setTextCommentaire(String textCommentaire) {
		this.textCommentaire = textCommentaire;
	}


	public String getTextCommentaire() {
		return textCommentaire;
	}
	

	public void setDate(Date date) {
		this.dateCommentaire = date;
	}


	public Date getDateCommentaire() {
		return dateCommentaire;
	}

	public EspaceUtilisateur getEspaceUtilisateur() {
		return espaceUtilisateur;
	}

	public Projet getProjet() {
		return projet;
	}


	public void setEspaceUtilisateur(EspaceUtilisateur espaceUtilisateur) {
		this.espaceUtilisateur = espaceUtilisateur;
	}


	public void setProjet(Projet projet) {
		this.projet = projet;
	}


	public Actualite getActualite() {
		return actualite;
	}


	public void setActualite(Actualite actualite) {
		this.actualite = actualite;
	}
	
	
	
	

}
