package fr.isika.cdi7.fouille.presentation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.isika.cdi7.fouille.dao.CollecteRepository;
import fr.isika.cdi7.fouille.dao.ContrePartieRepository;
import fr.isika.cdi7.fouille.dao.CycleRepository;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContrePartie;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.Don;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.ConsulterProjetService;
import fr.isika.cdi7.fouille.services.DonService;
import fr.isika.cdi7.fouille.services.ProjetService;
import fr.isika.cdi7.fouille.services.UserService;

@Controller
@RequestMapping("/don")
public class DonController {

	// ---------------------------------------- //
	// FAIRE UN DON //
	// ---------------------------------------- //

	@Autowired
	private DonService donService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CollecteRepository collecteRepo;
	
	@Autowired
	private ContrePartieRepository contrePartieRepository;
	@Autowired
	private ConsulterProjetService consulterProjetService;
	
	@Autowired
	private ConsulterProjetService consulterService;
	
	@Autowired
	private ProjetService projetService;

	@GetMapping("/faireUnDon")
	public String faireUnDon(@RequestParam Long idCollecte,Model model) {
		
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Personne personnne = donService.getUtilisateur(user.getPersonne().getId());
		Long idPersonne = user.getPersonne().getId();
		Projet projetUser = projetService.getProjetParIdPersonne(idPersonne);
		Discussion discussion = userService.getDiscussionConseiller(user.getId());
		
		
		model.addAttribute("discussion",discussion );
		model.addAttribute("Porteur",user );
		model.addAttribute("personne", user);
		model.addAttribute("projetUser", projetUser);

		
		Collecte collecte  = collecteRepo.findById(idCollecte).get();
		model.addAttribute("collecte", collecte);
		model.addAttribute("idCollecte", idCollecte);
		return "faireUnDon";
	}

	@PostMapping("/faireUnDon")
	public String faireUnDon(@RequestParam Long idCollecte,@RequestParam String montant_don, @RequestParam(required = false) String idContrePartie,
			Model model) {

		//Personne Connecte : 
		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Personne personnne = donService.getUtilisateur(user.getPersonne().getId());
		Long idPersonne = user.getPersonne().getId();
		Projet projetUser = projetService.getProjetParIdPersonne(idPersonne);
		Discussion discussion = userService.getDiscussionConseiller(user.getId());
		
		
		
		Collecte collecte  = collecteRepo.findById(idCollecte).get();
		Projet projet = donService.getProjet(collecte.getCycle().getProjet().getId());
		Cycle cycle = consulterProjetService.getDernierCycle(projet.getId()).get();
		ContenuCollecte cc = cycle.getCollecte().getContenuCollecte();
		
		
		int montantDon = Integer.parseInt(montant_don);
		List<ContrePartie> contreParties = donService.getListContrePartieTest(montantDon, collecte.getId());
		List<Don> ListeDeDon = projetService.getDonDunePersonne(user.getPersonne().getId());
		
		if(projet != null) {
			model.addAttribute("projet",projet );
		}
		
		model.addAttribute("discussion",discussion );
		model.addAttribute("Porteur",user );
		model.addAttribute("montant", montant_don);
		model.addAttribute("idContrePartie", idContrePartie);
		model.addAttribute("personne", user);
		model.addAttribute("projet", projet);
		model.addAttribute("projetUser", projetUser);
		model.addAttribute("contenuCollecte", cc);
		model.addAttribute("collecte", collecte);
		model.addAttribute("listeContrePartie", contreParties);

		return "recapDon";

	}
	
	@GetMapping(value = "/imageDon")
	@ResponseBody
	public byte[] afficherdescriptionImage(@RequestParam(name = "contenuCollecteId") Long contenuCollecteId) {
		ContenuCollecte cc = consulterService.getContenuCollecteParId(contenuCollecteId);
		return cc.getImage();
	}

	@PostMapping("/recapDon")
	public String validerDon(@RequestParam String idCollecte,
			@RequestParam(required = false) String radioCP , @RequestParam String montant, @RequestParam(required = false) String radioAno) {  //@RequestParam String idPersonne, 

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Personne personnne = donService.getUtilisateur(user.getPersonne().getId());
		
		Don don = new Don();
		int montantDon = Integer.parseInt(montant);
		if(!radioCP.equals("PasDeContrePartie")) {
			ContrePartie cp =contrePartieRepository.findById(Long.parseLong(radioCP)).get();
			don.setContrePartie(cp);
		}
		don.setMontant(montantDon);
		don.setPersonne(personnne);
		don.setDateDon(new Date());
		if(radioAno != null) {
			don.setAnonyme(true);
		}else {
			don.setAnonyme(false);
		}
		Collecte collecte = donService.getCollecteId(Long.parseLong(idCollecte));
		don.setCollecte(collecte);
		Don donSave = donService.save(don);
		donService.IncrementationSommeCollecte(donSave, collecte);

		return "validerDon";
	}

}
