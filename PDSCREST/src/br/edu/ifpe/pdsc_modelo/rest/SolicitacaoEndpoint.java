package br.edu.ifpe.pdsc_modelo.rest;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import br.edu.ifpe.pdsc_modelo.ejb.Login;
import br.edu.ifpe.pdsc_modelo.ejb.SolicitacaoInterface;
import br.edu.ifpe.pdsc_modelo.entidades.Solicitacao;
import br.edu.ifpe.pdsc_modelo.entidades.User;
import br.edu.ifpe.pdsc_modelo.util.ClientUtility;
import br.edu.ifpe.pdsc_modelo.util.PasswordUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
@Path("/solicitacoes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
public class SolicitacaoEndpoint {

	// recupera solicitações por id
	@GET
	@Path("/{id}")
	public Response findById(@PathParam("id") int id) {
		try {
			List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
			SolicitacaoInterface solicitacaoBean = ClientUtility.getSolicitacaoBean();
			Solicitacao solicitacao = solicitacaoBean.getSolicitacao(id);
			solicitacoes.add(solicitacao);
			if (solicitacao != null)
				return Response.ok(solicitacoes).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}

	// recupera todas solicitações
	@GET
	public Response solicitacoes() {
		try {
			SolicitacaoInterface solicitacaoBean = ClientUtility.getSolicitacaoBean();
			List<Solicitacao> solicitacoes = solicitacaoBean.getSolicitacoes();
			if (solicitacoes != null)
				return Response.ok(solicitacoes).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}

	// cadastro de solicitações
	// @Consumes(APPLICATION_FORM_URLENCODED)
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Solicitacao solicitacao) {
		try {
			SolicitacaoInterface solicitacaobean = ClientUtility.getSolicitacaoBean();
			Solicitacao s = new Solicitacao();
			s.setNumeroSolicitacao(solicitacao.getNumeroSolicitacao());
			s.setTipo(solicitacao.getTipo());
			s.setNomeSolicitante(solicitacao.getNomeSolicitante());
			s.setAssunto(solicitacao.getAssunto());
			Solicitacao solicitacao1 = solicitacaobean.cadastrarSolicitacao(s);
			if (solicitacao1 != null)
				return Response.ok(solicitacao1).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}

	@POST
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response change(Solicitacao solicitacao) {
		try {
			SolicitacaoInterface solicitacaobean = ClientUtility.getSolicitacaoBean();
			solicitacaobean.updateSolicitacao(solicitacao);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") String id) {
		try {
			SolicitacaoInterface solicitacaobean = ClientUtility.getSolicitacaoBean();
			solicitacaobean.removeSolicitacao(id);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}

}