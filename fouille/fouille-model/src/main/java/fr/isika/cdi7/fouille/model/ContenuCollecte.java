package fr.isika.cdi7.fouille.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class ContenuCollecte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nomProjet;
	@Lob
	private String descCourte;
	@Lob
	private String descLongue;

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "idCollecte")
	private Collecte collecte;
	@Lob
	private byte[] image;
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Long getId() {
		return id;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public String getDescCourte() {
		return descCourte;
	}

	public void setDescCourte(String descCourte) {
		this.descCourte = descCourte;
	}

	public String getDescLongue() {
		return descLongue;
	}

	public void setDescLongue(String descLongue) {
		this.descLongue = descLongue;
	}

	public Collecte getCollecte() {
		return collecte;
	}

	public void setCollecte(Collecte collecte) {
		this.collecte = collecte;
	}
}
