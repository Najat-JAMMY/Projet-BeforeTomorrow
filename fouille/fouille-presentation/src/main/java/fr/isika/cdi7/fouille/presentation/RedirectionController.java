package fr.isika.cdi7.fouille.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectionController {
	@GetMapping(path = { "/" })
	public String redirect() {
		return "redirect:accueil";
	}
}
