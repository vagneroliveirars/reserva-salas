package br.com.banana.service;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jettison.JettisonFeature;
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
	
	private static String SERVICES_CONTEXT = "http://localhost:8080/reserva-salas-0.0.1-SNAPSHOT";
	
	private static String RESERVAS_CONTEXT = SERVICES_CONTEXT + "/reservas";
	
	@Test
	public void testeAdicionaReserva() throws Exception {
		Local local = new Local();
		local.setId(1l);
		
		Sala sala = new Sala();
		sala.setId(1l);
		
		Calendar dataHoraInicio = Calendar.getInstance();
		dataHoraInicio.set(2017, Calendar.JANUARY, 1, 8, 0);
		
		Calendar dataHoraFim = Calendar.getInstance();
		dataHoraFim.set(2017, Calendar.JANUARY, 1, 9, 0);
		
		Reserva reserva = new Reserva(local, sala, dataHoraInicio, dataHoraFim, "Vagner", true, 10l,
				"Reserva de teste 1");
		
		
		
		/*
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(RESERVAS_CONTEXT);
		Response response = target.request().accept(MediaType.APPLICATION_JSON).get();
		*/
		
		/*
		Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
		WebTarget webTarget = client.target(RESERVAS_CONTEXT);
		
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(reserva, MediaType.APPLICATION_JSON));
		
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		/*
		
		/*
		assertNotNull(response);
		assertEquals(201, response.getStatus());
		assertNotNull(response.getLocation());
		*/
		
		String json = "{\n" + 
				"\"local\": {\"id\":1},\n" + 
				"\"sala\":{\"id\":1},\n" + 
				"\"dataHoraInicio\":1347915600000,\n" + 
				"\"dataHoraFim\":1347918300000,\n" + 
				"\"responsavel\":\"Vanda\",\n" + 
				"\"cafe\":true,\n" + 
				"\"quantidadePessoas\":30,\n" + 
				"\"descricao\":\"Terceira reuniao\"\n" + 
				"}";
		
		Client client = ClientBuilder.newClient().register(JettisonFeature.class);
		WebTarget target = client.target("http://localhost:8080/reserva-salas-0.0.1-SNAPSHOT/").path("reservas");
		
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(reserva));
		
		assertEquals(201, response.getStatus());
	}
		
}
