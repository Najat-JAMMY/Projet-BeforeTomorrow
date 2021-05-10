package fr.isika.cdi7.fouille.services;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.DiscussionRepository;
import fr.isika.cdi7.fouille.dao.EspaceUtilisateurRepository;
import fr.isika.cdi7.fouille.dao.MessageRepository;
import fr.isika.cdi7.fouille.dao.PersonneRepository;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.Message;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.TypeRole;
import fr.isika.cdi7.fouille.model.form_objects.InscriptionForm;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private PersonneRepository personneRepository;
	
	@Autowired
	private DiscussionRepository discussionRepository;

	@Autowired
	private EspaceUtilisateurRepository espaceUtilisateurRepository;
	
	@Autowired
	private MessageRepository messageRepository;

	public UserServiceImpl(EspaceUtilisateurRepository espaceUtilisateurRepository) {
		this.espaceUtilisateurRepository = espaceUtilisateurRepository;
	}

	@Override
	public boolean emailExists(String email) {
		return espaceUtilisateurRepository.findByEmail(email) != null;
	}

	@Override
	public void addUtilisateur(InscriptionForm formInscription) {
		Personne personne = new Personne();
		personne.setNom(formInscription.getNom());
		personne.setPrenom(formInscription.getPrenom());
		personne.setRole(TypeRole.ROLE_UTILISATEUR);

		EspaceUtilisateur espace_utilisateur = new EspaceUtilisateur();
		espace_utilisateur.setEmail(formInscription.getEmail());
		espace_utilisateur.setMotDePasse(passwordEncoder.encode(formInscription.getMotDePasse()));
		espace_utilisateur.setPersonne(personne);
		espace_utilisateur.setDateCreation(new Date());

		personneRepository.save(personne);
		espaceUtilisateurRepository.save(espace_utilisateur);

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		EspaceUtilisateur user = espaceUtilisateurRepository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("L'email ou le mot de passe est invalide");
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getPersonne().getRole().toString()));

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getMotDePasse(),
				grantedAuthorities);

	}

	@Override
	public Discussion getDiscussion(Long id) {
		return discussionRepository.findByIdUtilisateur(id);
	}

	@Override
	public List<Discussion> getAllDiscussionUtilisatateur(Long id) {
		return discussionRepository.findDiscussionUtilisateurLambda(id);
	}

	@Override
	public Discussion creationDiscussionEtEnvoieMessage(String sujet, String message, EspaceUtilisateur user) {
	
		Discussion discussion = new Discussion();
		discussion.setSujet(sujet);
		discussion.setUtilisateur(user);
		discussionRepository.save(discussion);
		
		Message msg = new Message();
		msg.setDiscussion(discussion);
		msg.setLectureUtilisateur(true);
		msg.setMessage(message);
		msg.setPersonne(user);
		messageRepository.save(msg);
		return discussion;
	}

	@Override
	public Discussion getDiscussionConseiller(Long id) {
		return discussionRepository.findDiscussionConseillerByIdUtilisateur(id);
	}

	

}
