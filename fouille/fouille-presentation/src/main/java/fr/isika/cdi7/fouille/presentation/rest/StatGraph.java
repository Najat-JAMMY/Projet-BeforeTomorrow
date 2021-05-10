package fr.isika.cdi7.fouille.presentation.rest;

public class StatGraph {

	private String country;
	private Long visits;
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getVisits() {
		return visits;
	}
	public void setVisits(Long visits) {
		this.visits = visits;
	}

	public static StatGraph create(String country, Long visits) {
		StatGraph statGraph = new StatGraph();
		statGraph.setCountry(country);
		statGraph.setVisits(visits);
		return statGraph;
	}

}
