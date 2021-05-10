package fr.isika.cdi7.fouille.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
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
public class Collecte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	@Temporal(TemporalType.DATE)
	private Date dateCloture;
	private Integer duree;
	private Integer montantDemande;
	private Integer montantCollecte;

	@OneToOne
	@JoinColumn(name = "idCycle")
	private Cycle cycle;

	@OneToMany
	@JoinColumn(name = "idCollecte")
	public List<Don> listeDeDons = new LinkedList<>();

	@OneToOne(mappedBy="collecte")
	private ContenuCollecte contenuCollecte;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idCollecte")
	public List<ContrePartie> listeDeContreParties = new LinkedList<>();

	public Long getId() {
		return id;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateCloture() {
		return dateCloture;
	}

	public void setDateCloture(Date dateCloture) {
		this.dateCloture = dateCloture;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	public Integer getMontantDemande() {
		return montantDemande;
	}

	public void setMontantDemande(Integer montantDemande) {
		this.montantDemande = montantDemande;
	}

	public Integer getMontantCollecte() {
		return montantCollecte;
	}

	public void setMontantCollecte(Integer montantCollecte) {
		this.montantCollecte = montantCollecte;
	}

	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	public List<Don> getListeDeDons() {
		return listeDeDons;
	}

	public void setListeDeDons(List<Don> listeDeDons) {
		this.listeDeDons = listeDeDons;
	}

	public List<ContrePartie> getListeDeContreParties() {
		return listeDeContreParties;
	}

	public void setListeDeContreParties(List<ContrePartie> listeDeContreParties) {
		this.listeDeContreParties = listeDeContreParties;
	}

	public ContenuCollecte getContenuCollecte() {
		return contenuCollecte;
	}

	public void setContenuCollecte(ContenuCollecte contenuCollecte) {
		this.contenuCollecte = contenuCollecte;
	}

	public void addContrePartie(ContrePartie contrePartie) {
		this.listeDeContreParties.add(contrePartie);
		contrePartie.setCollecte(this);
	}

}
