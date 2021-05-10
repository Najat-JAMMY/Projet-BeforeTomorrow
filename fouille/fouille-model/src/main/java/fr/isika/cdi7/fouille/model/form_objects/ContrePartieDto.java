package fr.isika.cdi7.fouille.model.form_objects;

import org.springframework.web.multipart.MultipartFile;

import fr.isika.cdi7.fouille.model.Collecte;

public class ContrePartieDto {

	private Long id;
	private int montant;
	private String titre;
	private String description;
	private MultipartFile image;
	private Collecte collecte;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public Collecte getCollecte() {
		return collecte;
	}

	public void setCollecte(Collecte collecte) {
		this.collecte = collecte;
	}

}
