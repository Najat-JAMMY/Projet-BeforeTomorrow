package fr.isika.cdi7.fouille.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long idDestinataire;
	@Temporal(TemporalType.DATE)
	private Date date;
	private boolean lectureStaff;
	private boolean lectureUtilisateur;

	@Lob
	private String message;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Message_Personne"), name = "idPersonne")
	private EspaceUtilisateur personne;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Message_Discussion"), name = "idDiscussion")
	private Discussion discussion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdDestinataire() {
		return idDestinataire;
	}

	public void setIdDestinataire(Long idDestinataire) {
		this.idDestinataire = idDestinataire;
	}

	public boolean isLectureStaff() {
		return lectureStaff;
	}

	public void setLectureStaff(boolean lectureStaff) {
		this.lectureStaff = lectureStaff;
	}

	public boolean isLectureUtilisateur() {
		return lectureUtilisateur;
	}

	public void setLectureUtilisateur(boolean lectureUtilisateur) {
		this.lectureUtilisateur = lectureUtilisateur;
	}

	public EspaceUtilisateur getPersonne() {
		return personne;
	}

	public void setPersonne(EspaceUtilisateur personne) {
		this.personne = personne;
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
