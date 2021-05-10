package fr.isika.cdi7.fouille.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Don {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer montant;
	private boolean anonyme;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Don_Collecte"), name = "idCollecte")
	private Collecte collecte;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Don_ContrePartie"), name = "idContrePartie")
	private ContrePartie contrePartie;

	@OneToOne
	@JoinColumn(name = "idPersonne")
	private Personne personne;
	
	@Temporal(TemporalType.DATE)
	private Date dateDon;

	public Long getIdDon() {
		return id;
	}

	public Integer getMontant() {
		return montant;
	}

	public void setMontant(Integer montant) {
		this.montant = montant;
	}

	public boolean isAnonyme() {
		return anonyme;
	}

	public void setAnonyme(boolean anonyme) {
		this.anonyme = anonyme;
	}

	public Collecte getCollecte() {
		return collecte;
	}

	public void setCollecte(Collecte collecte) {
		this.collecte = collecte;
	}

	public Personne getPersonne() {
		return personne;
	}

	public void setPersonne(Personne personne) {
		this.personne = personne;
	}

	public Date getDateDon() {
		return dateDon;
	}
	
	public String getMonthDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateDon);
		return new SimpleDateFormat("MMM").format(cal.get(Calendar.MONTH));
		
	}
//	public List<String> getMonthDate() {
//		Calendar cal = Calendar.getInstance();;
//		List<String> listM = new ArrayList();
//		 	List<String> monthNamesList = new ArrayList();
//		 	monthNamesList.add("January");
//		 	monthNamesList.add("Fevrier");
//		 	monthNamesList.add("Mars");
//		 	
//		 	for (String string : monthNamesList) {
//		 		cal.setTime(this.dateDon);
//				 if (new SimpleDateFormat("MMM").format(cal.get(Calendar.MONTH)) == string) {
//					 
//					 listM.add(string);
//					 
//				 }
//			}
//
//		 	return listM;
//		
//	}


	public void setDateDon(Date dateDon) {
		this.dateDon = dateDon;
	}

	public ContrePartie getContrePartie() {
		return contrePartie;
	}

	public void setContrePartie(ContrePartie contrePartie) {
		this.contrePartie = contrePartie;
	}

}
