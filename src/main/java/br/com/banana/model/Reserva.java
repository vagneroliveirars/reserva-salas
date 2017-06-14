package br.com.banana.model;

import java.util.Calendar;

/**
 * Classe que representa uma reserva
 * 
 * @author vagner
 *
 */
public class Reserva {

	private Long id;

	private Local local;

	private Sala sala;

	private Calendar dataHoraInicio;

	private Calendar dataHoraFim;

	private String responsavel;

	private Boolean cafe;

	private Long quantidadePessoas;

	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Calendar getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Calendar dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Calendar getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Calendar dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public Boolean getCafe() {
		return cafe;
	}

	public void setCafe(Boolean cafe) {
		this.cafe = cafe;
	}

	public Long getQuantidadePessoas() {
		return quantidadePessoas;
	}

	public void setQuantidadePessoas(Long quantidadePessoas) {
		this.quantidadePessoas = quantidadePessoas;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
