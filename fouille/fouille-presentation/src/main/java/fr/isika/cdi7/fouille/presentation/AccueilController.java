package fr.isika.cdi7.fouille.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.CollectesEtMissionsEnCoursService;
import fr.isika.cdi7.fouille.services.ConsulterProjetService;
import fr.isika.cdi7.fouille.services.ProjetService;
import fr.isika.cdi7.fouille.services.UserService;


@Controller
public class AccueilController {

	@Autowired
	private CollectesEtMissionsEnCoursService service;
	@Autowired
	private ConsulterProjetService consulterProjetService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ProjetService projetService;

	@Autowired
	private UserService userService;

	@GetMapping("/accueil")
	public String accueil(Model model) {
		
		List<Collecte> collectes = service.getCollectesEnCoursSurDernierCycle();
		List<Mission> missions = service.getMissionsEnCoursSurDernierCycle();
		
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = new EspaceUtilisateur();
		Discussion discussion = new Discussion();

		Projet projet = new Projet() ;		
		
		if(auth != "anonymousUser") {
			user = adminService.findIdAdmin(auth);
			projet = projetService.getProjetParIdPersonne(user.getPersonne().getId());
			model.addAttribute("projet",projet );
			model.addAttribute("Porteur",user );
				if(projet != null) {
					
					 discussion = userService.getDiscussionConseiller(user.getId());
					 model.addAttribute("discussion",discussion );
				}
		}
		model.addAttribute("collectes", collectes);
		model.addAttribute("missions", missions);

		return "accueil";
		
	}
	@GetMapping(value = "/AccueilImage")
	@ResponseBody
	public byte[] afficherContenuImage(@RequestParam(name = "contenuCollecteId") Long contenuCollecteId) {
		ContenuCollecte cc = consulterProjetService.getContenuCollecteParId(contenuCollecteId);
		return cc.getImage();
	}
	@GetMapping(value = "/AccueilMissionImage")
	@ResponseBody
	public byte[] afficherMissionContenuImage(@RequestParam(name = "contenuMissionId") Long kk) {
		
		ContenuMission cm=consulterProjetService.getContenuMissionById(kk);
		return cm.getImage();
	}

}
