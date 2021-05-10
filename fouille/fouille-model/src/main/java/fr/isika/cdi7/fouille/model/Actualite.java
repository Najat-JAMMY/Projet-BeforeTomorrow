package fr.isika.cdi7.fouille.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Actualite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date date;
	@Lob
	private String description;
	@Enumerated(EnumType.STRING)
	private TypeContenu typeContenu;

	private Long refIdContenu;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idActualite")
	private List<Commentaire> commentaires = new LinkedList<>();

	public Long getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TypeContenu getContenu() {
		return typeContenu;
	}

	public void setContenu(TypeContenu contenu) {
		this.typeContenu = contenu;
	}

	public Long getRefIdContenu() {
		return refIdContenu;
	}

	public void setRefIdContenu(Long refIdContenu) {
		this.refIdContenu = refIdContenu;
	}

	public List<Commentaire> getCommentaires() {
		return commentaires;
	}

	public void setCommentaires(List<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}
	
	

}
