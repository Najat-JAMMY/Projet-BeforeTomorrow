package fr.isika.cdi7.fouille.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Mission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date dateDebut;
	@Temporal(TemporalType.DATE)
	private Date dateFin;
	private Integer duree;
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "idLocalisation")
	private Adresse localisation;
	@OneToOne
	@JoinColumn(name = "idCycle")
	private Cycle cycle;

	@OneToOne(mappedBy = "mission")
	private ContenuMission contenuMission;

	public Long getIdMission() {
		return id;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Integer getDuree() {
		return duree;
	}

	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	public Cycle getCycle() {
		return cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	public Adresse getLocalisation() {
		return localisation;
	}

	public void setLocalisation(Adresse localisation) {
		this.localisation = localisation;
	}

	public ContenuMission getContenuMission() {
		return contenuMission;
	}

	public void setContenuMission(ContenuMission contenuMission) {
		this.contenuMission = contenuMission;
	}

}
