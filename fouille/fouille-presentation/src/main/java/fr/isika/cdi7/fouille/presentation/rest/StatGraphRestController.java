package fr.isika.cdi7.fouille.presentation.rest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.isika.cdi7.fouille.dao.DonRepository;
import fr.isika.cdi7.fouille.dao.ProjetRepository;
import fr.isika.cdi7.fouille.model.Don;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.form_objects.StatistiqueProjet;
import fr.isika.cdi7.fouille.services.AdminService;

@RestController
public class StatGraphRestController {
	
	private static final Logger LOGGER = Logger.getLogger(StatGraphRestController.class.getSimpleName());
	
	public StatGraphRestController() {
		LOGGER.info(" ******************************************************************* created");
	}
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ProjetRepository projetRepo;
	
	@Autowired
	private DonRepository donRepo;
	
	
	@GetMapping("/stat/coordsStatGraph")
	public List<StatGraph> all() {
		// etape finale envoi des donnees "fabriquees"
		return donneDeTest();
	}
	
	@GetMapping("/stat/coordsStatProjet")
	public List<StatProjet> allProjet() {
		// etape finale envoi des donnees "fabriquees"
		//return Arrays.asList( createDummyprojet(),createDummyprojet2(),createDummyprojet3());
		return donneeProjet();
		
	}
	
	
	private List<StatGraph> donneDeTest() {
	List<StatGraph> liste = new ArrayList<>();

//	for(int i=1;i<5;i++) {
//		liste.add(StatGraph.create("Value_" ,Long.valueOf( 4)));
//	}
//		
		List<Don> listeDeDon = donRepo.getNbDon();
		
			
			 Map<String, Long> counting = listeDeDon.stream().collect(
		                Collectors.groupingBy(Don::getMonthDate, Collectors.counting()));
			System.out.println(counting);
			for (Map.Entry<String, Long> entry : counting.entrySet()) {
				liste.add(StatGraph.create(entry.getKey().toString(),entry.getValue()));
			}
			
		
//			 Map<Date, Long> counting = listeDeDon.stream().collect(
//		                Collectors.groupingBy(Don::getDateDon, Collectors.counting()));
//			 
//			
//			System.out.println(counting);
//			
//			
//			for (Map.Entry<Date, Long> entry : counting.entrySet()) {
//				liste.add(StatGraph.create(entry.getKey().toString(),entry.getValue()));
//			}
			liste.add(StatGraph.create("Value_" ,Long.valueOf( 100)));
	  
		
		return liste;
	}
	
	private List<StatProjet> donneeProjet() {
		List<StatProjet> liste = new ArrayList<>();
		List<Projet> listeStatistiqueProjet = projetRepo.getNbProjetByEtat3();
		 Map<EtatProjet, Long> counting = listeStatistiqueProjet.stream().collect(
	                Collectors.groupingBy(Projet :: getEtat, Collectors.counting()));

		for (Map.Entry<EtatProjet, Long> entry : counting.entrySet()) {
			liste.add(StatProjet.create(entry.getKey().toString(),entry.getValue()));
		}
	
		return liste;
	}
	
	private List<StatistiqueProjet> methodetest(List<Projet> lste){
		List<StatistiqueProjet> liste = new ArrayList<>();
		
		
			return liste;
	}
	
	
//	
//	private StatProjet createDummyprojet() {
//		StatProjet statProjet = new StatProjet();
//		statProjet.setCountry("EN_ATTENTE");
//		statProjet.setLitres(102);
//	    return statProjet;
//	}
//	
//	private StatProjet createDummyprojet2() {
//		StatProjet statProjet = new StatProjet();
//		statProjet.setCountry("EN_COLLECTE");
//		statProjet.setLitres(252);
//	    return statProjet;
//	}
//				
//	
//	private StatProjet createDummyprojet3() {
//		StatProjet statProjet = new StatProjet();
//		statProjet.setCountry("EN_MISSION");
//		statProjet.setLitres(1082);
//	    return statProjet;
//	}
		
	
	

}
