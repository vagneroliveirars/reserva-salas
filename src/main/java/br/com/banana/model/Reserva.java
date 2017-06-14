package br.com.banana.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe que representa uma reserva
 * 
 * @author vagner
 *
 */
@Entity
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Local local;

	@OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
	private Sala sala;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataHoraInicio;

	@Temporal(TemporalType.TIMESTAMP)
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
