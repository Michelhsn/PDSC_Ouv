package br.edu.ifpe.pdsc_modelo.rest;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import br.edu.ifpe.pdsc_modelo.ejb.Login;
import br.edu.ifpe.pdsc_modelo.ejb.SolicitacaoInterface;
import br.edu.ifpe.pdsc_modelo.entidades.Solicitacao;
import br.edu.ifpe.pdsc_modelo.entidades.User;
import br.edu.ifpe.pdsc_modelo.util.ClientUtility;
import br.edu.ifpe.pdsc_modelo.util.PasswordUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
@Path("/solicitacoes")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
public class SolicitacaoEndpoint implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4993293130748747995L;
	
	@Context
	private HttpServletResponse res;

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
	
	@GET
	@Path("/relatorio")
	public Response relatorioSolicitacoes() throws JRException, IOException {
			SolicitacaoInterface solicitacaoBean;
			try {
				solicitacaoBean = ClientUtility.getSolicitacaoBean();
				List<Solicitacao> solicitacoes = solicitacaoBean.getSolicitacoes();
				
				java.io.InputStream template = this.getClass().getResourceAsStream("/br/edu/ifpe/pdsc_modelo/relatorio/" +
						 "relatorio_solicitante.jrxml");
				JasperReport report = JasperCompileManager.compileReport(template);
				JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(solicitacoes));
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				JasperExportManager.exportReportToPdfStream(print, baos);

				res.reset();
				res.setContentType("application/pdf");
				res.setContentLength(baos.size());
				res.setHeader("Content-disposition", "inline; filename=relatorioSolicitacao.pdf");
				res.getOutputStream().write(baos.toByteArray());
				res.getOutputStream().flush();
				res.getOutputStream().close();
				if (solicitacoes != null)
					return Response.ok(solicitacoes).build();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
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
			Solicitacao solicitacao1 = solicitacaobean.cadastrarSolicitacao(solicitacao);
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