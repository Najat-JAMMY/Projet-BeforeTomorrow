package fr.isika.cdi7.fouille.model;

import org.springframework.security.core.GrantedAuthority;

public enum TypeRole implements GrantedAuthority {
	ROLE_UTILISATEUR("UTILISATEUR"),
	ROLE_GESTIONNAIRE_PROJET("GESTIONNAIRE PROJET"),
	ROLE_CHARGE_COM("CHARGE COM"),
	ROLE_DIRECTEUR("DIRECTEUR");

	
	
	private String role;
	
	TypeRole(String string) {
		this.role = string;
	}

	
	public String getRole() {
		return role;
	}



	@Override
	public String getAuthority() {

		return name();
	}

}
