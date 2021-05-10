package fr.isika.cdi7.fouille.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Adresse {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String rue;
	private String codePostal;
	private String ville;
	private Double longitude;
	private Double latitude;

	public Long getId() {
		return id;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(Pays pays) {
		this.pays = pays;
	}

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Adresse_Pays"), name = "idPays")
	private Pays pays;
	
	public Adresse() {
		super();
	}

	public Adresse(Double longitude, Double latitude, Pays pays) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.pays = pays;
	}
}
