package fr.isika.cdi7.fouille.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Statistiques {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer nbVisiteurs;
	private Integer nbContributions;
	private Integer nbProjetsLances;
	private Integer nbCampagnesReussies;
	@Temporal(TemporalType.DATE)
	private Date date;

}
