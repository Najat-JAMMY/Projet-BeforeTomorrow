package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.cdi7.fouille.model.Commentaire;

public interface CommentaireRepository extends CrudRepository<Commentaire, Long> {

	@Query("Select c from Commentaire c where c.projet.id = :idProjetParam")
	List<Commentaire> getCommentairesCollecteParProjetId(@Param("idProjetParam") Long idProjet);
	
	@Query("Select c from Commentaire c where c.actualite.id = :idActualiteParam")
	List<Commentaire> getCommentairesMissionParActualite(@Param("idActualiteParam") Long idActualite);
 	

}
