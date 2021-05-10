package fr.isika.cdi7.fouille.services;

import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.form_objects.CollecteForm;

@Service
public interface CollecteService {

	public Collecte save(Collecte collecte);

	public Collecte findById(long id);

	public void saveCollecte(CollecteForm form, Collecte collecte);
}
