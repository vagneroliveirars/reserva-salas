package br.com.banana.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.banana.model.Local;

@Stateless
@Path("/locais")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class LocalService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@GET
	public Response listaTodos() {
		CriteriaQuery<Local> query = this.entityManager.getCriteriaBuilder().createQuery(Local.class);
		query.from(Local.class);
		List<Local> locais = this.entityManager.createQuery(query).getResultList();
		
		return Response.ok(locais).build();
	}
		
}
