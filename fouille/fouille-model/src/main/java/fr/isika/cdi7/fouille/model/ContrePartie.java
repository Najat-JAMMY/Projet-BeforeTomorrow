package fr.isika.cdi7.fouille.model;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.web.multipart.MultipartFile;

@Entity
public class ContrePartie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int montant;
	private String titre;

	@Lob
	private String description;

	@Lob
	private byte[] image;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_collecte_contrePartie"), name = "idCollecte")
	private Collecte collecte;

	public Long getId() {
		return id;
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Collecte getCollecte() {
		return collecte;
	}

	public void setCollecte(Collecte collecte) {
		this.collecte = collecte;
	}



}
