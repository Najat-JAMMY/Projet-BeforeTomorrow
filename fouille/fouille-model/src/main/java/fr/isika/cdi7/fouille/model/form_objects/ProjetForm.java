package fr.isika.cdi7.fouille.model.form_objects;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class ProjetForm {

	private String titre;
	private String rattachement;
	private String montantEnvisage;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateEnvisagee;
	private String description;
	private String pays;

	public ProjetForm() {

	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getRattachement() {
		return rattachement;
	}

	public void setRattachement(String rattachement) {
		this.rattachement = rattachement;
	}

	public String getMontantEnvisage() {
		return montantEnvisage;
	}

	public void setMontantEnvisage(String montantEnvisage) {
		this.montantEnvisage = montantEnvisage;
	}

	public Date getDateEnvisagee() {
		return dateEnvisagee;
	}

	public void setDateEnvisagee(Date dateEnvisagee) {
		this.dateEnvisagee = dateEnvisagee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

}
