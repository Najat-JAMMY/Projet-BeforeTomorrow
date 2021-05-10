package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import fr.isika.cdi7.fouille.model.Favoris;

public interface FavorisRepository extends CrudRepository<Favoris,Long> {
	
	@Query("SELECT fav from Favoris fav  where fav.espaceUtilisateur.id = :id")
	List<Favoris> getFavorisPersonne(Long id);

	@Query("SELECT f from Favoris f where f.espaceUtilisateur.id = :idEspaceParam and f.projet.id = :idProjetParam")
	Favoris findByProjetIdAndPersonneId(@Param("idEspaceParam") Long idEspace, @Param("idProjetParam") Long idProjet);
}
