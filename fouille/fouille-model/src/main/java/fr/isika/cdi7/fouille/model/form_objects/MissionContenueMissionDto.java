package fr.isika.cdi7.fouille.model.form_objects;


import org.springframework.web.multipart.MultipartFile;

public class MissionContenueMissionDto {
	
	private String descriptionCourteMission;
	private String descriptionLongMission;
	private String nomMission;
	private MultipartFile image;
	private Integer duree;
		
	
	public Integer getDuree() {
		return duree;
	}
	public void setDuree(Integer duree) {
		this.duree = duree;
	}

	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public String getNomMission() {
		return nomMission;
	}
	public void setNomMission(String nomMission) {
		this.nomMission = nomMission;
	}
	public String getDescriptionCourteMission() {
		return descriptionCourteMission;
	}
	public void setDescriptionCourteMission(String descriptionCourteMission) {
		this.descriptionCourteMission = descriptionCourteMission;
	}
	public String getDescriptionLongMission() {
		return descriptionLongMission;
	}
	public void setDescriptionLongMission(String descriptionLongMission) {
		this.descriptionLongMission = descriptionLongMission;
	}




}
