package fr.isika.cdi7.fouille.presentation;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.isika.cdi7.fouille.dao.DiscussionRepository;
import fr.isika.cdi7.fouille.dao.MessageRepository;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.Don;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Favoris;
import fr.isika.cdi7.fouille.model.Message;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.ConsulterProjetService;
import fr.isika.cdi7.fouille.services.ProjetService;
import fr.isika.cdi7.fouille.services.UserService;

@Controller
@RequestMapping("/compte")
public class UtilisateurController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ProjetService projetService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private DiscussionRepository discussionRpo;
	
	@Autowired
	private MessageRepository msgRpo;
	
	@Autowired
	private ConsulterProjetService consulterProjetService;
	
	
	
	
		@GetMapping("/monCompte")
		public String monCompte(Model model) {
			
			String auth = SecurityContextHolder.getContext().getAuthentication().getName();
//			if(!auth.isEmpty()) {
				EspaceUtilisateur user = adminService.findIdAdmin(auth);
				Long idPersonne = user.getPersonne().getId();
				Projet projet = null;
				projet = projetService.getProjetParIdPersonne(idPersonne);
				List<Favoris>ListeDeProjetEnFavoris = projetService.getProjetFavoris(user.getId());
				List<Cycle> listeDeCycleFavoris = projetService.getCycleByListFavoris(ListeDeProjetEnFavoris);
				List<Don> ListeDeDon = projetService.getDonDunePersonne(user.getPersonne().getId());
				
				
//			}
				if(projet != null) {
					
					if(projet.getEtat() != EtatProjet.EN_ATTENTE_DE_TRAITEMENT && projet.getEtat() != EtatProjet.REJETE) {
						
						Cycle cycle = consulterProjetService.getDernierCycleprojet(projet.getId());
						model.addAttribute("cycle",cycle );
					}
					
					Discussion discussion = userService.getDiscussionConseiller(user.getId());
		
					model.addAttribute("discussion",discussion );
					model.addAttribute("projet",projet );
				}
				
				model.addAttribute("listeFavoris",ListeDeProjetEnFavoris );
				model.addAttribute("listeDeCycleFavoris",listeDeCycleFavoris );
				
				for (Cycle c : listeDeCycleFavoris) {
					System.out.println(c.getId());
				}
				model.addAttribute("ListeDeDon",ListeDeDon );
				model.addAttribute("Porteur",user );
				
			return "utilisateur_compte";
		}
		
		@GetMapping("/messagerie")
		public String messagerie(Model model) {
			
			//Si porteur de projet -> afficher discussion avec gestionnaire 
			// Sinon afficher les discussionss puis les messages de la discussion
			
				String auth = SecurityContextHolder.getContext().getAuthentication().getName();

				EspaceUtilisateur user = adminService.findIdAdmin(auth);
				Long idPersonne = user.getPersonne().getId();
				Projet projet = projetService.getProjetParIdPersonne(idPersonne);
				
				if(projet != null) {
					Discussion discussion = userService.getDiscussionConseiller(user.getId());
					
					System.out.println(discussion.getId());
					List<Message> listeMessage = msgRpo.getMessageDiscussion(discussion.getId());
					
					model.addAttribute("projet",projet );
					model.addAttribute("discussion",discussion );
				   /// model.addAttribute("Discussion",Discussion );
					model.addAttribute("ListeDeMessage",listeMessage );
					model.addAttribute("Porteur",user );
					return "utilisateur_discussion";
				}
				
				List<Discussion> listeDiscussion = userService.getAllDiscussionUtilisatateur(user.getId());
				model.addAttribute("listeDiscussion",listeDiscussion );
				model.addAttribute("Porteur",user );
				
			return "utilisateur_messagerie";
		}
		
		
		
		
		
		@GetMapping("/messagerieSupport")
		public String messagerieSupport(Model model) {
			
			//Si porteur de projet -> afficher discussion avec gestionnaire 
			// Sinon afficher les discussionss puis les messages de la discussion
			
				String auth = SecurityContextHolder.getContext().getAuthentication().getName();

				EspaceUtilisateur user = adminService.findIdAdmin(auth);
				Long idPersonne = user.getPersonne().getId();
				Projet projet = projetService.getProjetParIdPersonne(idPersonne);
				Discussion discussion = userService.getDiscussionConseiller(user.getId());
				model.addAttribute("discussion",discussion );
				model.addAttribute("projet",projet );
				List<Discussion> listeDiscussion =  discussionRpo.findDiscussionSupportByIdUtilisateur(user.getId());  
//				userService.getAllDiscussionUtilisatateur(user.getId());
				model.addAttribute("listeDiscussion",listeDiscussion );
				model.addAttribute("Porteur",user );
				
			return "utilisateur_messagerie";
		}
		
		
	
		
		
		@GetMapping("/discussion")
		public String discussion(@RequestParam Long idDiscussion,Model model) {
			
					String auth = SecurityContextHolder.getContext().getAuthentication().getName();
					EspaceUtilisateur user = adminService.findIdAdmin(auth);
					List<Message> listeMessage = adminService.getMessageDiscussion(idDiscussion);
					
					Discussion discussion = discussionRpo.findById(idDiscussion).get();
					model.addAttribute("discussion",discussion );
					model.addAttribute("ListeDeMessage",listeMessage );
					model.addAttribute("Porteur",user );
				
					return "utilisateur_discussion";
		}
		
		
		@GetMapping("/discussionSupport")
		public String discussionSupport(@RequestParam Long idDiscussion,Model model) {
			
					String auth = SecurityContextHolder.getContext().getAuthentication().getName();
					EspaceUtilisateur user = adminService.findIdAdmin(auth);
					List<Message> listeMessage = adminService.getMessageDiscussion(idDiscussion);
					
				//	Discussion discussion = discussionRpo.findById(idDiscussion).get();
					Discussion discussion = userService.getDiscussionConseiller(user.getId());
					Discussion discussionSupport = discussionRpo.findById(idDiscussion).get();
					Long idPersonne = user.getPersonne().getId();
					Projet projet = projetService.getProjetParIdPersonne(idPersonne);
					
					
					model.addAttribute("projet",projet );
					model.addAttribute("discussion",discussion );
					model.addAttribute("discussionSupport",discussionSupport );
					model.addAttribute("ListeDeMessage",listeMessage );
					model.addAttribute("Porteur",user );
				
					return "utilisateur_discussionSupport";
		}
		
		
		
		@PostMapping("/envoieMesssageSupport")
		public String envoieMesssageSupport(@RequestParam String Message,@RequestParam String idDiscussion,Model model) {
			
					String auth = SecurityContextHolder.getContext().getAuthentication().getName();
					EspaceUtilisateur user = adminService.findIdAdmin(auth);
					adminService.envoyerMessage(Message,idDiscussion,user);
					return "redirect:/compte/discussionSupport/?idDiscussion=" + idDiscussion;
					
		}
		
		
		
		@PostMapping("/envoieMesssage")
		public String EnvoiMesssage(@RequestParam String Message,@RequestParam String idDiscussion,Model model) {
			
					String auth = SecurityContextHolder.getContext().getAuthentication().getName();
					EspaceUtilisateur user = adminService.findIdAdmin(auth);
					adminService.envoyerMessage(Message,idDiscussion,user);

					return "redirect:/compte/messagerie";
					
		}
		
		
		@GetMapping("/nouveauMessage")
		public String nouveauMessage(Model model) {
			
					String auth = SecurityContextHolder.getContext().getAuthentication().getName();

					EspaceUtilisateur user = adminService.findIdAdmin(auth);
					Long idPersonne = user.getPersonne().getId();
					Projet projet = projetService.getProjetParIdPersonne(idPersonne);
					Discussion discussion = userService.getDiscussionConseiller(user.getId());
					
					model.addAttribute("discussion",discussion );
					model.addAttribute("projet",projet );
					model.addAttribute("Porteur",user );
					return "utilisateur_nouveauMessageSupport";
		}
		
		@PostMapping("/nouveauMessage")
		public String envoieDunouveauMessageNouvelleDiscussion(@RequestParam String sujet, @RequestParam String message,Model model) {
			
					String auth = SecurityContextHolder.getContext().getAuthentication().getName();
					EspaceUtilisateur user = adminService.findIdAdmin(auth);
					Long idPersonne = user.getPersonne().getId();
					
					Projet projet = projetService.getProjetParIdPersonne(idPersonne);
					model.addAttribute("projet",projet );
					model.addAttribute("Porteur",user );
					Discussion idDiscussion = userService.creationDiscussionEtEnvoieMessage(sujet,message,user);
			
					return "redirect:/compte/discussionSupport/?idDiscussion=" + idDiscussion.getId();
		}
		
		
		
		@GetMapping("/contribution")
		public String contribution(Model model) {
			
					String auth = SecurityContextHolder.getContext().getAuthentication().getName();
					EspaceUtilisateur user = adminService.findIdAdmin(auth);
					Long idPersonne = user.getPersonne().getId();
					Projet projet = projetService.getProjetParIdPersonne(idPersonne);
					Discussion discussion = userService.getDiscussionConseiller(user.getId());	
					List<Don> ListeDeDon = projetService.getDonDunePersonne(user.getPersonne().getId());
					
					
					model.addAttribute("discussion",discussion );
					model.addAttribute("projet",projet );
					model.addAttribute("ListeDeDon",ListeDeDon );
					model.addAttribute("Porteur",user );
				
					return "utilisateur_don";
		}
		
		
		
		
		
		


}
