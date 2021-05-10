package fr.isika.cdi7.fouille.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContrePartie;
import fr.isika.cdi7.fouille.model.form_objects.ContrePartieDto;

@Service
public interface ContrePartieService {
	List<ContrePartie> getContrepartie(Long collecteId);

	ContrePartie save(ContrePartie contrePartie);

	Optional<ContrePartie> findById(Long contrepartieId);

	void saveContrePartie(ContrePartieDto contrePartieDto, Collecte collecte) throws IOException;

	void deleteContrePartie(Long idContrePartie);
}
