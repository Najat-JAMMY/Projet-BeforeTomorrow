package fr.isika.cdi7.fouille.presentation;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.form_objects.ProjetForm;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.DonService;
import fr.isika.cdi7.fouille.services.ProjetService;

@Controller
@RequestMapping("/lancerUnProjet")
public class LancerUnProjetController {

	private static final Logger LOGGER = Logger.getLogger(LancerUnProjetController.class.getSimpleName());

	@Autowired
	private ProjetService projetService;

	@Autowired
	private DonService donService;
	
	@Autowired
	private AdminService adminService;

	@GetMapping("/lancerProjet")
	public String faireUnDon(Model model) {
		model.addAttribute("projetForm", new ProjetForm());
		return "lancerProjet";
	}

	@PostMapping("/lancerProjet")
	public String validerFormulaire(@ModelAttribute("projetForm") ProjetForm projetForm) throws ParseException {
		
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Long idPorteur = user.getPersonne().getId();

		Personne porteur = donService.getUtilisateur(idPorteur);
		LOGGER.info("" + projetForm.getDescription());

		projetService.addProjet(porteur, projetForm);

		return "redirect:/compte/monCompte";
	}

	private Date createDate() {
		return Date.from(LocalDate.now().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@GetMapping("/gestionnaireDeProjet")
	public String afficherProjetDepose(Model model) {

		model.addAttribute("listProjetDepose", projetService.listDeProjetDepose(EtatProjet.EN_ATTENTE_DE_TRAITEMENT));

		return "gestionnaireDeProjet";

	}
}
