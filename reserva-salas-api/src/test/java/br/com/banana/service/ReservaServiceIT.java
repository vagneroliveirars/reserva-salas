package br.com.banana.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import br.com.banana.model.Local;
import br.com.banana.model.Reserva;
import br.com.banana.model.Sala;

/**
 * Classe de teste para o serviço de reservas 
 * 
 * @author vagner
 *
 */
public class ReservaServiceIT {
	
	private static String SERVICES_CONTEXT = "http://localhost:8080/reserva-salas";

	private WebTarget target;
	
	@Before
	public void setup() {
		Client client = ClientBuilder.newClient();
		this.target = client.target(SERVICES_CONTEXT).path("reservas");
	}
	
	@Test
	public void testeAdicionaReserva() throws Exception {
		Local local = new Local();
		local.setId(1l);
		
		Sala sala = new Sala();
		sala.setId(1l);
		
		Calendar dataHoraInicio = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraInicio.set(2016, Calendar.JANUARY, 1, 8, 0);
		
		Calendar dataHoraFim = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraFim.set(2016, Calendar.JANUARY, 1, 9, 0);
		
		Reserva reserva = new Reserva(local, sala, dataHoraInicio, dataHoraFim, "Vagner", true, 10l,
				"Reserva de teste 1");
		
		Response response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertNotNull(response.getLocation());
	}
	
	@Test
	public void testeBuscaReservaPorId() {
		Local local = new Local();
		local.setId(1l);
		
		Sala sala = new Sala();
		sala.setId(1l);
		
		Calendar dataHoraInicio = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraInicio.set(2016, Calendar.JANUARY, 2, 10, 0);
		
		Calendar dataHoraFim = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraFim.set(2016, Calendar.JANUARY, 2, 11, 0);
		
		Reserva reserva = new Reserva(local, sala, dataHoraInicio, dataHoraFim, "Vagner", true, 10l,
				"Reserva de teste 2");
		
		Response response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		
		Link link = Link.fromUri(response.getLocation()).build();
		
		reserva = ClientBuilder.newClient()				
				.invocation(link)
				.accept(MediaType.APPLICATION_JSON)
				.get(Reserva.class);
		
		assertNotNull(reserva);
		assertEquals("Reserva de teste 2", reserva.getDescricao());
	}
	
	@Test
	public void testeAtualizaReserva() {
		Local local = new Local();
		local.setId(1l);
		
		Sala sala = new Sala();
		sala.setId(1l);
		
		Calendar dataHoraInicio = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraInicio.set(2016, Calendar.JANUARY, 3, 12, 0);
		
		Calendar dataHoraFim = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraFim.set(2016, Calendar.JANUARY, 3, 13, 0);
		
		Reserva reserva = new Reserva(local, sala, dataHoraInicio, dataHoraFim, "Vagner", true, 10l,
				"Reserva de teste 2");
		
		Response response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		
		Link link = Link.fromUri(response.getLocation()).build();
		
		reserva = ClientBuilder.newClient()				
				.invocation(link)
				.accept(MediaType.APPLICATION_JSON)
				.get(Reserva.class);
		
		reserva.setDescricao("Teste de update");
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		
		assertNotNull(response);
		assertEquals(204, response.getStatus());
	}
	
	@Test
	public void testeDeletaReserva() {
		Local local = new Local();
		local.setId(1l);
		
		Sala sala = new Sala();
		sala.setId(1l);
		
		Calendar dataHoraInicio = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraInicio.set(2016, Calendar.JANUARY, 4, 14, 0);
		
		Calendar dataHoraFim = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraFim.set(2016, Calendar.JANUARY, 4, 15, 0);
		
		Reserva reserva = new Reserva(local, sala, dataHoraInicio, dataHoraFim, "Vagner", true, 10l,
				"Reserva de teste 2");
		
		Response response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		
		Link link = Link.fromUri(response.getLocation()).build();
		
		response = ClientBuilder.newClient()				
				.invocation(link)
				.accept(MediaType.APPLICATION_JSON)
				.delete();
		
		assertNotNull(response);
		assertEquals(204, response.getStatus());
	}
	
	@Test
	public void testeChoqueDeHorarios() {
		Local local = new Local();
		local.setId(1l);
		
		Sala sala = new Sala();
		sala.setId(1l);
		
		/*
		 * Data hora início: 01/01/2017 8:00
		 * Data hora fim: 01/01/2017 9:00
		 */
		Calendar dataHoraInicio = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraInicio.set(2017, Calendar.JANUARY, 1, 8, 0, 0);
		
		Calendar dataHoraFim = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraFim.set(2017, Calendar.JANUARY, 1, 9, 0, 0);
		
		Reserva reserva = new Reserva(local, sala, dataHoraInicio, dataHoraFim, "Vagner", true, 10l,
				"Reserva de teste 1");
		
		Response response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertNotNull(response.getLocation());
		
		/*
		 * Data hora início: 01/01/2017 8:15
		 * Data hora fim: 01/01/2017 8:30
		 */
		dataHoraInicio.set(2017, Calendar.JANUARY, 1, 8, 15, 0);
		dataHoraFim.set(2017, Calendar.JANUARY, 1, 8, 30, 0);
		reserva.setDataHoraInicio(dataHoraInicio);
		reserva.setDataHoraFim(dataHoraFim);
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		assertNotNull(response);
		assertEquals(409, response.getStatus());
		
		
		/*
		 * Data hora início: 01/01/2017 7:00
		 * Data hora fim: 01/01/2017 10:00
		 */
		dataHoraInicio.set(2017, Calendar.JANUARY, 1, 7, 0, 0);
		dataHoraFim.set(2017, Calendar.JANUARY, 1, 10, 0, 0);
		reserva.setDataHoraInicio(dataHoraInicio);
		reserva.setDataHoraFim(dataHoraFim);
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		assertNotNull(response);
		assertEquals(409, response.getStatus());
		
		
		/*
		 * Data hora início: 01/01/2017 7:00
		 * Data hora fim: 01/01/2017 8:00
		 */
		dataHoraInicio.set(2017, Calendar.JANUARY, 1, 7, 0, 0);
		dataHoraFim.set(2017, Calendar.JANUARY, 1, 8, 0, 0);
		reserva.setDataHoraInicio(dataHoraInicio);
		reserva.setDataHoraFim(dataHoraFim);
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		assertNotNull(response);
		assertEquals(409, response.getStatus());
		
		
		/*
		 * Data hora início: 01/01/2017 9:00
		 * Data hora fim: 01/01/2017 10:00
		 */
		dataHoraInicio.set(2017, Calendar.JANUARY, 1, 9, 0, 0);
		dataHoraFim.set(2017, Calendar.JANUARY, 1, 10, 0, 0);
		reserva.setDataHoraInicio(dataHoraInicio);
		reserva.setDataHoraFim(dataHoraFim);
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		assertNotNull(response);
		assertEquals(409, response.getStatus());
		
		
		/*
		 * Data hora início: 01/01/2017 7:00
		 * Data hora fim: 01/01/2017 7:59
		 */
		dataHoraInicio.set(2017, Calendar.JANUARY, 1, 7, 0, 0);
		dataHoraFim.set(2017, Calendar.JANUARY, 1, 7, 59, 0);
		reserva.setDataHoraInicio(dataHoraInicio);
		reserva.setDataHoraFim(dataHoraFim);
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertNotNull(response.getLocation());
		
		
		/*
		 * Data hora início: 01/01/2017 9:01
		 * Data hora fim: 01/01/2017 10:00
		 */
		dataHoraInicio.set(2017, Calendar.JANUARY, 1, 9, 1, 0);
		dataHoraFim.set(2017, Calendar.JANUARY, 1, 10, 0, 0);
		reserva.setDataHoraInicio(dataHoraInicio);
		reserva.setDataHoraFim(dataHoraFim);
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertNotNull(response.getLocation());
		
		Link link = Link.fromUri(response.getLocation()).build();
		
		reserva = ClientBuilder.newClient()				
				.invocation(link)
				.accept(MediaType.APPLICATION_JSON)
				.get(Reserva.class);
		
		/*
		 * Data hora início: 01/01/2017 9:15
		 * Data hora fim: 01/01/2017 9:45
		 */
		dataHoraInicio.set(2017, Calendar.JANUARY, 1, 9, 15, 0);
		dataHoraFim.set(2017, Calendar.JANUARY, 1, 9, 45, 0);
		reserva.setDataHoraInicio(dataHoraInicio);
		reserva.setDataHoraFim(dataHoraFim);
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		
		assertNotNull(response);
		assertEquals(204, response.getStatus());
	}
	
	@Test
	public void testeDataInicialMaiorQueDataFinal() {
		Local local = new Local();
		local.setId(1l);
		
		Sala sala = new Sala();
		sala.setId(1l);
		
		/*
		 * Data hora início: 01/01/2018 10:00
		 * Data hora fim: 01/01/2018 9:00
		 */
		Calendar dataHoraInicio = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		Calendar dataHoraFim = Calendar.getInstance(TimeZone.getTimeZone("GMT-3"));
		dataHoraInicio.set(2018, Calendar.JANUARY, 1, 10, 1, 0);
		dataHoraFim.set(2018, Calendar.JANUARY, 1, 9, 0, 0);
		
		Reserva reserva = new Reserva(local, sala, dataHoraInicio, dataHoraFim, "Vagner", true, 10l,
				"Reserva de teste 1");
		
		Response response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		assertNotNull(response);
		assertEquals(409, response.getStatus());
		
		/*
		 * Data hora início: 01/01/2018 9:00
		 * Data hora fim: 01/01/2018 10:00
		 */
		dataHoraInicio.set(2018, Calendar.JANUARY, 1, 9, 1, 0);
		dataHoraFim.set(2018, Calendar.JANUARY, 1, 10, 0, 0);
		reserva.setDataHoraInicio(dataHoraInicio);
		reserva.setDataHoraFim(dataHoraFim);
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertNotNull(response.getLocation());
		
		Link link = Link.fromUri(response.getLocation()).build();
		
		reserva = ClientBuilder.newClient()				
				.invocation(link)
				.accept(MediaType.APPLICATION_JSON)
				.get(Reserva.class);
		
		/*
		 * Data hora início: 01/01/2018 10:00
		 * Data hora fim: 01/01/2018 9:00
		 */
		dataHoraInicio.set(2018, Calendar.JANUARY, 1, 10, 1, 0);
		dataHoraFim.set(2018, Calendar.JANUARY, 1, 9, 0, 0);
		reserva.setDataHoraInicio(dataHoraInicio);
		reserva.setDataHoraFim(dataHoraFim);
		
		response = this.target.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		
		assertNotNull(response);
		assertEquals(409, response.getStatus());
	}
		
}
