package fr.isika.cdi7.fouille.model;

public enum Thematique {

	ARCHEOLOGIE_FUNERAIRE("ARCHEOLOGIE FUNERAIRE"),
	ARCHEOLOGIE_SOUS_MARINE("ARCHEOLOGIE SOUS MARINE"),
	ARCHITECTURE_URBAINE ("ARCHITECTURE URBAINE"),
	PALAIS ("PALAIS"),
	GRANDES_CITES ("GRANDES CITES");

	private String thematique;
	Thematique(String string) {
		this.thematique = string;
	}
	
	public String getThematique() {
		return thematique;
	}


}
