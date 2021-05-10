package fr.isika.cdi7.fouille.dao;

import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

@Repository
public class DefaultDao {

	private static final Logger LOGGER = Logger.getLogger(DefaultDao.class.getSimpleName()); 
	
	public DefaultDao() {
		LOGGER.info("DefaultDao instance created");
	}
	
}
