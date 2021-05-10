package fr.isika.cdi7.fouille.presentation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import fr.isika.cdi7.fouille.model.Actualite;
import fr.isika.cdi7.fouille.services.FouilleService;

@Controller
public class ActualitesController {
	@Autowired
	private FouilleService fouilleService;

	@GetMapping("/listActualites")
	public String getView(Model model) {
		List<Actualite> myActualites = fouilleService.listActualite();
		model.addAttribute("actualites", myActualites);

		return "actualitesList";
	}

}
