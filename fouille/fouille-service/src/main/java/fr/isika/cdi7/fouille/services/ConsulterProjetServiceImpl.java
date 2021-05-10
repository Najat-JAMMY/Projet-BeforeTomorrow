package fr.isika.cdi7.fouille.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


import java.util.Collections;

import java.util.Comparator;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.ActualiteRepository;
import fr.isika.cdi7.fouille.dao.CollecteRepository;
import fr.isika.cdi7.fouille.dao.CommentaireRepository;
import fr.isika.cdi7.fouille.dao.ContenuCollecteRepository;
import fr.isika.cdi7.fouille.dao.ContenuMissionRepository;
import fr.isika.cdi7.fouille.dao.ContrePartieRepository;
import fr.isika.cdi7.fouille.dao.CycleRepository;
import fr.isika.cdi7.fouille.dao.MediaRepository;
import fr.isika.cdi7.fouille.dao.MissionRepository;
import fr.isika.cdi7.fouille.dao.ProjetRepository;
import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Commentaire;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.ContrePartie;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Don;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.MediaActualite;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Pays;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.Saison;
import fr.isika.cdi7.fouille.model.TypeContenu;
import fr.isika.cdi7.fouille.utils.CollecteStatsUtils;

@Service
public class ConsulterProjetServiceImpl implements ConsulterProjetService {

	@Autowired
	private ProjetRepository projetRepository;
	@Autowired
	private ContrePartieRepository contrePartieRepository;
	@Autowired
	private ContenuCollecteRepository contenuCollecteRepository;
	@Autowired
	private ContenuMissionRepository contenuMissionRepository;
	@Autowired
	private MissionRepository missionRepository;
	@Autowired
	private CollecteRepository collecteRepository;
	@Autowired
	private CycleRepository cycleRepository;
	@Autowired
	private ActualiteRepository actualiteRepository;
	@Autowired
	private CommentaireRepository commentaireRepository;
	@Autowired
	private MediaRepository mediaRepository;

	@Override
	public Optional<Projet> getProjetById(Long idProjet) {
		return Optional.ofNullable(projetRepository.findById(idProjet));
	}

	@Override
	public ContenuCollecte getContenuCollecteParCollecteId(Long idCollecte) {
		return contenuCollecteRepository.getContenuCollecteByCollecteId(idCollecte);
	}

	@Override
	public List<ContrePartie> getListeContrePartieByCollecteId(Long idCollecte) {
		return contrePartieRepository.getListeContrePartieByCollecteId(idCollecte);
	}

	@Override
	public Optional<Mission> getMissionParCycleId(Long idCycle) {
		return Optional.ofNullable(missionRepository.getMissionParCycleId(idCycle));
	}

	@Override
	public Optional<Cycle> getDernierCycle(Long idProjet) {

		LinkedList<Cycle> cycles = cycleRepository.findAllCyclesOfProjet(idProjet);
		Collections.sort(cycles);
		return Optional.ofNullable(cycles.getLast());

//		List<Cycle> cycles = cycleRepository.findAllCyclesOfProjet(idProjet);
//		return cycles.stream().sorted(Comparator.comparing(Cycle::getId).reversed()).findFirst();

	}
	
	@Override
	public Cycle getDernierCycleprojet(Long idProjet) {
		return cycleRepository.findAllCyclesOfProjet(idProjet).getLast();
	}

	@Override
	public Optional<Saison> getDerniereSaison(Long idProjet) {
		return Optional.ofNullable(getDernierCycle(idProjet).get().getSaison());
	}

	@Override
	public long getCompteARebours(Long idCollecte) {
		LocalDate dateCloture = LocalDate
				.parse(collecteRepository.findById(idCollecte).get().getDateCloture().toString());
		return ChronoUnit.DAYS.between(LocalDate.now(), dateCloture);
	}
	@Override
	public long getCompteAReboursMission(Long idMission) {
		LocalDate dateCloture = LocalDate
				.parse(missionRepository.findById(idMission).getDateFin().toString());
		return ChronoUnit.DAYS.between(LocalDate.now(), dateCloture);
	}

	@Override
	public Integer getPourcentageMontantAtteint(Long idCollecte) {
		Optional<Collecte> collecte = collecteRepository.findById(idCollecte);
		return CollecteStatsUtils.pourcentageMontantCollecte(collecte);
	}

	@Override
	public List<Personne> getListeContributeurs(Long idCollecte) {
		Optional<Collecte> collecte = collecteRepository.findById(idCollecte);
		List<Personne> listeVide = new LinkedList<Personne>();
		return collecte.isPresent() && !collecte.get().getListeDeDons().isEmpty()
				? chercherContributeurs(collecte.get().getListeDeDons())
				: listeVide;
	}

	private List<Personne> chercherContributeurs(List<Don> dons) {
		return dons.stream().map(Don::getPersonne).collect(Collectors.toList());
	}

	@Override
	public List<Actualite> getListeActualites(Long idContenuCollecte, TypeContenu typeContenu) {
		return actualiteRepository.findActualiteParTypeContenu(typeContenu).stream()
				.filter(a -> a.getRefIdContenu().equals(idContenuCollecte)).collect(Collectors.toList());
	}

	@Override
	public List<Commentaire> getListeCommentaires(Long idProjet) {
		return commentaireRepository.getCommentairesCollecteParProjetId(idProjet);
	}
	@Override
	public List<Commentaire> getListeCommentairesParActualite(Long idActualite) {
		return commentaireRepository.getCommentairesMissionParActualite(idActualite);
	}

	@Override
	public Optional<Pays> getLocalisationMission(Mission mission) {
		return Optional.ofNullable(missionRepository.findById(mission.getIdMission()).getLocalisation().getPays());
	}

	public Mission getMissionByCycleId(Long idCycle) {
		return missionRepository.getMissionByCycleId(idCycle);
	}

	@Override

	public List<Projet> findByProjetEtat(EtatProjet etat) {
		return projetRepository.findProjetByEtat(etat);
	}

	public ContenuCollecte getContenuCollecteParId(long id) {
		return contenuCollecteRepository.findById(id).get();

	}

	@Override
	public ContenuMission getContenuMissionById(long idContenuMission) {
		return contenuMissionRepository.findById(idContenuMission).get();
	
	}

	@Override
	public MediaActualite getActualiteImage(Long idActualite) {
		return mediaRepository.findByIdActualite(idActualite);
	}

}
