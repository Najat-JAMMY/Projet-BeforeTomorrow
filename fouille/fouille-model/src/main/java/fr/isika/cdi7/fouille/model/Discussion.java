package fr.isika.cdi7.fouille.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Discussion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String sujet;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Discussion_Utilisateur"), name = "idUtilisateur")
	private EspaceUtilisateur utilisateur;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "FK_Discussion_Staff"), name = "idStaff")
	private EspaceUtilisateur staff;

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "idDiscussion") 
	private Set<Message> messages;

	
	
	
	public Long getId() {
		return id;
	}

	public String getSujet() {
		return sujet;
	}

	public void setSujet(String sujet) {
		this.sujet = sujet;
	}

	public EspaceUtilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(EspaceUtilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public EspaceUtilisateur getStaff() {
		return staff;
	}

	public void setStaff(EspaceUtilisateur staff) {
		this.staff = staff;
	}

	public Set<Message> getMessages() {
		return messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	
	
}
