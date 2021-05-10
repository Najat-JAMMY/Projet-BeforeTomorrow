package fr.isika.cdi7.fouille.presentation;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

import fr.isika.cdi7.fouille.model.Adresse;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContrePartie;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Pays;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.form_objects.CollecteForm;
import fr.isika.cdi7.fouille.model.form_objects.ContrePartieDto;
import fr.isika.cdi7.fouille.model.form_objects.ProjetCollecteContenuForm;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.CollecteService;
import fr.isika.cdi7.fouille.services.ConsulterProjetService;
import fr.isika.cdi7.fouille.services.ContrePartieService;
import fr.isika.cdi7.fouille.services.CycleService;
import fr.isika.cdi7.fouille.services.FouilleService;
import fr.isika.cdi7.fouille.services.ProjetService;
import fr.isika.cdi7.fouille.services.UserService;

@Controller
@RequestMapping("/projet")
public class EditerProjetController {
	@Autowired
	private ConsulterProjetService consulterService;
	@Autowired
	private ProjetService projetService;
	@Autowired
	private CycleService cycleService;
	@Autowired
	private CollecteService collecteService;
	@Autowired
	private ContrePartieService contrePartieService;
	@Autowired
	private ConsulterProjetService consulterProjetService;
	@Autowired
	private FouilleService fouilleService;

	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;

	@GetMapping("/showProjet")
	public String showProjet(Model model, @RequestParam(name = "id") Long projetId) {
		Projet projet = projetService.findById(projetId);
		model.addAttribute("projet", projet);
		ProjetCollecteContenuForm form = new ProjetCollecteContenuForm();
		ContenuCollecte cc = consulterProjetService.getDernierCycle(projetId).get().getCollecte().getContenuCollecte();
		Adresse localisation = cc.getCollecte().getCycle().getMission().getLocalisation();

		if(cc.getNomProjet() != null) {
			form.setNomProjet(cc.getNomProjet());
		} else {
			form.setNomProjet("");
		}
		if(cc.getDescCourte() != null) {
			form.setDescriptionCourte(cc.getDescCourte());
		} else {
			form.setDescriptionCourte("");
		}
		if(cc.getDescLongue()  != null) {
			form.setDescriptionLong(cc.getDescLongue());
		} else {
			form.setDescriptionLong("");
		}
		if(localisation.getLatitude() != null) {
			form.setLatitude(localisation.getLatitude());
		} else {
			form.setLatitude(0.0);
		}
		if(localisation.getLongitude() != null) {
			form.setLongitude(localisation.getLongitude());
		} else {
			form.setLongitude(0.0);
		}
		if(localisation.getPays() != null) {
			form.setNomPays_fr(localisation.getPays().getNomPays_fr());
		} else {
			form.setNomPays_fr("");
		}
		if(cc.getCollecte().getCycle().getThematique() != null) {
			form.setThematique(cc.getCollecte().getCycle().getThematique());
		} else {
			form.setThematique(null);
		}
		if(cc.getCollecte().getCycle().getChronologie() != null) {
			form.setChronologie(cc.getCollecte().getCycle().getChronologie());
		} else {
			form.setChronologie(null);
		}
		if(cc.getCollecte().getCycle().getMission().getDateDebut() != null) {
			form.setDateDebut(cc.getCollecte().getCycle().getMission().getDateDebut());
		} else {
			form.setDateDebut(null);
		}

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Discussion discussion = userService.getDiscussionConseiller(user.getId());
		model.addAttribute("discussion",discussion );
		model.addAttribute("projet",projet );
		model.addAttribute("collecte", consulterProjetService.getDernierCycle(projetId).get().getCollecte());
		model.addAttribute("form", form);

		return "showProjet";
	}

	@GetMapping("/editer")
	public String editerProjet(Model model, @RequestParam(name = "id") Long projetId) {

		ProjetCollecteContenuForm form = new ProjetCollecteContenuForm();
		Cycle cycle = consulterProjetService.getDernierCycleprojet(projetId);
		Mission mission = cycle.getMission();

		form.setDateDebut(mission.getDateDebut());
		Projet projet = projetService.findById(projetId);
		model.addAttribute("projet", projet);

		ContenuCollecte cc = consulterProjetService.getDernierCycle(projetId).get().getCollecte().getContenuCollecte();
		Adresse localisation = cc.getCollecte().getCycle().getMission().getLocalisation();

		if(cc.getNomProjet()  != null) {
			form.setNomProjet(cc.getNomProjet());
		} else {
			form.setNomProjet("");
		}
		if(cc.getDescCourte()  != null) {
			form.setDescriptionCourte(cc.getDescCourte());
		} else {
			form.setDescriptionCourte("");
		}
		if(cc.getDescLongue()  != null) {
			form.setDescriptionLong(cc.getDescLongue());
		} else {
			form.setDescriptionLong("");
		}
		if(localisation.getLatitude() != null) {
			form.setLatitude(localisation.getLatitude());
		} else {
			form.setLatitude(0.0);
		}
		if(localisation.getLongitude() != null) {
			form.setLongitude(localisation.getLongitude());
		} else {
			form.setLongitude(0.0);
		}
		if(localisation.getPays() != null) {
			form.setNomPays_fr(localisation.getPays().getNomPays_fr());
		} else {
			form.setNomPays_fr("");
		}
		form.setDateDebut(mission.getDateDebut());
		form.setProjetId(projetId);
		
		if(cycle.getThematique() != null) {
			form.setThematique(cycle.getThematique());
		} else {
			form.setThematique(null);
		}
		if(cycle.getChronologie() != null) {
			form.setChronologie(cycle.getChronologie());
		} else {
			form.setChronologie(null);
		}

		List<Pays> paysListe = fouilleService.getlistPays();
		model.addAttribute("paysListe", paysListe);

		model.addAttribute("ProjetCollecteContenueForm", form);
		return  "editerProjet";
	}

	@PostMapping("/editer")
	public String editerProjet(Model model, @ModelAttribute("projetCollecteContuneForm") ProjetCollecteContenuForm form)
			throws IOException {
		projetService.editerProjet(form);
		return "redirect:showProjet?id=" + form.getProjetId();
	}

	@GetMapping(value = "/descriptionImage")
	@ResponseBody
	public byte[] afficherdescriptionImage(@RequestParam(name = "contenuCollecteId") Long contenuCollecteId) {
		ContenuCollecte cc = consulterService.getContenuCollecteParId(contenuCollecteId);
		return cc.getImage();
	}

	@GetMapping("/ajouterCollecte")
	public String ajouterCollecte(Model model, @RequestParam(name = "CycleId") Long CycleId) throws IOException {
		
		CollecteForm form = new CollecteForm();
		Cycle cycle = cycleService.findById(CycleId);
		Collecte collecte = cycle.getCollecte();

		List<ContrePartie> cp = cycle.getCollecte().getListeDeContreParties();
		
		for (ContrePartie contrePartie : cp) {
			
			if(contrePartie.getDescription() != null) {
				form.setDescriptionCP(contrePartie.getDescription());
			} else {
				form.setDescriptionCP("");
			}
			if(contrePartie.getImage() != null) {
				form.setImageCP(contrePartie.getImage());
			} else {
				form.setImageCP(null);
			}
			if(contrePartie.getTitre() != null) {
				form.setTitreCP(contrePartie.getTitre());
			} else {
				form.setTitreCP("");
			}
			if(contrePartie.getMontant() != 0) {
				form.setMontantCP(contrePartie.getMontant());
			} else {
				form.setMontantCP(0);
			}
		}
		
		if(collecte.getDuree() != null) {
			form.setDuree(collecte.getDuree());
		} else {
			form.setDuree(null);
		}
		
		if(collecte.getMontantDemande() != null) {
			form.setMontant(collecte.getMontantDemande());
		} else {
			form.setMontant(null);
		}
		
		model.addAttribute("contreparties",
				consulterProjetService.getListeContrePartieByCollecteId(collecte.getId()));
		model.addAttribute("form", form);
		model.addAttribute("cycle", cycle);
		model.addAttribute("collecte", collecte);
		return  "ajouterCollecte";
	}

	@PostMapping("/ajouterCollecte")
	public String ajouterCollecte(Model model, @RequestParam(name = "CycleId") Long CycleId,
			@RequestParam(name="collecte") Long idcollecte,@ModelAttribute("form") CollecteForm form) {
			
		Collecte collecte = collecteService.findById(idcollecte);
		collecteService.saveCollecte(form, collecte);
		
		return "redirect:showProjet?id=" + collecte.getCycle().getProjet().getId();
	}

	@GetMapping("/ajouterContrepartie")
	public String ajouterContrePartie(Model model, @RequestParam(name = "CollecteId") Long CollecteId) {

		ContrePartieDto contrePartieDto = new ContrePartieDto();
		Collecte collecte = collecteService.findById(CollecteId);
		
		model.addAttribute("contrePartie", contrePartieDto);
		model.addAttribute("collecte", collecte);
		return  "ajouterContrePartie";

	}

	@PostMapping("/ajouterContrepartie")
	public String ajouterContrePartie(Model model, @ModelAttribute("contrePartie") ContrePartieDto contrePartieDto,@RequestParam(name = "CollecteId") Long CollecteId )
			throws IOException {
		Collecte collecte = collecteService.findById(CollecteId);
		contrePartieService.saveContrePartie(contrePartieDto,collecte);
		return "redirect:ajouterCollecte?CycleId=" + collecte.getCycle().getId();

	}
	
	@GetMapping("/supprimerContrePartie")
	public String supprimerContrepartie(Model model, @RequestParam(name = "idContrePartie") Long idContrePartie) {
		ContrePartie cp = contrePartieService.findById(idContrePartie).get();
		Cycle cycle = cycleService.findById(cp.getCollecte().getCycle().getId());
		contrePartieService.deleteContrePartie(idContrePartie);
		return "redirect:ajouterCollecte?CycleId=" + cycle.getId();
	}

	@GetMapping(value = "/contrepartieImage")
	@ResponseBody
	public byte[] afficherPhoto(@RequestParam(name = "contrepartieId") Long contrepartieId) {
		Optional<ContrePartie> contrePartie = contrePartieService.findById(contrepartieId);
		return contrePartie.get().getImage();
	}



}