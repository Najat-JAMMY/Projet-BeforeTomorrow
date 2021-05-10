package fr.isika.cdi7.fouille.model.form_objects;

import org.springframework.web.multipart.MultipartFile;

import fr.isika.cdi7.fouille.model.Chronologie;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Thematique;

public class RechercheForm {
	private String paysNomfr;
	private Thematique thematique;
	private Chronologie chronologie;
	private EtatProjet etatProjet;
	private MultipartFile image;
	private Long idContenuCollecte;
	//private String nomProjet;
	//private String descriptionCourteProjet;
	
	public Long getIdContenuCollecte() {
		return idContenuCollecte;
	}
	public void setIdContenuCollecte(Long idContenuCollecte) {
		this.idContenuCollecte = idContenuCollecte;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public String getPaysNomfr() {
		return paysNomfr;
	}
	public void setPaysNomfr(String paysNomfr) {
		this.paysNomfr = paysNomfr;
	}
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
	public EtatProjet getEtatProjet() {
		return etatProjet;
	}
	public void setEtatProjet(EtatProjet etatProjet) {
		this.etatProjet = etatProjet;
	}
	
}
