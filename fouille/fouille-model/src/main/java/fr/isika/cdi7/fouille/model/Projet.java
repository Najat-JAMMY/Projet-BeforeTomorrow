package fr.isika.cdi7.fouille.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Projet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date dateDepot;

	@Enumerated(EnumType.STRING)
	@ColumnDefault("'EN_ATTENTE_DE_TRAITEMENT'")
	private EtatProjet etat;

	private String titre;

	private String rattachement;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "projet", fetch = FetchType.EAGER)
	private List<Cycle> cycle;

	@Lob
	private String description;

	@Temporal(TemporalType.DATE)
	private Date dateEnvisagee;

	private Integer montantEnvisage;
	private String pays;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_PROJET_PERSONNEPorteur"), name = "idPorteurDeProjet")
	private Personne porteur;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_PROJET_PERSONNEGestionnaire"), name = "idGestionnaire")
	private Personne gestionnaire;

	@OneToOne
	private DossierAdministratif dossierAdministratif;

	public DossierAdministratif getDossierAdministratif() {
		return dossierAdministratif;
	}

	public void setDossierAdministratif(DossierAdministratif dossierAdministratif) {
		this.dossierAdministratif = dossierAdministratif;
	}

	public List<Cycle> getCycle() {
		return cycle;
	}

	public void setCycle(List<Cycle> cycle) {
		this.cycle = cycle;
	}

	public Long getId() {
		return id;
	}

	public Date getDateDepot() {
		return dateDepot;
	}

	public void setDateDepot(Date dateDepot) {
		this.dateDepot = dateDepot;
	}

	public EtatProjet getEtat() {
		return etat;
	}

	public void setEtat(EtatProjet etat) {
		this.etat = etat;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Projet withProjetTitre(String titre) {
		this.titre = titre;
		return this;
	}

	public Projet withEtat(EtatProjet etat) {
		this.etat = etat;
		return this;
	}

	public String getRattachement() {
		return rattachement;
	}

	public Date getDateEnvisagee() {
		return dateEnvisagee;
	}

	public Integer getMontantEnvisage() {
		return montantEnvisage;
	}

	public void setDateEnvisagee(Date dateEnvisagee) {
		this.dateEnvisagee = dateEnvisagee;
	}

	public String getPays() {
		return pays;
	}

	public String getDescription() {
		return description;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public Personne getPorteur() {
		return porteur;
	}

	public void setPorteur(Personne porteur) {
		this.porteur = porteur;
	}

	public void setGestionnaire(Personne gestionnaire) {
		this.gestionnaire = gestionnaire;
	}

	public Personne getGestionnaire() {
		return gestionnaire;
	}

	public void setRattachement(String rattachement) {
		this.rattachement = rattachement;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setMontantEnvisage(Integer montantEnvisage) {
		this.montantEnvisage = montantEnvisage;
	}

}
