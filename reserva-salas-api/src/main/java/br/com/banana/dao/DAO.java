package br.com.banana.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Classe DAO genérica
 * 
 * @author vagner
 *
 * @param <T>
 */
public class DAO<T> implements Serializable {

	private static final long serialVersionUID = 3265575551784280498L;

	private final Class<T> classe;
	
	@PersistenceContext
	private EntityManager entityManager;

	public DAO(Class<T> classe) {
		this.classe = classe;
	}

	public void insere(T obj) {
		this.entityManager.persist(obj);
	}

	public void remove(T obj) {
		this.entityManager.remove(obj);
	}

	public void atualiza(T obj) {
		this.entityManager.merge(obj);
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = this.entityManager.getCriteriaBuilder().createQuery(classe);
		query.from(classe);
		return this.entityManager.createQuery(query).getResultList();
	}

	public T buscaPorId(Long id) {
		return this.entityManager.find(classe, id);
	}

}
