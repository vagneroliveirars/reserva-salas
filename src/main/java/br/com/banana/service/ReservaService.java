package br.com.banana.service;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.banana.model.Reserva;

/**
 * Serviço REST para reservas
 * 
 * @author vagner
 *
 */
@Stateless
@Path("/reservas")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ReservaService {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Lista todas as reservas
	 * 
	 * @return Response
	 */
	@GET
	public Response listaTodas() {
		List<Reserva> reservas = this.entityManager
				.createQuery("select r from Reserva r", Reserva.class)
				.getResultList();

		return Response.ok(reservas).build();
	}
	
	/**
	 * Busca a reserva por id
	 * 
	 * @param id
	 * @return Response
	 */
	@GET
	@Path("/{id}")
	public Response buscaPorId(@PathParam("id") Long id) {
		Reserva reserva = this.entityManager.find(Reserva.class, id);
		
		if (reserva != null) {
			return Response.ok(reserva).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}
	
	/**
	 * Adiciona uma nova reserva
	 * 
	 * @param uriInfo
	 * @param reserva
	 * @return Response
	 */
	@POST
	public Response adiciona(@Context UriInfo uriInfo, Reserva reserva) {
		this.entityManager.persist(reserva);
		
		// Constrói a URL onde o recurso está disponível
		UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		URI location = uriBuilder.path("/{id}").build(reserva.getId());
		
		return Response.created(location).build();
	}
	
	/**
	 * Atualiza uma reserva
	 * 
	 * @param reserva
	 * @return Response
	 */
	@PUT
	public Response atualiza(Reserva reserva) {
		this.entityManager.merge(reserva);
		return Response.noContent().build();
	}
	
	/**
	 * Deleta uma reserva
	 * 
	 * @param id
	 * @return Response
	 */
	@DELETE
	@Path("/{id}")
	public Response deleta(@PathParam("id") Long id) {
		Reserva reserva = this.entityManager.find(Reserva.class, id);
		
		if (reserva == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		this.entityManager.remove(reserva);
		
		return Response.noContent().build();
	}

}
