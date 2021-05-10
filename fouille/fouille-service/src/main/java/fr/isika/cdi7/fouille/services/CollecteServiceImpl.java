package fr.isika.cdi7.fouille.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.CollecteRepository;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.form_objects.CollecteForm;

@Service
public class CollecteServiceImpl implements CollecteService {

	@Autowired
	private CollecteRepository collecteRepository;

	@Override
	public Collecte save(Collecte collecte) {
		return collecteRepository.save(collecte);
	}

	public Collecte findById(long id) {
		return collecteRepository.findById(id);
	}

	@Override
	public void saveCollecte(CollecteForm form, Collecte collecte) {
		
		collecte.setDuree(form.getDuree());
		collecte.setMontantDemande(form.getMontant());
		collecteRepository.save(collecte);
		
	}
}
