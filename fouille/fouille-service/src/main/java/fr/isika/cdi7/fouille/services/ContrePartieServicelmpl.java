package fr.isika.cdi7.fouille.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.ContrePartieRepository;
import fr.isika.cdi7.fouille.dao.CycleRepository;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContrePartie;
import fr.isika.cdi7.fouille.model.form_objects.ContrePartieDto;

@Service
public class ContrePartieServicelmpl implements ContrePartieService {
	@Autowired
	private ContrePartieRepository contrePartieRepository;

	@Override
	public List<ContrePartie> getContrepartie(Long collecteId) {
		// TODO Auto-generated method stub
		return contrePartieRepository.getListeContrePartieByCollecteId((long) collecteId);
	}

	@Override
	public ContrePartie save(ContrePartie contrePartie) {
		return contrePartieRepository.save(contrePartie);

	}

	@Override
	public Optional<ContrePartie> findById(Long contrepartieId) {
		
		return contrePartieRepository.findById(contrepartieId);
	}

	@Override
	public void saveContrePartie(ContrePartieDto contrePartieDto, Collecte collecte) throws IOException {
		
	ContrePartie cp = new ContrePartie();
	cp.setCollecte(collecte);
	cp.setDescription(contrePartieDto.getDescription());
	cp.setImage(contrePartieDto.getImage().getBytes());
	cp.setMontant(contrePartieDto.getMontant());
	cp.setTitre(contrePartieDto.getTitre());
	contrePartieRepository.save(cp);
		
	}

	@Override
	public void deleteContrePartie(Long idContrePartie) {
		ContrePartie cp = contrePartieRepository.findById(idContrePartie).get();
		contrePartieRepository.delete(cp);
		
	}

}
