package fr.isika.cdi7.fouille.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class ContenuMission {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nomMission;

	@Lob
	private String descCourteMission;
	@Lob
	private String descLongueMission;

	@OneToOne
	@JoinColumn(name = "idMission")
	private Mission mission;
	@Lob
	private byte[] image;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getNomMission() {
		return nomMission;
	}

	public void setNomMission(String nomMission) {
		this.nomMission = nomMission;
	}

	public String getDescCourteMission() {
		return descCourteMission;
	}

	public void setDescCourteMission(String descCourteMission) {
		this.descCourteMission = descCourteMission;
	}

	public String getDescLongueMission() {
		return descLongueMission;
	}

	public void setDescLongueMission(String descLongueMission) {
		this.descLongueMission = descLongueMission;
	}

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

}
