package br.edu.ifpe.pdsc_modelo.ejb;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import br.edu.ifpe.pdsc_modelo.entidades.Solicitacao;

@Stateful
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 20)
public class SolicitacaoBean implements SolicitacaoInterface {

	@PersistenceContext(unitName = "pu", type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@PostConstruct
	private void initializeBean() {
	}

	public Solicitacao cadastrarSolicitacao(Solicitacao solicitacao) {
		Solicitacao s = new Solicitacao();
		s.setNumeroSolicitacao(solicitacao.getNumeroSolicitacao());
		s.setAssunto(solicitacao.getAssunto());
		s.setNomeSolicitante(solicitacao.getNomeSolicitante());
		s.setTipo(solicitacao.getTipo());
		entityManager.persist(solicitacao);
		return solicitacao;
	}

	// retorna solicitação pelo id
	public Solicitacao getSolicitacao(int id) {
		/*
		 * Solicitacao solicitacao = entityManager.find(Solicitacao.class, id); if
		 * (solicitacao != null) return solicitacao; return null;
		 */

		String jpql = ("select s from Solicitacao s where s.numeroSolicitacao = " + id);
		Query query = entityManager.createQuery(jpql, Solicitacao.class);
		Solicitacao solicitacao = (Solicitacao) query.getSingleResult();
		if (solicitacao != null) {
			return solicitacao;
		}

		return null;
	}

	/*
	 * retornar solicitações pelo número public List<Solicitacao> getSolicitacao(int
	 * numero) { String jpql =
	 * ("select s from Solicitacao s where s.numeroSolicitacao = " + numero); Query
	 * query = entityManager.createQuery(jpql, Solicitacao.class); List<Solicitacao>
	 * solicitacao = (List<Solicitacao>) query.getSingleResult(); if (solicitacao !=
	 * null) return solicitacao; return null; }
	 */

	// consulta todas solicitações
	public List<Solicitacao> getSolicitacoes() {
		String jpql = ("select s from Solicitacao s");
		Query query = entityManager.createQuery(jpql, Solicitacao.class);
		List<Solicitacao> solicitacoes = query.getResultList();
		if (solicitacoes != null) {
			return solicitacoes;
		}
		return null;
	}

	// atualiza solicitação
	public void updateSolicitacao(Solicitacao solicitacao) {
		String jpql = ("UPDATE Solicitacao s SET s.nomeSolicitante= " + solicitacao.getNomeSolicitante()
				+ " where s.numeroSolicitacao = " + solicitacao.getNumeroSolicitacao());
		Query query = entityManager.createQuery(jpql, Solicitacao.class);
		query.executeUpdate();

		// entityManager.merge(solicitacao);
	}

	public void removeSolicitacao(String num) {

		String jpql = ("DELETE from Solicitacao s where s.numeroSolicitacao = " + num);
		Query query = entityManager.createQuery(jpql, Solicitacao.class);
		query.executeUpdate();

		//entityManager.remove(solicitacao);
		// entityManager.merge(solicitacao);
	}

}