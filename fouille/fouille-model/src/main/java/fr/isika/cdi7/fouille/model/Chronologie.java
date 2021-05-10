package fr.isika.cdi7.fouille.model;

public enum Chronologie {


	PREHISTOIRE("PREHISTOIRE"),
	PALEOLITHIQUE("PALEOLITHIQUE"),
	NEOLITHIQUE("NEOLITHIQUE"),
	AGE_DU_BRONZE("AGE DU BRONZE"),
	AGE_DU_FER("AGE DU FER");


	private String chronologie;

	Chronologie(String string) {
		this.chronologie = string;
	}

	public String getChronologie() {
		return chronologie;
	}

	public void setChronologie(String chronologie) {
		this.chronologie = chronologie;
	}
	
}
