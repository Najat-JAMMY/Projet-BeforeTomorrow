package fr.isika.cdi7.fouille.model.form_objects;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import fr.isika.cdi7.fouille.model.Chronologie;
import fr.isika.cdi7.fouille.model.Thematique;

public class ProjetCollecteContenuForm {
	private Long projetId;
	private Long contenuCollecteId;
	private String nomProjet = "";
	private String descriptionCourte  = "";
	private String descriptionLong = "";
	
	@DateTimeFormat(iso = ISO.DATE)
	private Date dateDebut = new Date();
	
	private String nomPays_fr = "";
	private Double longitude = 0.0;
	private Double latitude = 0.0;
	private Long paysId;
	private MultipartFile image;
	private Thematique thematique = null;
	private Chronologie chronologie = null;
	
	


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

	public Long getContenuCollecteId() {
		return contenuCollecteId;
	}

	public void setContenuCollecteId(Long contenuCollecteId) {
		this.contenuCollecteId = contenuCollecteId;
	}
	
	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public String getNomPays_fr() {
		return nomPays_fr;
	}

	public void setNomPays_fr(String nomPays_fr) {
		this.nomPays_fr = nomPays_fr;
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

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}


	public Long getProjetId() {
		return projetId;
	}

	public void setProjetId(Long projetId) {
		this.projetId = projetId;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public String getDescriptionCourte() {
		return descriptionCourte;
	}

	public void setDescriptionCourte(String descriptionCourte) {
		this.descriptionCourte = descriptionCourte;
	}

	public String getDescriptionLong() {
		return descriptionLong;
	}

	public void setDescriptionLong(String descriptionlong) {
		this.descriptionLong = descriptionlong;
	}

	public Long getPaysId() {
		return paysId;
	}

	public void setPaysId(Long paysId) {
		this.paysId = paysId;
	}
	
	

}
