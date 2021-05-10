package fr.isika.cdi7.fouille.presentation.rest;

public class StatProjet {

	private String country;
	private Long litres;
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getLitres() {
		return litres;
	}
	public void setLitres(Long litres) {
		this.litres = litres;
	}

	
	public static StatProjet create(String country, Long litres) {
		StatProjet StatProjet = new StatProjet();
		StatProjet.setCountry(country);
		StatProjet.setLitres(litres);
		return StatProjet;
	}
	
	
	

	

}