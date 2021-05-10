package fr.isika.cdi7.fouille.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Cycle implements Comparable<Cycle> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Saison saison;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date date;

	@Enumerated(EnumType.STRING)
	private Thematique thematique;

	@Enumerated(EnumType.STRING)
	private Chronologie chronologie;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Cycle_Projet"), name = "idProjet")
	private Projet projet;

	@OneToOne(mappedBy = "cycle")
	private Mission mission;

	@OneToOne(mappedBy = "cycle")
	private Collecte collecte;

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collecte getCollecte() {
		return collecte;
	}

	public void setCollecte(Collecte collecte) {
		this.collecte = collecte;
	}

	public Saison getSaison() {
		return saison;
	}

	public void setSaison(Saison saison) {
		this.saison = saison;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Thematique getThematique() {
		return thematique;
	}

	public void setThematique(Thematique thematique) {
		this.thematique = thematique;
	}

	public Chronologie getChronologie() {
		return chronologie;
	}

	public void setChronologie(Chronologie chronologie) {
		this.chronologie = chronologie;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}

	public Long getId() {
		return id;
	}


	@Override
	public int compareTo(Cycle o) {
		return this.id.compareTo(o.id);
	}


}
