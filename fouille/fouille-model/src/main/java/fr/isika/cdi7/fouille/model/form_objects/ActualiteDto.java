package fr.isika.cdi7.fouille.model.form_objects;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.multipart.MultipartFile;

import fr.isika.cdi7.fouille.model.TypeContenu;

public class ActualiteDto {
	private Long projetId;


	private MultipartFile image;

	private Long refIdContenu;
	private String description;
	@DateTimeFormat(iso = ISO.DATE)
	private Date date;
	@Enumerated(EnumType.STRING)
	private TypeContenu typeContenu;
	

	public Long getProjetId() {
		return projetId;
	}
	public void setProjetId(Long projetId) {
		this.projetId = projetId;
	}

	public Long getRefIdContenu() {
		return refIdContenu;
	}
	public void setRefIdContenu(Long refIdContenu) {
		this.refIdContenu = refIdContenu;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public TypeContenu getTypeContenu() {
		return typeContenu;
	}
	public void setTypeContenu(TypeContenu typeContenu) {
		this.typeContenu = typeContenu;
	}
}
