package br.com.banana.service;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Application;

/**
 * Classe de configuração da aplicação JAX-RS
 * 
 * @author vagner
 *
 */
public class ApplicationJAXRS extends Application {
	
	@Override
	public Map<String, Object> getProperties() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put("jersey.config.server.provider.packages", "br.com.banana.service");
		return properties;
	}

}
