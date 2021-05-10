package fr.isika.cdi7.fouille.presentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.isika.cdi7.fouille.dao.ActualiteRepository;
import fr.isika.cdi7.fouille.dao.MediaRepository;
import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.MediaActualite;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.TypeContenu;
import fr.isika.cdi7.fouille.model.form_objects.ActualiteDto;
import fr.isika.cdi7.fouille.services.ActualiteService;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.CycleService;
import fr.isika.cdi7.fouille.services.ProjetService;
import fr.isika.cdi7.fouille.services.UserService;


@Controller
public class EditerActualiteController {
	@Autowired
	private ProjetService projetService;
	@Autowired
	private CycleService cycleService;
	@Autowired
	private ActualiteService actService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	@Autowired
	private MediaRepository mediaRepo;
	@Autowired
	private ActualiteRepository actualiteRepo;
	
	
	

	@GetMapping("/actualites")
	public String showActualite(Model model, @RequestParam(name = "id") Long projetId) {
		Projet projet = projetService.findById(projetId);
		ContenuCollecte cc = cycleService.getLastCycleFromProjet(projet).getCollecte().getContenuCollecte();
		ContenuMission cm = cycleService.getLastCycleFromProjet(projet).getMission().getContenuMission();
System.out.println(cc.getId());

		List<Actualite> listActualite = new ArrayList();
		
		if(projet.getEtat().equals(EtatProjet.EN_MISSION)) {
			listActualite = actService.getListActualiteByIdContenuEtTypeContenu(cm.getId(),TypeContenu.MISSION);
			 
		}
		else if(projet.getEtat().equals(EtatProjet.EN_CAMPAGNE_DE_COLLECTE)){
			 listActualite = actService.getListActualiteByIdContenuEtTypeContenu(cc.getId(),TypeContenu.COLLECTE);			 
		}

		List<MediaActualite> listeMedia = actService.getListMediaByListeActualite(listActualite);

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Discussion discussion = userService.getDiscussionConseiller(user.getId());
		model.addAttribute("listeMedia",listeMedia );
		model.addAttribute("listActualite",listActualite );
		model.addAttribute("discussion",discussion );
		model.addAttribute("projet",projet );
		
		return "showActualite";
		
		
	}
	
	@GetMapping("/editeractualite")
	public String editerActualite(Model model, @RequestParam(name = "id") Long projetId) {
		Projet projet = projetService.findById(projetId);
		ActualiteDto actualiteDto=new ActualiteDto();
		ContenuCollecte cc = cycleService.getLastCycleFromProjet(projet).getCollecte().getContenuCollecte();
		ContenuMission cm = cycleService.getLastCycleFromProjet(projet).getMission().getContenuMission();
		
		actualiteDto.setDate(new Date());
		if (projet.getEtat().equals(EtatProjet.EN_MISSION)) {

			actualiteDto.setTypeContenu(TypeContenu.MISSION);
			actualiteDto.setRefIdContenu(cm.getId());

		} else if (projet.getEtat().equals(EtatProjet.EN_CAMPAGNE_DE_COLLECTE)) {
			actualiteDto.setTypeContenu(TypeContenu.COLLECTE);
			actualiteDto.setRefIdContenu(cc.getId());

		}
		actualiteDto.setProjetId(projetId);
		model.addAttribute("actualite", actualiteDto);
		model.addAttribute("projet", projet);
		
		
		return  "editerActualite";
	}

	@PostMapping("/editeractualite")
	public String editerActualite(Model model, @ModelAttribute("actualite") ActualiteDto actualiteDto) throws IOException {
		Projet projet = projetService.findById(actualiteDto.getProjetId());
		
      cycleService.editerActualite(actualiteDto);
      
		return "redirect:actualites?id=" + actualiteDto.getProjetId();
	}
	
	@GetMapping("/supprimerActualite")
	public String supprimerContrepartie(Model model, @RequestParam(name = "id") Long idMediaActualite) {
	
		MediaActualite media  = mediaRepo.findById(idMediaActualite).get();
		ContenuCollecte cc = new ContenuCollecte();
		ContenuMission cm = new ContenuMission();
		Projet projet  = new Projet();
		if(media.getActualite().getContenu() == TypeContenu.COLLECTE) {
			
			 cc = actService.findContenuCollecteBYRefMedia(media.getActualite().getRefIdContenu());
			 projet = cc.getCollecte().getCycle().getProjet();
			
		}else if(media.getActualite().getContenu() == TypeContenu.MISSION){
			cm = actService.findContenuCollecteMissionBYRefMedia(media.getActualite().getRefIdContenu());
			 projet = cm.getMission().getCycle().getProjet();
			
		}
		
		
		
		Actualite act = media.getActualite();
	
		mediaRepo.delete(media);
		actualiteRepo.delete(act);		
		return "redirect:actualites?id=" +projet.getId();

	} 
	
	@GetMapping(value = "/actualiteImage")
	@ResponseBody
	public byte[] afficherdescriptionImage(@RequestParam(name = "id") Long idActualite) {
		MediaActualite md = actService.getMediaByIdActualite(idActualite);
		return md.getMedia();
	}

	//@GetMapping(value = "/missionImage")
//	@ResponseBody
//	public byte[] afficherdescriptionImage(@RequestParam(name = "contenueMissionId") Long contenuMissionId) {
//		// Projet projet = pService.findById(projetId);
//		// ContenuCollecte
//		// cc=consulterService.getContenuCollecteParId(missionCollecteId);
//		ContenuMission cm = consulterService.getContenuMissionById(contenuMissionId);
//
//		return cm.getImage();
//	}
//	@GetMapping("/showActualite")
//	public String showMission(Model model, @RequestParam(name = "idActualite") Long idActualite,@RequestParam(name = "idProjet") Long idProjet) {
//		
//		Actualite a=actualteService.findById(idActualite);
//		
//		Projet projet = projetService.findById(idProjet);
//		Cycle cycle=cycleService.getLastCycleFromProjet(projet);
//		ActualiteDto actualiteDto = new ActualiteDto();
//		
//		if(projet.getEtat().equals(EtatProjet.EN_MISSION)) {
//			actualiteDto.setDate(a.getDate());
//			actualiteDto.setDescription(a.getDescription());
//			actualiteDto.setImage(a.getMediaActualite().getMedia().);
//		}
//		
//		 else if (projet.getEtat().equals(EtatProjet.EN_CAMPAGNE_DE_COLLECTE)) {
//			 
//			 
//			 
//		 }
//		
//		
//		
//		model.addAttribute("projet", projet);
//		model.addAttribute("ContenueMission", contenuMission);
//		model.addAttribute("Actualite", ActualiteDto);
//		ActualiteDto actualiteDto = new ActualiteDto();
//		actualiteDto.setDate(c);
//missionContenueMissionDto.setNomMission(getLastCycleFromProjet(projet).getMission().getContenuMission().getNomMission());
//		missionContenueMissionDto.setDescriptionCourteMission(
//				getLastCycleFromProjet(projet).getMission().getContenuMission().getDescCourteMission());
//		missionContenueMissionDto.setDescriptionLongMission(
//				getLastCycleFromProjet(projet).getMission().getContenuMission().getDescLongueMission());
//		
//		ContenuMission contenuMission=getLastCycleFromProjet(projet).getMission().getContenuMission();
//		model.addAttribute("ContenueMission", contenuMission);
//		System.out.println(contenuMission.getId());
//		model.addAttribute("Actualite", ActualiteDto);
//		missionContenueMissionDto
//				.setNomPays_fr(getLastCycleFromProjet(projet).getMission().getLocalisation().getPays().getNomPays_fr());
//		missionContenueMissionDto
//				.setLongitude(getLastCycleFromProjet(projet).getMission().getLocalisation().getLongitude());
//		missionContenueMissionDto
//				.setLatitude(getLastCycleFromProjet(projet).getMission().getLocalisation().getLatitude());
//		// if (projet.getCycle() != null && !projet.getCycle().isEmpty()) {
//		// model.addAttribute("collecte", projet.getCycle().get(projet.getCycle().size()
//		// - 1).getCollecte());
//
//		return showPage(model, "editermission", "showMission", projetId);
//	}

}
