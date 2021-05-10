package fr.isika.cdi7.fouille.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.CollecteRepository;
import fr.isika.cdi7.fouille.dao.ContrePartieRepository;
import fr.isika.cdi7.fouille.dao.CycleRepository;
import fr.isika.cdi7.fouille.dao.DonRepository;
import fr.isika.cdi7.fouille.dao.PersonneRepository;
import fr.isika.cdi7.fouille.dao.ProjetRepository;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContrePartie;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Don;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;

@Service
public class DonServiceImpl implements DonService {

	@Autowired
	private DonRepository DonRepository;

	@Autowired
	private ContrePartieRepository contrePartieRepository;

	@Autowired
	private ProjetRepository projetRepository;

	@Autowired
	private PersonneRepository personneRepository;

	@Autowired
	private CycleRepository cycleRepository;

	@Autowired
	private CollecteRepository collecteRepository;

	@Override
	public Don save(Don don) {
		return DonRepository.save(don);

	}

	@Override
	public List<ContrePartie> getListContrePartieTest(Integer montant, Long idCollecte) {
		return contrePartieRepository.getListContrePartieParMontantTest(montant, idCollecte);
	}

	@Override
	public Personne getUtilisateur(Long i) {
		Optional<Personne> pers = personneRepository.findById(i);
		return pers.get();
	}

	@Override
	public Projet getProjet(Long i) {
		Projet projet = projetRepository.findById(i);
		return projet;
	}
	//
	// @Override
	// public Collecte getCollecte(Long idCycle) {
	// Optional<Collecte> collecte = collecteRepository.findById(idCycle);
	// return collecte.get();
	//
	// }

	@Override
	public Collecte getCollecteId(Long idCollecte) {

		Optional<Collecte> collecte = collecteRepository.findById(idCollecte);
		return collecte.get();

	}

	@Override
	public Cycle getCycleParProjetId(Long idProjet) {

		return cycleRepository.getCycleIdProjet(idProjet);
	}

	@Override
	public Collecte getCollecteIdCycle(Long idCycle) {
		return collecteRepository.getCollecteIdCycle(idCycle);

	}

	@Override
	public void IncrementationSommeCollecte(Don donSave, Collecte collecte) {

		
	//	collecte.setMontantCollecte( collecte.getMontantCollecte() == null ? 0 : collecte.getMontantCollecte() + donSave.getMontant());
	//	collecteRepository.save(collecte);

		Optional<Integer> montant = Optional.ofNullable(collecte.getMontantCollecte());
		if (montant.isPresent()) {
			collecte.setMontantCollecte(collecte.getMontantCollecte() + donSave.getMontant());
			collecteRepository.save(collecte);
		} else {
			collecte.setMontantCollecte(donSave.getMontant());
			collecteRepository.save(collecte);
		}

	}

}
