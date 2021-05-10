package fr.isika.cdi7.fouille.services;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.AdresseRepository;
import fr.isika.cdi7.fouille.dao.ContenuCollecteRepository;
import fr.isika.cdi7.fouille.dao.CycleRepository;
import fr.isika.cdi7.fouille.dao.DonRepository;
import fr.isika.cdi7.fouille.dao.FavorisRepository;
import fr.isika.cdi7.fouille.dao.MissionRepository;
import fr.isika.cdi7.fouille.dao.PaysRepository;
import fr.isika.cdi7.fouille.dao.ProjetRepository;
import fr.isika.cdi7.fouille.model.Adresse;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Don;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Favoris;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Pays;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.form_objects.ProjetCollecteContenuForm;
import fr.isika.cdi7.fouille.model.form_objects.ProjetForm;

@Service
public class ProjetServiceImpl implements ProjetService {

	@Autowired
	private ProjetRepository projetRepository;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private FavorisRepository favorisRepository;

	@Autowired
	private DonRepository donRepository;

	@Autowired
	private CycleRepository cycleRepository;

	@Autowired
	private ConsulterProjetService cPService;

	@Autowired
	private MissionRepository missionRepository;
	@Autowired
	private AdresseRepository adresseRepository;
	@Autowired
	private PaysRepository paysRepository;
	@Autowired
	private ContenuCollecteRepository contenuCollecteRepository;

	@Override
	public List<Projet> listDeProjetDepose(EtatProjet etat) {
		List<Projet> listeDeProjet = projetRepository.findProjetByEtat(etat);
		return listeDeProjet;
	}

	@Override
	public Optional<Projet> getProjetParId(Long id) {
		return Optional.ofNullable(projetRepository.findById(id));
	}

	@Override
	public void add(Projet projet) {
		projetRepository.save(projet);
	}

	private Date createDate() {
		return Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public void addProjet(Personne porteur, ProjetForm projetForm) {
		Projet projetEntity = new Projet();
		projetEntity.setTitre(projetForm.getTitre());
		projetEntity.setRattachement(projetForm.getRattachement());

		int montantEnvisage = Integer.parseInt(projetForm.getMontantEnvisage());
		projetEntity.setMontantEnvisage(montantEnvisage);
		projetEntity.setDateEnvisagee(projetForm.getDateEnvisagee());
		projetEntity.setPays(projetForm.getPays());
		projetEntity.setDescription(projetForm.getDescription());
		projetEntity.setDateDepot(createDate());
		projetEntity.setEtat(EtatProjet.EN_ATTENTE_DE_TRAITEMENT);
		projetEntity.setGestionnaire(null);
		projetEntity.setPorteur(porteur);

		projetRepository.save(projetEntity);

	}

	public void editerProjet(ProjetCollecteContenuForm projetCollecteContenueForm) throws IOException {

		Cycle cycle = cPService.getDernierCycleprojet(projetCollecteContenueForm.getProjetId());

	
		cycle.setChronologie(projetCollecteContenueForm.getChronologie());
		cycle.setThematique(projetCollecteContenueForm.getThematique());
		ContenuCollecte cc = cycle.getCollecte().getContenuCollecte();
		cc.setDescCourte(projetCollecteContenueForm.getDescriptionCourte());
		cc.setDescLongue(projetCollecteContenueForm.getDescriptionLong());
		cc.setNomProjet(projetCollecteContenueForm.getNomProjet());
		Adresse localisation = cycle.getMission().getLocalisation();
		localisation.setLatitude(projetCollecteContenueForm.getLatitude());
		localisation.setLongitude(projetCollecteContenueForm.getLongitude());
		Pays pays = paysRepository.findByNomfr(projetCollecteContenueForm.getNomPays_fr());
		localisation.setPays(pays);
		Mission mission = cycle.getMission();
		mission.setDateDebut(projetCollecteContenueForm.getDateDebut());
		
		if (projetCollecteContenueForm.getImage().getBytes() != null
				&& projetCollecteContenueForm.getImage().getBytes().length > 0) {
			cc.setImage(projetCollecteContenueForm.getImage().getBytes());
		}
		
		
		cycleRepository.save(cycle);
		contenuCollecteRepository.save(cc);
		adresseRepository.save(localisation);
		missionRepository.save(mission);
	}

	public Projet findById(long id) {
		return projetRepository.findById(id);
	}

	@Override
	public Projet save(Projet projet) {
		return projetRepository.save(projet);
	}
//	@Override
//	@Transactional(rollbackFor = Exception.class)
//	public int updatePhoto(Long deviceId, MultipartFile file) throws Exception {
//		byte[] bytes = file.getBytes();
//		deviceDtls.setPhoto(bytes);
//		int result = deviceDtlsMapper.updateById(deviceDtls);
//		if (result != 1) {
//			throw new RuntimeException("error refresh！");
//		}
//		return result;
//	}

	@Override
	public Projet getProjetParIdPersonne(Long idPersonne) {

		return projetRepository.getProjetIdPorteur(idPersonne);
	}

	@Override
	public List<Favoris> getProjetFavoris(Long id) {
		return favorisRepository.getFavorisPersonne(id);

	}

	@Override
	public List<Don> getDonDunePersonne(Long id) {
		return donRepository.getCollecteContributionDunePersonne(id);

	}

	@Override
	public List<Cycle> getCycleByListFavoris(List<Favoris> listeDeProjetEnFavoris) {

		List<Cycle> listeDeCycle = new ArrayList();
		for (Favoris favoris : listeDeProjetEnFavoris) {
			listeDeCycle
					.add(favoris.getProjet().getCycle().stream().max(Comparator.comparingLong(e -> e.getId())).get());
		}
		return listeDeCycle;
	}

	@Override
	public void MiseEnLigneDuneMission(Long idCycle) {
		
		Cycle cycle = cycleRepository.findById(idCycle).get();
		Mission mission = missionRepository.findById(cycle.getMission().getIdMission());
		Projet proj = projetRepository.findById(cycle.getProjet().getId());
		
		mission.setDateDebut(new Date());
		Date dat = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, mission.getDuree());  
		mission.setDateFin(cal.getTime());
		missionRepository.save(mission);
		
		adminService.changementEtatProjetById(proj.getId().toString(), EtatProjet.EN_MISSION);
	}
	@Override
	public List<Projet> findAll() {
		
		return projetRepository.findAll();
	}


}