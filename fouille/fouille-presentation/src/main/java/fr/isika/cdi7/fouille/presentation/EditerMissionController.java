package fr.isika.cdi7.fouille.presentation;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import fr.isika.cdi7.fouille.model.Collecte;
import fr.isika.cdi7.fouille.model.ContenuMission;
import fr.isika.cdi7.fouille.model.Cycle;
import fr.isika.cdi7.fouille.model.Discussion;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;
import fr.isika.cdi7.fouille.model.EtatProjet;
import fr.isika.cdi7.fouille.model.Message;
import fr.isika.cdi7.fouille.model.Mission;
import fr.isika.cdi7.fouille.model.Projet;
import fr.isika.cdi7.fouille.model.form_objects.MissionContenueMissionDto;
import fr.isika.cdi7.fouille.services.AdminService;
import fr.isika.cdi7.fouille.services.ConsulterProjetService;
import fr.isika.cdi7.fouille.services.CycleService;
import fr.isika.cdi7.fouille.services.ProjetService;
import fr.isika.cdi7.fouille.services.UserService;

@Controller
public class EditerMissionController {
	@Autowired
	private ConsulterProjetService consulterService;
	@Autowired
	private ProjetService projetService;
	@Autowired
	private CycleService cycleService;
	@Autowired
	private AdminService adminService;

	@Autowired
	private UserService userService;

	@Autowired
	private ConsulterProjetService consulterProjetService;

	@GetMapping("/showMission")
	public String showMission(Model model, @RequestParam(name = "id") Long projetId) {
		Projet projet = projetService.findById(projetId);
		Cycle cycle = consulterProjetService.getDernierCycle(projetId).get();
		model.addAttribute("projet", projet);
		MissionContenueMissionDto missionContenueMissionDto = new MissionContenueMissionDto();

		if (cycle.getMission().getDuree() != null) {
			missionContenueMissionDto.setDuree(cycle.getMission().getDuree());
		} else {
			missionContenueMissionDto.setDuree(0);
		}
		if (cycle.getMission().getContenuMission().getNomMission() != null) {
			missionContenueMissionDto.setNomMission(cycle.getMission().getContenuMission().getNomMission());
		} else {
			missionContenueMissionDto.setNomMission("");
		}
		if (cycle.getMission().getContenuMission().getDescCourteMission() != null) {
			missionContenueMissionDto
					.setDescriptionCourteMission(cycle.getMission().getContenuMission().getDescCourteMission());
		} else {
			missionContenueMissionDto.setDescriptionCourteMission("");
		}
		if (cycle.getMission().getContenuMission().getDescLongueMission() != null) {
			missionContenueMissionDto
					.setDescriptionLongMission(cycle.getMission().getContenuMission().getDescLongueMission());
		} else {
			missionContenueMissionDto.setDescriptionLongMission("");
		}

		String auth = SecurityContextHolder.getContext().getAuthentication().getName();
		EspaceUtilisateur user = adminService.findIdAdmin(auth);
		Discussion discussion = userService.getDiscussionConseiller(user.getId());
		model.addAttribute("discussion", discussion);
		model.addAttribute("projet", projet);

		model.addAttribute("missionContenueMissionDto", missionContenueMissionDto);
		model.addAttribute("contenueMission", cycle.getMission().getContenuMission());
		model.addAttribute("mission", cycle.getMission());
		model.addAttribute("cycle", cycle);
		model.addAttribute("projetId", projet.getId());

		return "showMission";

	}

	@GetMapping("/editermission")
	public String editerMission(Model model, @RequestParam(name = "id") Long projetId) {
		Projet projet = projetService.findById(projetId);
		Cycle cycle = consulterProjetService.getDernierCycle(projetId).get();
		model.addAttribute("projet", projet);
		MissionContenueMissionDto missionContenueMissionDto = new MissionContenueMissionDto();

		if (cycle.getMission().getDuree() != null) {
			missionContenueMissionDto.setDuree(cycle.getMission().getDuree());
		} else {
			missionContenueMissionDto.setDuree(0);
		}
		if (cycle.getMission().getContenuMission().getNomMission() != null) {
			missionContenueMissionDto.setNomMission(cycle.getMission().getContenuMission().getNomMission());
		} else {
			missionContenueMissionDto.setNomMission("");
		}
		if (cycle.getMission().getContenuMission().getDescCourteMission() != null) {
			missionContenueMissionDto
					.setDescriptionCourteMission(cycle.getMission().getContenuMission().getDescCourteMission());
		} else {
			missionContenueMissionDto.setDescriptionCourteMission("");
		}
		if (cycle.getMission().getContenuMission().getDescLongueMission() != null) {
			missionContenueMissionDto
					.setDescriptionLongMission(cycle.getMission().getContenuMission().getDescLongueMission());
		} else {
			missionContenueMissionDto.setDescriptionLongMission("");
		}

		model.addAttribute("MissionContenueMissionDto", missionContenueMissionDto);
		model.addAttribute("projetId", projet.getId());
		model.addAttribute("contenueMissionId", cycle.getMission().getContenuMission().getId());

		return "editerMission";

	}

	private Cycle getLastCycleFromProjet(Projet projet) {
		Cycle cycle = projet.getCycle().stream().max(Comparator.comparingLong(e -> e.getId())).get();
		return cycle;
	}

	@PostMapping("/editermission")
	public String editerMission(Model model,
			@ModelAttribute("MissionContenueMissionDto") MissionContenueMissionDto missionContenueMissionDto,
			@RequestParam(name = "id") Long projetId) throws IOException {

		cycleService.editerMission(missionContenueMissionDto, projetId);
		return "redirect:showMission?id=" + projetId;

	}

	@GetMapping(value = "/missionImage")
	@ResponseBody
	public byte[] afficherdescriptionImage(@RequestParam(name = "contenueMissionId") Long contenuMissionId) {
		ContenuMission cm = consulterService.getContenuMissionById(contenuMissionId);
		return cm.getImage();
	}

	@PostMapping("/changerStatus")
	public String changerStatus(Model model, @RequestParam(name = "id") Long idCycle) {

		projetService.MiseEnLigneDuneMission(idCycle);

		return "redirect:compte/monCompte";
	}

}