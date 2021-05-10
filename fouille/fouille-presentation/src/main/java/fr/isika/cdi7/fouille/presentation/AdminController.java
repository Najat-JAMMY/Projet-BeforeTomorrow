package fr.isika.cdi7.fouille.presentation;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.isika.cdi7.fouille.dao.ContenuCollecteRepository;
import fr.isika.cdi7.fouille.dao.DiscussionRepository;
import fr.isika.cdi7.fouille.dao.EspaceUtilisateurRepository;
import fr.isika.cdi7.fouille.dao.MessageRepository;
import fr.isika.cdi7.fouille.dao.ProjetRepository;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Commentaire;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Message;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.Saison;
import fr.isika.cdi7.fouille.model.TypeRole;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.CollectesEtMissionsEnCoursService;
import fr.isika.cdi7.fouille.services.DonService;

@Controller
@RequestMapping("/admins")
public class AdminController {

	private static final String ADMIN = "Admin";

	@Autowired
	private AdminService adminService;

	@Autowired
	private DonService donsService;

	@Autowired
	private ProjetRepository projetRepo;

	@Autowired
	private EspaceUtilisateurRepository userRepo;

	@Autowired
	private MessageRepository messageRepo;

	@Autowired
	private DiscussionRepository discussionRepo;

	@Autowired
	private ContenuCollecteRepository contenuCollecteRepo;
	@Autowired
	private CollectesEtMissionsEnCoursService ccmc;

	@GetMapping("/admin")
	public String index(Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);

		Long idAdmin = adminService.getIdByEmail(auth);
		model.addAttribute("idAdmin", idAdmin);
		model.addAttribute(ADMIN, user);
		System.out.println(auth);
		System.out.println(idAdmin);
		return "index_Admin";
	}

	@GetMapping("/projet")
	public String listeDeProjet(Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Long idAdmin = adminService.getIdByEmail(auth);
		model.addAttribute("idAdmin", idAdmin);
		model.addAttribute(ADMIN, user);

		List<Projet> listeDeprojetEnAttente = adminService
				.getProjetGestionnaireEnAttente(EtatProjet.EN_ATTENTE_DE_TRAITEMENT);
		List<Projet> listeDeProjetGestionnaireEnPrepa = adminService
				.getProjetGestionnaireParEtat(EtatProjet.EN_PREPARATION_DE_COLLECTE, idAdmin);
		List<Projet> listeDeProjetGestionnaireEnCollecte = adminService
				.getProjetGestionnaireParEtat(EtatProjet.EN_CAMPAGNE_DE_COLLECTE, idAdmin);

		List<Cycle> listeDeProjetGestionnaireEnFinDeMission = adminService
				.getProjetGestionnaireFinDeMission(EtatProjet.EN_MISSION, idAdmin);

		List<Cycle> listCy = ccmc.tousLesDerniersCyclesEnCours(EtatProjet.EN_MISSION).stream()
				.filter(c -> c.getMission().getDateFin().before(new Date())
						&& c.getProjet().getGestionnaire().getId() == idAdmin && c.getSaison() != Saison.DIX)
				.collect(Collectors.toList());

		model.addAttribute("listeDeprojetEnAttente", listeDeprojetEnAttente);
		model.addAttribute("listeDeProjetGestionnaire", listeDeProjetGestionnaireEnPrepa);
		model.addAttribute("listeDeProjetGestionnaireEnCollecte", listeDeProjetGestionnaireEnCollecte);
		model.addAttribute("listeDeProjetGestionnaireEnFinDeMission", listCy);
		return "gestionnaire_projet";
	}

	@PostMapping("/projetDecision")
	public String listeDeProjet(@RequestParam String DecisionAdmin, @RequestParam String id, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Long idAdmin = user.getPersonne().getId();
		model.addAttribute(ADMIN, user);

		List<Projet> listeDeprojetEnAttente = adminService.getProjetEnAttnte();
		List<Projet> listeDeProjetGestionnaireEnPrepa = adminService.getProjetgestionnaireEnPrepaCollecte(idAdmin);
		if (DecisionAdmin.contentEquals("accepter")) {
			adminService.accepterprojet(id, user);
		} else {
			adminService.rejeterProjet(id);
		}
		model.addAttribute("listeDeprojetEnAttente", listeDeprojetEnAttente);
		model.addAttribute("listeDeProjetGestionnaire", listeDeProjetGestionnaireEnPrepa);

		return "redirect:/admins/projet";

	}

	@PostMapping("/reconductionProjet")
	public String reconductionProjet(@RequestParam String DecisionAdmin, @RequestParam Saison saison,
			@RequestParam String idProjet, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Long idAdmin = user.getPersonne().getId();
		model.addAttribute(ADMIN, user);

		List<Projet> listeDeprojetEnAttente = adminService.getProjetEnAttnte();
		List<Projet> listeDeProjetGestionnaireEnPrepa = adminService.getProjetgestionnaireEnPrepaCollecte(idAdmin);
		if (DecisionAdmin.contentEquals("Reconduir")) {
			adminService.Reconduirprojet(idProjet, user, saison);
		} else {
			adminService.changementEtatProjetById(idProjet, EtatProjet.TERMINE);
		}
		model.addAttribute("listeDeprojetEnAttente", listeDeprojetEnAttente);
		model.addAttribute("listeDeProjetGestionnaire", listeDeProjetGestionnaireEnPrepa);

		return "redirect:/admins/projet";

	}

	private Cycle getLastCycleFromProjet(Projet projet) {
		Cycle cycle = projet.getCycle().stream().max(Comparator.comparingLong(e -> e.getId())).get();
		return cycle;
	}

	@PostMapping("/detailProjetPreparation")
	public String detailProjetEnPrepaDeCollecte(@RequestParam String idProjet, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Long idEspaceAdmin = user.getPersonne().getId();
		model.addAttribute(ADMIN, user);
		List<Projet> listeDeprojetEnAttente = adminService.getProjetEnAttnte();
		List<Projet> listeDeProjetGestionnaireEnPrepa = adminService
				.getProjetgestionnaireEnPrepaCollecte(idEspaceAdmin);
		Long idProj = Long.parseLong(idProjet);
		Projet projet = projetRepo.findById(idProj);
		Cycle cycle = getLastCycleFromProjet(projet);
		Collecte collecte = donsService.getCollecteIdCycle(cycle.getId());
		ContenuCollecte contenuCollecte = contenuCollecteRepo.getContenuCollecteByCollecteId(collecte.getId());

		model.addAttribute("Projet", projet);
		model.addAttribute("Cycle", cycle);
		model.addAttribute("Collecte", collecte);
		model.addAttribute("ContenueCollecte", contenuCollecte);

		return "gestionnaire_editionProjet";

	}

	@PostMapping("/decisionProjetEnCollecte")
	public String changementEtatProjetEnPrepaDeCollecte(@RequestParam String DecisionGestionnaire,
			@RequestParam String idProjet, @RequestParam String idCollecte, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Long idEspaceAdmin = user.getPersonne().getId();
		model.addAttribute(ADMIN, user);
		List<Projet> listeDeprojetEnAttente = adminService.getProjetEnAttnte();
		List<Projet> listeDeProjetGestionnaireEnPrepa = adminService
				.getProjetgestionnaireEnPrepaCollecte(idEspaceAdmin);

		if (DecisionGestionnaire.contentEquals("valider")) {
			adminService.accepterProjetEnCollecte(idProjet, idCollecte);
		} else {
			adminService.refuserProjetEnCollecte(idProjet);
		}
		model.addAttribute("listeDeprojetEnAttente", listeDeprojetEnAttente);
		model.addAttribute("listeDeProjetGestionnaire", listeDeProjetGestionnaireEnPrepa);

		return "redirect:/admins/projet";

	}

	@GetMapping("/messagerieGestionnaire")
	public String messagerieGestionnaire(Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);
		List<Projet> listeDeProjetSuiviParLeGestionnaire = adminService
				.getProjetSuiviParGestionnaire(user.getPersonne().getId());
		model.addAttribute("listeDeprojet", listeDeProjetSuiviParLeGestionnaire);

		return "gestionnaire_messagerie";

	}

	@GetMapping("/discussion/{idPorteur}")
	public String discussionGestionnaire(@PathVariable("idPorteur") String idPorteur, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);
		EspaceUtilisateur espacePorteur = userRepo.getEspaceUserParIdPersonne(Long.parseLong(idPorteur));
		Discussion discussion = discussionRepo.getDiscussionGestionnairePorteur(espacePorteur.getId(), user.getId());

		// Mettre les message de la discussion en lus
		adminService.MettreMessageEnlus(discussion);
		// Liste de message d'une discussion
		List<Message> listeMessage = adminService.getMessageDiscussion(discussion.getId());
		// ListeDeMessage par personne
		List<Message> listeMessagePorteur = messageRepo.getMessagePersonneDansUneDiscussion(discussion.getId(),
				espacePorteur.getId());
		List<Message> listeMessageGestionnaire = messageRepo.getMessagePersonneDansUneDiscussion(discussion.getId(),
				user.getId());

		model.addAttribute("Discussion", discussion);
		model.addAttribute("Porteur", espacePorteur);
		model.addAttribute("ListeDeMessage", listeMessage);
		model.addAttribute("ListeDeMessageDuPorteur", listeMessagePorteur);
		model.addAttribute("ListeDeMessageDuGestionnaire", listeMessageGestionnaire);

		return "gestionnaire_discussion";
	}

	@PostMapping("/envoieMesssage")
	public String EnvoieMessage(@RequestParam String Message, @RequestParam String idDiscussion,
			@RequestParam String iDPorteur, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);
		adminService.envoyerMessage(Message, idDiscussion, user);

		EspaceUtilisateur espacePorteur = userRepo.getEspaceUserParIdPersonne(Long.parseLong(iDPorteur));
		Discussion discussion = discussionRepo.getDiscussionGestionnairePorteur(espacePorteur.getId(), user.getId());
		List<Message> listeMessage = adminService.getMessageDiscussion(discussion.getId());
		model.addAttribute("Discussion", discussion);
		model.addAttribute("Porteur", espacePorteur);
		model.addAttribute("ListeDeMessage", listeMessage);

		return "redirect:/admins/discussion/" + iDPorteur;

	}

	// --------------------------------------------------- //
	// DIRECTEUR //
	// --------------------------------------------------- //

	@GetMapping("/financementProjet")
	public String ProjetEnFinDeCollecte(Model model) {
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);

		List<Collecte> listeDeProjetEnFinDeCollecte = adminService
				.getProjetFinDeCollecte(EtatProjet.EN_CAMPAGNE_DE_COLLECTE);
		List<Collecte> listC = ccmc.getCollectesEnCoursSurDernierCycle().stream()
				.filter(c -> c.getDateCloture().before(new Date())).collect(Collectors.toList());

		List<ContenuCollecte> listeContenueCollecteByIdCollecte = adminService.getListContenuCollecte(listC);

		model.addAttribute("ListeDeCollecte", listC);
		model.addAttribute("ListeDeContenuCollecte", listeContenueCollecteByIdCollecte);
		return "directeur_projet";
	}

	@PostMapping("/finDeFinancementD")
	public String ProjetEnFinDeCollecte(@RequestParam String idProjet, @RequestParam String ChoixDirecteur,
			Model model) {
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);
		List<Collecte> listeDeProjetEnFinDeCollecte = adminService
				.getProjetFinDeCollecte(EtatProjet.EN_CAMPAGNE_DE_COLLECTE);
		model.addAttribute("ListeDeCollecte", listeDeProjetEnFinDeCollecte);

		if (ChoixDirecteur.contentEquals("valider")) {

			adminService.changementEtatProjetById(idProjet, EtatProjet.EN_PREPARATION_DE_MISSION);
		} else {
			adminService.changementEtatProjetById(idProjet, EtatProjet.CLOS_EN_ECHEC);
		}

		return "redirect:/admins/financementProjet";

	}

	@GetMapping("/employes")
	public String ListeEmployes(Model model) {
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Long idAdmin = user.getPersonne().getId();
		model.addAttribute(ADMIN, user);

		List<Personne> listeDePersonne = adminService.getListeEmployes(user.getPersonne().getId());
		List<TypeRole> enumRole = Arrays.asList(TypeRole.values());
		System.out.println(idAdmin);
		model.addAttribute("listeDeRole", enumRole);
		model.addAttribute("listeDePersonne", listeDePersonne);

		return "directeur_listeEmployes";
	}

	@PostMapping("/etatEmployes")
	public String chagementEtatEmployes(@Valid TypeRole role, @Valid String idEmploye, Model model) {
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);

		System.out.println(role);
		System.out.println(idEmploye);
		adminService.changementRolePersonneById(idEmploye, role);

		return "redirect:/admins/employes";

	}

	@GetMapping("/statistique")
	public String statistique(Model model) {
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);
		return "directeur_statitique";
	}

	// --------------------------------------------------- //
	// CHARGE DE COM //
	// --------------------------------------------------- //

	@GetMapping("/support")
	public String listeDeMessageSupport(Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);
		List<Message> listeMessage = adminService.getMessageDiscussionSupport(user.getId());
		List<Message> listeMessageNonLus = adminService.getMessageDiscussionSupportNonLus(user.getId());
		model.addAttribute("listeDeMessage", listeMessage);
		model.addAttribute("listeDeMessageNonLus", listeMessageNonLus);
		return "chargeCom_messageSupport";
	}

	@GetMapping("/discussionChargeCom/{idDiscussion}")
	public String DiscussionSupport(@PathVariable("idDiscussion") Long idDiscussion, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);
		List<Message> listeMessage = adminService.getMessageDiscussion(idDiscussion);
		adminService.SetMessageEnLus(listeMessage, user);
		model.addAttribute("listeDeMessage", listeMessage);
		model.addAttribute("idDiscussion", idDiscussion);

		return "chargeCom_DiscussionSupport";
	}

	@PostMapping("/envoieMesssageSupport")
	public String EnvoieMessageSupport(@RequestParam String Message, @RequestParam String idDiscussion, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);
		adminService.envoyerMessage(Message, idDiscussion, user);
		List<Message> listeMessage = adminService.getMessageDiscussion(Long.parseLong(idDiscussion));
		adminService.SetMessageEnLus(listeMessage, user);
		model.addAttribute("listeDeMessage", listeMessage);
		model.addAttribute("idDiscussion", idDiscussion);

		return "redirect:/admins/discussionChargeCom/" + idDiscussion;

	}

	@GetMapping("/getMembre")
	public String AfficherListeMembre(Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);

		List<Personne> listePersonne = adminService.getListeUtilisateur();

		List<EspaceUtilisateur> ListeEspaceUser = adminService.getEspaceUtilisateurParListePersonne(listePersonne);
		model.addAttribute("listeDePersonne", listePersonne);
		model.addAttribute("listeEspaceUtilisateur", ListeEspaceUser);
		return "chargeCom_ListeMembres";
	}

	@GetMapping("/supprimerMembre/{idPersonne}") // IL FAUT SUPPRIMER LES COMMENTAIRES D'UNE PERSONNE !!!!
	public String SuppressionCompteMembre(@PathVariable("idPersonne") Long idPersonne, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);
		List<Personne> listePersonne = adminService.getListeUtilisateur();
		List<EspaceUtilisateur> ListeEspaceUser = adminService.getEspaceUtilisateurParListePersonne(listePersonne);

		adminService.SupprimerCompte(idPersonne);
		model.addAttribute("listeDePersonne", listePersonne);
		model.addAttribute("listeEspaceUtilisateur", ListeEspaceUser);

		return "redirect:/admins/getMembre";

	}

	@GetMapping("/commentaires")
	public String AfficherDeCommentaire(Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);

		List<Commentaire> listeDeCommentaire = adminService.getListeCommentaires();
		model.addAttribute("listeDeCommentaire", listeDeCommentaire);
		return "chargeCom_ListeCommentaires";
	}

	@GetMapping("/supprimerCommentaire/{idCommentaire}")
	public String SuppressionCommentaire(@PathVariable("idCommentaire") Long idCommentaire, Model model) {

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		model.addAttribute(ADMIN, user);

		adminService.SupprimerCommentaire(idCommentaire);
		List<Commentaire> listeDeCommentaire = adminService.getListeCommentaires();
		model.addAttribute("listeDeCommentaire", listeDeCommentaire);

		return "redirect:/admins/commentaires";

	}

}
