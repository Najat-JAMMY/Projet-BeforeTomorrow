package fr.isika.cdi7.fouille.presentation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.form_objects.RechercheForm;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.CollectesEtMissionsEnCoursService;
import fr.isika.cdi7.fouille.services.ConsulterProjetService;
import fr.isika.cdi7.fouille.services.FouilleService;
import fr.isika.cdi7.fouille.services.ProjetService;
import fr.isika.cdi7.fouille.services.UserService;
import fr.isika.cdi7.fouille.services.UtilisateurFonctionsService;

@Controller
@RequestMapping("/rechercherProjet")
public class RechercherProjetController {

	@Autowired
	private ConsulterProjetService consulterProjetService;
	@Autowired
	private FouilleService fouilleService;
	@Autowired
	private CollectesEtMissionsEnCoursService service;
	
	@Autowired
	UtilisateurFonctionsService utilisateurFonctionsService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjetService projetService;
	@Autowired
	private AdminService adminService;

	@GetMapping("/recherche")
	public String rechercherProjet(Model model) {

		model.addAttribute("RechercheForm", new RechercheForm());
		model.addAttribute("paysListe", fouilleService.getlistPays());

		model.addAttribute("missionAfficher", service.getMissionsEnCoursSurDernierCycle());
		model.addAttribute("collectesAfficher", service.getCollectesEnCoursSurDernierCycle());
		
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = new EspaceUtilisateur();
		Discussion discussion = new Discussion();	
		if(auth != "anonymousUser") {
			user = adminService.findIdAdmin(auth);
			Projet projetuser = projetService.getProjetParIdPersonne(user.getPersonne().getId());
			model.addAttribute("projetuser",projetuser );
			model.addAttribute("Porteur",user );
			 discussion = userService.getDiscussionConseiller(user.getId());
			 model.addAttribute("discussion",discussion );
		}

		
		
		return "recherche";
	}

	@GetMapping(value = "/collectecontenuImage")
	@ResponseBody
	public byte[] afficherContenuImage(@RequestParam(name = "contenuCollecteId") Long contenuCollecteId) {
		ContenuCollecte cc = consulterProjetService.getContenuCollecteParId(contenuCollecteId);
		System.out.println(cc.getImage());
		return cc.getImage();
	}

	@GetMapping(value = "/missioncontenuImage")
	@ResponseBody
	public byte[] afficherMissionContenuImage(@RequestParam(name = "contenuMissionId") Long kk) {

		ContenuMission cm = consulterProjetService.getContenuMissionById(kk);
		System.out.println(cm.getImage());
		return cm.getImage();
	}

	@PostMapping("/recherche")
	public String rechercherProjet(Model model, @ModelAttribute("RechercheForm") RechercheForm rechercheForm) {
		List<Collecte> collectesAfficher = new ArrayList<>();
		List<Mission> missionAfficher = new ArrayList<>();
		List<Cycle> cycles = new ArrayList<Cycle>();
		cycles.addAll(service.tousLesDerniersCyclesEnCours(EtatProjet.EN_CAMPAGNE_DE_COLLECTE));
		cycles.addAll(service.tousLesDerniersCyclesEnCours(EtatProjet.EN_MISSION));

		for (Cycle cycle : cycles) {
			Boolean chronoEquals = cycle.getChronologie() != null
					&& cycle.getChronologie().equals(rechercheForm.getChronologie());
			Boolean thematiqueEquals = cycle.getThematique() != null
					&& cycle.getThematique().equals(rechercheForm.getThematique());
			Boolean paysEquals = cycle.getMission() != null && cycle.getMission().getLocalisation() != null
					&& cycle.getMission().getLocalisation().getPays() != null && cycle.getMission().getLocalisation()
							.getPays().getNomPays_fr().equals(rechercheForm.getPaysNomfr());
			boolean rechercheFormChronologieNull = rechercheForm.getChronologie() == null;
			boolean rechercheThematiqueNull = rechercheForm.getThematique() == null;
			boolean recherchePaysNomfr = rechercheForm.getPaysNomfr() == null || rechercheForm.getPaysNomfr().isEmpty();
			if ((rechercheFormChronologieNull || (!rechercheFormChronologieNull && chronoEquals))
					&& (rechercheThematiqueNull || (!rechercheThematiqueNull && thematiqueEquals))
					&& (recherchePaysNomfr || (!recherchePaysNomfr && paysEquals))) {
				if (cycle.getProjet().getEtat().equals(EtatProjet.EN_CAMPAGNE_DE_COLLECTE)) {
					collectesAfficher.add(cycle.getCollecte());
				}
				if (cycle.getProjet().getEtat().equals(EtatProjet.EN_MISSION)) {
					missionAfficher.add(cycle.getMission());
				}
			}
		}

		model.addAttribute("RechercheForm", new RechercheForm());
		model.addAttribute("paysListe", fouilleService.getlistPays());
		model.addAttribute("collectesAfficher", collectesAfficher);
		model.addAttribute("missionAfficher", missionAfficher);
		return "recherche";
	}
}