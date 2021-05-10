package fr.isika.cdi7.fouille.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Pays {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomPays_en;

	public Pays() {

	}

	public String getNomPays_fr() {
		return nomPays_fr;
	}

	private String nomPays_fr;

	public void setNomPays_fr(String nomPays_fr) {
		this.nomPays_fr = nomPays_fr;
	}

	public Long getId() {
		return id;
	}

}
