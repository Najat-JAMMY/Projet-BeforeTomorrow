package fr.isika.cdi7.fouille.presentation;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Commentaire;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Favoris;
import fr.isika.cdi7.fouille.model.MediaActualite;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Pays;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.Saison;
import fr.isika.cdi7.fouille.model.TypeContenu;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.ConsulterProjetService;
import fr.isika.cdi7.fouille.services.DonService;
import fr.isika.cdi7.fouille.services.ProjetService;
import fr.isika.cdi7.fouille.services.UserService;
import fr.isika.cdi7.fouille.services.UtilisateurFonctionsService;
import fr.isika.cdi7.fouille.utils.DateUtils;

@Controller
@RequestMapping("/projet")
public class ProjetController {

	// ---------------------------------------- //
	// CONSULTER UN PROJET //
	// ---------------------------------------- //

	private static final Logger LOGGER = Logger.getLogger(ProjetController.class.getSimpleName());

	@Autowired
	private ConsulterProjetService consulterProjetService;
	@Autowired
	private DonService donService;
	@Autowired
	private AdminService adminService;
	@Autowired
	UtilisateurFonctionsService utilisateurFonctionsService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProjetService projetService;


	public ProjetController() {
		LOGGER.info("ProjetController instance created ...");
	}

	// ---------------------------------------- //
	// PROJET ETAT EN_COURS_DE_CAMPAGNE //
	// ---------------------------------------- //

	@GetMapping("/collecteEnCours/{idProjet}")
	public String collecteEnCours(@PathVariable("idProjet") Long idProjet, Model model) {

		Optional<Projet> projet = consulterProjetService.getProjetById(idProjet);

		if (projet.isPresent()) {
			Cycle currentCycle = consulterProjetService.getDernierCycle(idProjet).get();
			Saison saison = consulterProjetService.getDernierCycle(idProjet).get().getSaison();
			Collecte collecte = donService.getCollecteIdCycle(currentCycle.getId());
			Mission mission = consulterProjetService.getMissionParCycleId(currentCycle.getId()).get();
			ContenuCollecte contenuCollecte = consulterProjetService.getContenuCollecteParCollecteId(collecte.getId());

			model.addAttribute("contenuCollecte", contenuCollecte);
			model.addAttribute("collecte", collecte);
			model.addAttribute("mission", mission);
			model.addAttribute("saison", saison.toString().toUpperCase());
			model.addAttribute("porteur", projet.get().getPorteur());

			model.addAttribute("dateCloture", DateUtils.convertirFormatDate(collecte.getDateCloture()));
			model.addAttribute("dateDepartMission", DateUtils.convertirFormatDate(mission.getDateDebut()));

			Integer pourcentage = consulterProjetService.getPourcentageMontantAtteint(collecte.getId());
			model.addAttribute("pourcentage", pourcentage);

			Optional<Pays> pays = consulterProjetService.getLocalisationMission(mission);
			model.addAttribute("pays", pays.get().getNomPays_fr());

			Integer joursRestants = (int) consulterProjetService.getCompteARebours(collecte.getId());
			model.addAttribute("compteJours", joursRestants);

			model.addAttribute("contreparties",
					consulterProjetService.getListeContrePartieByCollecteId(collecte.getId()));

			List<Actualite> actualites = consulterProjetService.getListeActualites(contenuCollecte.getId(),
					TypeContenu.COLLECTE);
			List<Actualite> actualitesParDate = actualites.stream()
					.sorted(Comparator.comparing(Actualite::getDate).reversed()).collect(Collectors.toList());
			model.addAttribute("actualites", actualitesParDate);

			List<Personne> communaute = consulterProjetService.getListeContributeurs(collecte.getId());
			List<Personne> communauteSansDoublons = communaute.stream().distinct().collect(Collectors.toList());
			model.addAttribute("communaute", communauteSansDoublons);

			List<Commentaire> commentaires = consulterProjetService.getListeCommentaires(projet.get().getId());
			List<Commentaire> commentairesParDate = commentaires.stream()
					.sorted(Comparator.comparing(Commentaire::getDateCommentaire).reversed())
					.collect(Collectors.toList());
			model.addAttribute("commentaires", commentairesParDate);

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

			model.addAttribute("projet",projet.get() );
			Favoris favori = new Favoris();
			if (user != null) {
				favori = utilisateurFonctionsService.verifierSiFavoriDansEspaceUser(user.getId(), idProjet);
				model.addAttribute("favori", favori);
			}
			return "collecteEnCours";
		}
		model.addAttribute("message", "Il semble que cette mission soit introuvable...");
		return "pageIntrouvable";
	}

	
	@GetMapping("/actualiteImage/{idActualite}")
	@ResponseBody
	public byte[] afficherImage(@PathVariable("idActualite") Long idActualite) {
		MediaActualite ma = consulterProjetService.getActualiteImage(idActualite);
		return ma.getMedia();
	}

	// ---------------------------------------- //
	// PROJET ETAT MISSION_EN_COURS //
	// ---------------------------------------- //

	@GetMapping("/missionEnCours/{idProjet}")
	public String missionEnCours(@PathVariable("idProjet") Long idProjet, Model model) {

		Optional<Projet> projet = consulterProjetService.getProjetById(idProjet);

		if (projet.isPresent()) {
			Cycle currentCycle = consulterProjetService.getDernierCycle(idProjet).get();
			Saison saison = consulterProjetService.getDernierCycle(idProjet).get().getSaison();
			Collecte collecte = donService.getCollecteIdCycle(currentCycle.getId());
			Mission mission = consulterProjetService.getMissionParCycleId(currentCycle.getId()).get();
			ContenuMission contenuMission = consulterProjetService
					.getContenuMissionById(mission.getContenuMission().getId());

			model.addAttribute("contenuMission", contenuMission);
			model.addAttribute("collecte", collecte);
			model.addAttribute("mission", mission);
			model.addAttribute("saison", saison.toString().toUpperCase());
			model.addAttribute("porteur", projet.get().getPorteur());

			model.addAttribute("dateCloture", DateUtils.convertirFormatDate(mission.getDateFin()));
			model.addAttribute("dateDepartMission", DateUtils.convertirFormatDate(mission.getDateDebut()));

			Optional<Pays> pays = consulterProjetService.getLocalisationMission(mission);
			model.addAttribute("pays", pays.get().getNomPays_fr());

			Integer joursRestants = (int) consulterProjetService.getCompteAReboursMission(mission.getIdMission());
			model.addAttribute("compteJours", joursRestants);

			List<Actualite> actualites = consulterProjetService.getListeActualites(contenuMission.getId(),
					TypeContenu.MISSION);
			for (Actualite actualite : actualites) {
				actualite.setCommentaires(consulterProjetService.getListeCommentairesParActualite(actualite.getId())
						.stream().sorted(Comparator.comparing(Commentaire::getDateCommentaire).reversed())
						.collect(Collectors.toList()));
			}
			List<Actualite> actualitesParDate = actualites.stream()
					.sorted(Comparator.comparing(Actualite::getDate).reversed()).collect(Collectors.toList());
			model.addAttribute("actualites", actualitesParDate);

			List<Personne> communaute = consulterProjetService.getListeContributeurs(collecte.getId());
			List<Personne> communauteSansDoublons = communaute.stream().distinct().collect(Collectors.toList());
			model.addAttribute("communaute", communauteSansDoublons);

			String auth = SecurityContextHolder.getContext().getAuthentication().getName();
			EspaceUtilisateur user = new EspaceUtilisateur();
			Discussion discussion = new Discussion();	
			if(!auth.equals("anonymousUser")) {
				user = adminService.findIdAdmin(auth);
				Projet projetuser = projetService.getProjetParIdPersonne(user.getPersonne().getId());
				model.addAttribute("projetuser",projetuser );
				model.addAttribute("Porteur",user );
				 discussion = userService.getDiscussionConseiller(user.getId());
				 model.addAttribute("discussion",discussion );
			}
			model.addAttribute("projet",projet.get());
			
			Favoris favori = new Favoris();
			if (user != null) {
				favori = utilisateurFonctionsService.verifierSiFavoriDansEspaceUser(user.getId(), idProjet);
				model.addAttribute("favori", favori);
			}
			return "missionEnCours";
		}
		model.addAttribute("message", "Il semble que cette mission soit introuvable...");
		return "pageIntrouvable";
	}

	@PostMapping("/commenterActualite")
	public String posterUnCommentaireActualite(@RequestParam Long idProjet, @RequestParam String commentaire,
			@RequestParam String page, @RequestParam Long idActualite, Model model) {
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		utilisateurFonctionsService.posterUnCommentaireSurActualite(user.getId(), idProjet, idActualite, commentaire);
		model.addAttribute("idProjet", idProjet);
		return "redirect:/projet/" + page + idProjet;
	}
	@PostMapping("/posterUnCommentaire")
	public String posterUnCommentaire(@RequestParam Long idProjet, @RequestParam String commentaire,
			@RequestParam String page, Model model) {
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		utilisateurFonctionsService.posterUnCommentaire(user.getId(), idProjet, commentaire);
		model.addAttribute("idProjet", idProjet);
		return "redirect:/projet/" + page + idProjet;
	}

	@PostMapping("/ajouterAuxFavoris")
	public String ajouterAuxFavoris(@RequestParam Long idProjet, @RequestParam String page, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		utilisateurFonctionsService.ajouterAuxFavoris(user.getId(), idProjet);

		model.addAttribute("idProjet", idProjet);
		return "redirect:/projet/" + page + idProjet;

	}

	@PostMapping("/retirerDesFavoris")
	public String retirerDesFavoris(@RequestParam Long idProjet, @RequestParam String page, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		utilisateurFonctionsService.retirerDesFavoris(user.getId(), idProjet);

		model.addAttribute("idProjet", idProjet);
		return "redirect:/projet/" + page + idProjet;

	}

}
