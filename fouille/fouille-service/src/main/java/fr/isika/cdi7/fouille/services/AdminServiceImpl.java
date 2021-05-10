package fr.isika.cdi7.fouille.services;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.isika.cdi7.fouille.dao.AdresseRepository;
import fr.isika.cdi7.fouille.dao.CollecteRepository;
import fr.isika.cdi7.fouille.dao.CommentaireRepository;
import fr.isika.cdi7.fouille.dao.ContenuCollecteRepository;
import fr.isika.cdi7.fouille.dao.ContenuMissionRepository;
import fr.isika.cdi7.fouille.dao.CycleRepository;
import fr.isika.cdi7.fouille.dao.DiscussionRepository;
import fr.isika.cdi7.fouille.dao.DossierAdministratifRepository;
import fr.isika.cdi7.fouille.dao.EspaceUtilisateurRepository;
import fr.isika.cdi7.fouille.dao.MessageRepository;
import fr.isika.cdi7.fouille.dao.MissionRepository;
import fr.isika.cdi7.fouille.dao.PersonneRepository;
import fr.isika.cdi7.fouille.dao.ProjetRepository;
import fr.isika.cdi7.fouille.model.Adresse;
import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.Commentaire;
import fr.isika.cdi7.fouille.model.ContenuCollecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.DossierAdministratif;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Message;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Personne;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.Saison;
import fr.isika.cdi7.fouille.model.TypeRole;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private EspaceUtilisateurRepository espaceUserRepo;
	
	@Autowired
	private ProjetRepository projetRepo;
	
	@Autowired
	private CycleRepository cycleRepo;
	
	@Autowired
	private CollecteRepository collecteRepo;
	
	@Autowired
	private ContenuCollecteRepository contenuCollecteRepository;
	
	@Autowired
	private DossierAdministratifRepository dossierAdministratifRepository;
	
	@Autowired
	private PersonneRepository personneRepository;
	
	@Autowired
	private DiscussionRepository discussionRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	
	@Autowired
	private MissionRepository missionRepository;
	
	
	@Autowired
	private ContenuMissionRepository contenuMissionRepository;

	@Autowired
	private CommentaireRepository commentaireRepo;

	@Autowired
	private AdresseRepository adresseRepo;

	
	@Override
	public EspaceUtilisateur findIdAdmin(String email) {
		return espaceUserRepo.findByEmail(email);
	}


	@Override
	public Long getIdByEmail(String email) {
		return espaceUserRepo.getIdByEmail(email);
	}


	@Override
	public List<Projet> getProjetEnAttnte() {
		return projetRepo.findAll().stream().filter(p -> p.getEtat() == EtatProjet.EN_ATTENTE_DE_TRAITEMENT).collect(Collectors.toList());
		
	}

	@Override
	public List<Projet> getProjetgestionnaireEnPrepaCollecte(Long idAdmin) {
		
		return projetRepo.findAll().stream().filter(p -> p.getEtat() == EtatProjet.EN_PREPARATION_DE_COLLECTE && p.getGestionnaire().getId() == idAdmin).collect(Collectors.toList());
	}
	
	@Override
	public List<Projet> getProjetGestionnaireCollecte(Long idAdmin) {
		return projetRepo.findAll().stream().filter(p -> p.getEtat() == EtatProjet.EN_CAMPAGNE_DE_COLLECTE && p.getGestionnaire().getId() == idAdmin).collect(Collectors.toList());
	}
	
	@Override
	public List<Cycle> getProjetGestionnaireFinDeMission(EtatProjet enMission, Long idAdmin) {
//		List<Projet> projet = projetRepo.getProjetParEtat(enMission, idAdmin);
//		List<Cycle> Listecycle = getLastCycleFromProjet(projet);
//		cycleRepo.getCycleFinDeMission(Listecycle);
		Date dateDuJour = new Date();
		List<Cycle> listeDeCycle = cycleRepo.getCycleFinDeMission(dateDuJour,idAdmin,enMission );
		return listeDeCycle;
	}
	
	
	@Override
	public List<Collecte> getProjetFinDeCollecte(EtatProjet enCampagneDeCollecte) {
		Date dateDuJour = new Date();
		EtatProjet etat = EtatProjet.EN_CAMPAGNE_DE_COLLECTE;
		return collecteRepo.getProjetEnFinDeCollecte(dateDuJour,etat);
	}
	
	
	@Override
	public List<ContenuCollecte> getListContenuCollecte(List<Collecte> listeDeProjetEnFinDeCollecte) {
		List<ContenuCollecte> listeContenu = new ArrayList();
		for (Collecte collecte : listeDeProjetEnFinDeCollecte) {
			if (collecte == null)break;
			ContenuCollecte contenu = contenuCollecteRepository.getContenuCollecteByCollecteId(collecte.getId());
			listeContenu.add(contenu);
		}
		return listeContenu;
	}
	
	@Override
	public List<Projet> getProjetGestionnaireParEtat(EtatProjet etat,Long idAdmin) {
		return projetRepo.getProjetParEtat(etat,idAdmin);
	}
	
	@Override
	public List<Projet> getProjetGestionnaireEnAttente(EtatProjet etat) {
		return projetRepo.getProjetEnAttente(etat);
	}
	

	
	
	@Override
	public List<Projet> getProjetSuiviParGestionnaire(Long idAdmin) {
		return projetRepo.getProjetGestionnaire(idAdmin);
	}

	
	public void changementEtatProjet(String idProjet, EtatProjet etat) {
		
		Projet projet = projetRepo.findById(Long.parseLong(idProjet));
		projet.setEtat(etat);
		projetRepo.save(projet);
	
	}

	@Override
	public void accepterprojet(String idProjet, EspaceUtilisateur espaceAdmin) {
		
		changementEtatProjet(idProjet, EtatProjet.EN_PREPARATION_DE_COLLECTE);
		
		Long id= Long.parseLong(idProjet);
		Projet projet = projetRepo.findById(id);
		
		projet.setGestionnaire(espaceAdmin.getPersonne());
		
		Cycle cycle = new Cycle();
		cycle.setProjet(projet);
		cycle.setSaison(Saison.UNE);
		cycleRepo.save(cycle);
		
		Collecte collecte = new Collecte();
		collecte.setCycle(cycle);
		collecteRepo.save(collecte);
		
		ContenuCollecte contenuCollecte = new ContenuCollecte();
		contenuCollecte.setCollecte(collecte);
		contenuCollecteRepository.save(contenuCollecte);


		Adresse adresse = new Adresse();
		adresseRepo.save(adresse);
		
		Mission mission = new Mission();
		mission.setCycle(cycle);
		mission.setLocalisation(adresse);
		missionRepository.save(mission);
		
		ContenuMission contenuMission = new ContenuMission();
		contenuMission.setMission(mission);
		contenuMissionRepository.save(contenuMission);
		
		DossierAdministratif dossier = new DossierAdministratif();
		dossier.setProjet(projet);
		dossierAdministratifRepository.save(dossier);
		
		projet.setDossierAdministratif(dossier);
		
		EspaceUtilisateur espacePorteur = espaceUserRepo.getEspaceUserParIdPersonne(projet.getPorteur().getId());
		
		Discussion discusion = new Discussion();
		discusion.setStaff(espaceAdmin);
		discusion.setUtilisateur(espacePorteur);
		discussionRepository.save(discusion);
		
	}
	
	@Override
	public void Reconduirprojet(String idProjet, EspaceUtilisateur espaceAdmin, Saison saison) {
		
		changementEtatProjet(idProjet, EtatProjet.EN_PREPARATION_DE_COLLECTE);
		
		Long id= Long.parseLong(idProjet);
		Projet projet = projetRepo.findById(id);
		
		projet.setGestionnaire(espaceAdmin.getPersonne());
		
		Cycle cycle = new Cycle();
		cycle.setProjet(projet);
		cycle.setSaison(getSaisonSuivante(saison));
		cycleRepo.save(cycle);
		
		Collecte collecte = new Collecte();
		collecte.setCycle(cycle);
		collecteRepo.save(collecte);
		
		ContenuCollecte contenuCollecte = new ContenuCollecte();
		contenuCollecte.setCollecte(collecte);
		contenuCollecteRepository.save(contenuCollecte);
		
		Adresse adresse = new Adresse();
		adresseRepo.save(adresse);
		
		Mission mission = new Mission();
		mission.setCycle(cycle);
		mission.setLocalisation(adresse);
		missionRepository.save(mission);

		ContenuMission contenuMission = new ContenuMission();
		contenuMission.setMission(mission);
		contenuMissionRepository.save(contenuMission);
		
	}


	@Override
	public void rejeterProjet(String idProjet) {
		changementEtatProjet(idProjet, EtatProjet.REJETE);
	}


	@Override
	public void accepterProjetEnCollecte(String idProjet,String idCollecte) {
		Collecte collecte = collecteRepo.findById(Long.parseLong(idCollecte));
		collecte.setDateDebut(new Date());
		
		if(collecte.getDuree() != null) {
			  Date dat = new Date();
			  Calendar cal = Calendar.getInstance();
			  cal.add(Calendar.DAY_OF_MONTH, collecte.getDuree());  
			  collecte.setDateCloture(cal.getTime());
			
		}
	 
		 collecteRepo.save(collecte);
		changementEtatProjet(idProjet, EtatProjet.EN_CAMPAGNE_DE_COLLECTE);
	}


	@Override
	public void refuserProjetEnCollecte(String idProjet) {
		changementEtatProjet(idProjet, EtatProjet.ANNULE);
	}


	@Override
	public void envoyerMessage(String message, String idDiscussion, EspaceUtilisateur user) {
		
		Discussion discussion = discussionRepository.findById(Long.parseLong(idDiscussion));
		Optional<EspaceUtilisateur> espaceUer = espaceUserRepo.findById(user.getId());
		
		Message messageAEnvoyer = new Message();
		messageAEnvoyer.setMessage(message);
		messageAEnvoyer.setDate(new Date());
		messageAEnvoyer.setDiscussion(discussion);
		messageAEnvoyer.setPersonne(espaceUer.get());
		
		if(user.getPersonne().getRole() == TypeRole.ROLE_UTILISATEUR) {
			messageAEnvoyer.setLectureStaff(false);
			messageAEnvoyer.setLectureUtilisateur(true);
		}
		else {
			
			messageAEnvoyer.setLectureStaff(true);
			messageAEnvoyer.setLectureUtilisateur(false);
		}
		
		messageRepository.save(messageAEnvoyer);
	}


	@Override
	public void MettreMessageEnlus(Discussion discussion) {
		List<Message> listeMessage = messageRepository.getMessageDiscussion(discussion.getId());
		
		for (Message message : listeMessage) {
			message.setLectureStaff(true);
			messageRepository.save(message);
		}
	}


	@Override
	public void changementEtatProjetById(String idProjet, EtatProjet etat) {
		Projet projet = projetRepo.findById(Long.parseLong(idProjet));
		projet.setEtat(etat);
		projetRepo.save(projet);
		
	}


	@Override
	public List<Discussion> getDiscussionSupport() {
		return	discussionRepository.getDiscussionSupport();
		
	}


	@Override
	public List<Message> getMessageDiscussionSupport(Long idStaff) {
		List<Discussion> listeDiscussion = 	discussionRepository.getDiscussionSupport();
		List<Message> listeDeMessage = new ArrayList();
		for (Discussion discussion : listeDiscussion) {
			listeDeMessage.addAll(messageRepository.getMessageDiscussionSupport(discussion.getId(),idStaff));
		}
		return listeDeMessage;
	}
	

	@Override
	public List<Message> getMessageDiscussionSupportNonLus(Long idStaff) {
		
		List<Discussion> listeDiscussion = 	discussionRepository.getDiscussionSupport();
		List<Message> listeDeMessage = new ArrayList();
		for (Discussion discussion : listeDiscussion) {
			listeDeMessage.addAll(messageRepository.getMessageDiscussionSupportNonLus(discussion.getId(),idStaff));
		}
		return listeDeMessage;
		
	}


	@Override
	public List<Message> getMessageDiscussion(Long idDiscussion) {
		
		
		//Optional<Discussion> dis = discussionRepository.findById(idDiscussion);
		// Set<Message>  listeMessage = dis.get().getMessages();
		List<Message> listeMessage = messageRepository.getMessageDiscussionTrierParId(idDiscussion);
		 return listeMessage;
	}


	@Override
	public void SetMessageEnLus(List<Message> listeMessage, EspaceUtilisateur user) {
		for (Message message : listeMessage) {
			if(user.getPersonne().getRole() == TypeRole.ROLE_UTILISATEUR) {
				message.setLectureUtilisateur(true);
				messageRepository.save(message);
				
			}
			else {
				message.setLectureStaff(true);
				messageRepository.save(message);
			}
		}
		
	}


	@Override
	public List<Personne> getListeUtilisateur() {
		
		return personneRepository.getUtilisateur();
		
	}


	@Override
	public List<EspaceUtilisateur> getEspaceUtilisateurParListePersonne(List<Personne> listePersonne) {
		List<EspaceUtilisateur> listeEspace = new ArrayList();
		
		for (Personne personne : listePersonne) {
			EspaceUtilisateur espace = espaceUserRepo.getEspaceUserParIdPersonne(personne.getId());
			listeEspace.add(espace);
			
		}
		return listeEspace;
	}


	@Override
	public void SupprimerCompte(Long idPersonne) {	
		Optional<Personne> personne = personneRepository.findById(idPersonne);
		EspaceUtilisateur espace = espaceUserRepo.getEspaceUserParIdPersonne(personne.get().getId());
		espaceUserRepo.delete(espace);
		personneRepository.delete(personne.get());
		
	}


	@Override
	public List<Commentaire> getListeCommentaires() {
		return (List<Commentaire>) commentaireRepo.findAll();
	}


	@Override
	public void SupprimerCommentaire(Long idCommentaire) {
		Optional<Commentaire> com= commentaireRepo.findById(idCommentaire);
		commentaireRepo.delete(com.get());
		
	}


	@Override
	public List<Personne> getListeEmployes(Long id) {
		return personneRepository.getEmployes(id);
	
	}
	
	@Override
	public void changementRolePersonneById(String idProjet, TypeRole role) {
		Optional<Personne> personne = personneRepository.findById(Long.parseLong(idProjet));
		personne.get().setRole(role);
		personneRepository.save(personne.get());
		
	}


	public List<Cycle> getLastCycleFromProjet(List<Projet> Listeprojet) {
		List<Cycle> listeDeCycle = new ArrayList();
		for (Projet projet : Listeprojet) {
			listeDeCycle.add(projet.getCycle().stream().max(Comparator.comparingLong(e -> e.getId())).get());
		}
		return listeDeCycle;
	} 
	
private Saison getSaisonSuivante(Saison saison) {

	Saison saisonReturn = null;
	if(saison == Saison.UNE) {
		saisonReturn =Saison.DEUX;
	}else if(saison == Saison.DEUX){
		saisonReturn =Saison.TROIS;
	}else if(saison == Saison.TROIS){
		saisonReturn =Saison.QUATRE;
	}
	else if(saison == Saison.QUATRE){
		saisonReturn =Saison.CINQ;
	}
	else if(saison == Saison.CINQ){
		saisonReturn =Saison.SIX;	
	}
	else if(saison == Saison.SIX){
		saisonReturn =Saison.SEPT;
	}
	else if(saison == Saison.SEPT){
		saisonReturn =Saison.HUIT;
		}
	else if(saison == Saison.HUIT){
		saisonReturn =Saison.NEUF;
	}
	else if(saison == Saison.NEUF){
		saisonReturn =Saison.DIX;
	}
	return saisonReturn;
}


}
