package fr.isika.cdi7.fouille.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import fr.isika.cdi7.fouille.model.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

	@Query("SELECT m from Message m where m.discussion.id = :idDiscussion")
	List<Message> getMessageDiscussion(Long idDiscussion);

	@Query("SELECT m from Message m where m.discussion.id = :idDiscussion and m.personne.id != :idStaff")
	List<Message> getMessageDiscussionSupport(Long idDiscussion, Long idStaff);

	@Query("SELECT m from Message m where m.discussion.id = :idDiscussion and m.personne.id != :idStaff and m.lectureStaff = 0")
	List<Message> getMessageDiscussionSupportNonLus(Long idDiscussion, Long idStaff);

	@Query("SELECT m from Message m where m.discussion.id = :idDiscussion and m.personne.id = :idPersonne")
	List<Message> getMessagePersonneDansUneDiscussion(Long idDiscussion, Long idPersonne);

	@Query("SELECT m from Message m where m.discussion.id = :idDiscussion ORDER BY m.id")
	List<Message> getMessageDiscussionTrierParId(Long idDiscussion);

}
