package fr.isika.cdi7.fouille.model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private TypeDocument typeDocument;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] ficher;
}
