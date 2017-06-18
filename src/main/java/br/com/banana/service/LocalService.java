package br.com.banana.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.banana.model.Local;
import br.com.banana.model.Sala;

/**
 * Serviço REST para locais
 * 
 * @author vagner
 *
 */
@Stateless
@Path("/locais")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class LocalService {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Lista todos os locais
	 * 
	 * @return Response
	 */
	@GET
	public Response listaTodos() {
		CriteriaQuery<Local> query = this.entityManager.getCriteriaBuilder().createQuery(Local.class);
		query.from(Local.class);
		List<Local> locais = this.entityManager.createQuery(query).getResultList();
		
		return Response.ok(locais).build();
	}
	
	/**
	 * Lista as salas por local 
	 *
	 * @param idLocal
	 * @return Response
	 */
	@GET
	@Path("/{id}/salas")
	public Response listaSalasPorLocal(@PathParam("id") Long idLocal) {
		try {
			Local local = this.entityManager.createQuery(
					"select	l from Local l join	fetch l.salas where l.id=:id",
					Local.class).setParameter("id", idLocal).getSingleResult();
			
			List<Sala> salas = local.getSalas();
			
			return Response.ok(salas).build();
		} catch (NoResultException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
		
}
