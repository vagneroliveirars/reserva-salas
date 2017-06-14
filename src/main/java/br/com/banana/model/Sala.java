package br.com.banana.model;

/**
 * Classe que representa uma sala
 * 
 * @author vagner
 *
 */
public class Sala {

	private Long id;

	private String descricao;

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

}
