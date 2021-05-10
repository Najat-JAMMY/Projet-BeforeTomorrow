package fr.isika.cdi7.fouille.dao.inmemory;

import java.util.List;
import java.util.Optional;

import fr.isika.cdi7.fouille.dao.EspaceUtilisateurRepository;
import fr.isika.cdi7.fouille.model.EspaceUtilisateur;

public class InMemoryEspaceUtilisateurRepository implements EspaceUtilisateurRepository {

	
	private List<EspaceUtilisateur> espaces;
	
	public InMemoryEspaceUtilisateurRepository() {
	}
	
	private void init() {
		espaces.add(new EspaceUtilisateur());
	}
	
	@Override
	public <S extends EspaceUtilisateur> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends EspaceUtilisateur> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<EspaceUtilisateur> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<EspaceUtilisateur> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(EspaceUtilisateur entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends EspaceUtilisateur> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EspaceUtilisateur> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getIdByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EspaceUtilisateur findByEmail(String email) {
		// TODO Auto-generated method stub
		return new EspaceUtilisateur();
	}

	@Override
	public EspaceUtilisateur findByEmails(String email, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EspaceUtilisateur getEspaceUserParIdPersonne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
