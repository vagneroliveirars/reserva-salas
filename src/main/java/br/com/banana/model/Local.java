package br.com.banana.model;

import java.util.List;

/**
 * Classe que representa um local
 * 
 * @author vagner
 *
 */
public class Local {

	private Long id;

	private String descricao;

	private List<Sala> salas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Sala> getSalas() {
		return salas;
	}

	public void setSalas(List<Sala> salas) {
		this.salas = salas;
	}

}
