package fr.isika.cdi7.fouille.presentation;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.form_objects.EspaceUtilisateurForm;
import fr.isika.cdi7.fouille.model.form_objects.InscriptionForm;
import fr.isika.cdi7.fouille.services.EspaceUtilisateurService;
import fr.isika.cdi7.fouille.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
	private static final Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());

	public UserController() {
		LOGGER.info("UserController instance created ...");
	}

	@Autowired
	private UserService userService;
	
	@Autowired
	private EspaceUtilisateurService espaceUtilisateurService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/inscription")
	public String Inscription(Model model) {
		model.addAttribute("formInscription", new InscriptionForm());

		return "inscription";
	}

	@PostMapping("/inscription")
	public String ValiderFormulaire(@ModelAttribute("formInscription") InscriptionForm formInscription, Model model) {

		LOGGER.info("" + formInscription.getEmail());
		LOGGER.info("" + formInscription.getNom());
		LOGGER.info("" + formInscription.getEmail());

		if (userService.emailExists(formInscription.getEmail())) {
			String erreur = "Email existant";
			model.addAttribute("erreurMail", erreur);
			return "inscription";
		}

	userService.addUtilisateur(formInscription);

		return "redirect:/users/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/succes")
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException, ServletException {

        String role =  authResult.getAuthorities().toString();

        if(role.contains("ROLE_GESTIONNAIRE_PROJET") || role.contains("ROLE_DIRECTEUR") || role.contains("ROLE_CHARGE_COM")){
         response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/admins/admin"));                            
         }
         else if(role.contains("ROLE_UTILISATEUR")) {
             response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/compte/monCompte"));
         }
    }
	
	@GetMapping("/infoProfil")
	public String GetInfoPersonne(Model model, @RequestParam(name = "idUser") Long idUser) {

		EspaceUtilisateur eu = espaceUtilisateurService.getEspaceUserParIdPersonne(idUser);
		String emailDeTest = eu.getEmail();
		EspaceUtilisateurForm form = new EspaceUtilisateurForm();

		boolean exists = userService.emailExists(emailDeTest);
		if (exists) {
			EspaceUtilisateur espaceUtilisateur = espaceUtilisateurService.findByEmail(emailDeTest);
			if(espaceUtilisateur.getPersonne().getNumeroTel()==null) {
				form.setTelephone("");
			}else {
				form.setTelephone(String.valueOf(espaceUtilisateur.getPersonne().getNumeroTel()));
			}
			form.setEmail(espaceUtilisateur.getEmail());
			form.setNom(espaceUtilisateur.getPersonne().getNom());
			form.setPrenom(espaceUtilisateur.getPersonne().getPrenom());
			form.setIdUser(idUser+"");
			// form.setMotDePasse(espaceUtilisateur.getMotDePasse());
			
			//form.setTelephone(String.valueOf(espaceUtilisateur.getPersonne().getNumeroTel()));
		}
		model.addAttribute("espaceUtilisateurForm", form);
		return "espaceUtilisateur";
	}

	@PostMapping("/infoProfil")
	public String updateInfoPersonne(Model model, @RequestParam(name="idUser") Long idUser ,@ModelAttribute("espaceUtilisateurForm") EspaceUtilisateurForm form) {
		String emailDeTest=espaceUtilisateurService.getEspaceUserParIdPersonne(idUser).getEmail();
		boolean exists = userService.emailExists(emailDeTest);
		if (exists) {
			EspaceUtilisateur espaceUser = espaceUtilisateurService.findByEmail(emailDeTest);
			Personne p = espaceUser.getPersonne();
			p.setNom(form.getNom());
			p.setPrenom(form.getPrenom());
			p.setNumeroTel(form.getTelephone());
			
			espaceUtilisateurService.save(p);
			if (form.getMotDePasse() != null && !form.getMotDePasse().isEmpty()) {
				espaceUser.setMotDePasse(passwordEncoder.encode(form.getMotDePasse()));
				espaceUtilisateurService.save(espaceUser);
			}
		}

		return "redirect:/compte/monCompte";
		
	}

}
