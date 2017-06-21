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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.com.banana.exception.ReservaJaExisteException;
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
		try {
			validaChoqueHorarios(reserva);
		} catch (ReservaJaExisteException e) {
			throw new WebApplicationException(Status.CONFLICT);
		}
		
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
		if (this.entityManager.find(Reserva.class, reserva.getId()) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		try {
			validaChoqueHorarios(reserva);
		} catch (ReservaJaExisteException e) {
			throw new WebApplicationException(Status.CONFLICT);
		}
		
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
	
	/**
	 * Valida choque de horários na mesma sala e local
	 * 
	 * @param reserva
	 * @throws ReservaJaExisteException
	 */
	private void validaChoqueHorarios(Reserva reserva) throws ReservaJaExisteException {
		Long count = this.entityManager.createQuery(
				"select	count(r) from Reserva r where local_id=:local_id and sala_id=:sala_id"
				+ " and dataHoraInicio <= :dataHoraInicio and dataHoraFim >= :dataHoraFim"				
				+ " or (dataHoraInicio between :dataHoraInicio and :dataHoraFim"
				+ " or dataHoraFim between :dataHoraInicio and :dataHoraFim)",
				Long.class)
				.setParameter("local_id", reserva.getLocal().getId())
				.setParameter("sala_id", reserva.getSala().getId())
				.setParameter("dataHoraInicio", reserva.getDataHoraInicio())
				.setParameter("dataHoraFim", reserva.getDataHoraFim())
				.getSingleResult();
		
		if (count > 0) {
			throw new ReservaJaExisteException();
		}
	}

}
