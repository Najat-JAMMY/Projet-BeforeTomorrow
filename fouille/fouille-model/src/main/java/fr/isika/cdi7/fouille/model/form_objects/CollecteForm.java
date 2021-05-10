package fr.isika.cdi7.fouille.model.form_objects;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class CollecteForm {
	
	private Integer duree;
	private Integer montant;
	private Integer montantCP;
	private String descriptionCP;
	private byte[] imageCP;
	private String titreCP;

	public Integer getDuree() {
		return duree;
	}
	public void setDuree(Integer duree) {
		this.duree = duree;
	}
	public Integer getMontant() {
		return montant;
	}
	public void setMontant(Integer montant) {
		this.montant = montant;
	}
	public Integer getMontantCP() {
		return montantCP;
	}
	public void setMontantCP(Integer montantCP) {
		this.montantCP = montantCP;
	}
	public String getDescriptionCP() {
		return descriptionCP;
	}
	public void setDescriptionCP(String descriptionCP) {
		this.descriptionCP = descriptionCP;
	}

	public String getTitreCP() {
		return titreCP;
	}
	public void setTitreCP(String titreCP) {
		this.titreCP = titreCP;
	}
	public byte[] getImageCP() {
		return imageCP;
	}
	public void setImageCP(byte[] imageCP) {
		this.imageCP = imageCP;
	}
	
	
	
	
	
	

}
