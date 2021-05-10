package fr.isika.cdi7.fouille.services;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class DefaultService {

	private static final Logger LOGGER = Logger.getLogger(DefaultService.class.getSimpleName());

	public DefaultService() {
		LOGGER.info("DefaultService instance created ...");
	}

}
