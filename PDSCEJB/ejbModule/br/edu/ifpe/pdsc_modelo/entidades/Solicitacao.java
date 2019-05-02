package br.edu.ifpe.pdsc_modelo.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "solicitacao", catalog = "data")
public class Solicitacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "num_solicitacao", nullable = false)
	private String numeroSolicitacao;

	@Column(name = "nom_solicitante", nullable = true)
	private String nomeSolicitante;

	@Column(name = "email_solicitante", nullable = false)
	private String emailSolicitante;

	@Column(name = "fone_solicitante", nullable = true)
	private String foneSolicitante;

	@Column(name = "des_solicitacao", nullable = true)
	private String descricaoSolicitacao;

	@Column(name = "unidade", nullable = true)
	private String unidade;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "encaminhamento", referencedColumnName = "id")
	private List<Encaminhamento> encaminhamento;

	@Column(name = "assunto", nullable = true)
	private String assunto;

	@Column(name = "tipo", nullable = true)
	private String tipo;

	@Column(name = "dat_abertura", nullable = true)
	private String dataAbertura;

	@Column(name = "dat_finalizacao", nullable = true)
	private Date dataFinalizacao;

	@Column(name = "cpf", nullable = true)
	private String cpf;

	@Column(name = "solicitacaocol", nullable = true)
	private String solicitacaocol;

	public String getNumeroSolicitacao() {
		return numeroSolicitacao;
	}

	public void setNumeroSolicitacao(String numeroSolicitacao) {
		this.numeroSolicitacao = numeroSolicitacao;
	}

	public String getNomeSolicitante() {
		return nomeSolicitante;
	}

	public void setNomeSolicitante(String nomeSolicitante) {
		this.nomeSolicitante = nomeSolicitante;
	}

	public String getEmailSolicitante() {
		return emailSolicitante;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public List<Encaminhamento> getEncaminhamento() {
		return encaminhamento;
	}

	public void setEncaminhamento(List<Encaminhamento> encaminhamento) {
		this.encaminhamento = encaminhamento;
	}

	public void setEmailSolicitante(String emailSolicitante) {
		this.emailSolicitante = emailSolicitante;
	}

	public String getFoneSolicitante() {
		return foneSolicitante;
	}

	public void setFoneSolicitante(String foneSolicitante) {
		this.foneSolicitante = foneSolicitante;
	}

	public String getDescricaoSolicitacao() {
		return descricaoSolicitacao;
	}

	public void setDescricaoSolicitacao(String descricaoSolicitacao) {
		this.descricaoSolicitacao = descricaoSolicitacao;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(String dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public Date getDataFinalizacao() {
		return dataFinalizacao;
	}

	public void setDataFinalizacao(Date dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSolicitacaocol() {
		return solicitacaocol;
	}

	public void setSolicitacaocol(String solicitacaocol) {
		this.solicitacaocol = solicitacaocol;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}