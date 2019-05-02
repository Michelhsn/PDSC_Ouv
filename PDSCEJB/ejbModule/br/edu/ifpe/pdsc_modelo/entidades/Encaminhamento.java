package br.edu.ifpe.pdsc_modelo.entidades;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "encaminhamento", catalog = "data")
public class Encaminhamento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;

	@Column(name = "resposta", nullable = true)
	private String resposta;
	
	@Column(name = "descricao", nullable = true)
	private String descricao;
	
	@Column(name = "unidade", nullable = false)
	private String unidade;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "COD_SOLICITACAO", referencedColumnName = "id")
	private Solicitacao codSolicitacao;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Solicitacao getCodSolicitacao() {
		return codSolicitacao;
	}

	public void setCodSolicitacao(Solicitacao codSolicitacao) {
		this.codSolicitacao = codSolicitacao;
	}

	
	
	

	
}