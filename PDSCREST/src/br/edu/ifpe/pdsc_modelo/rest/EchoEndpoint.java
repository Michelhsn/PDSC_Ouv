package br.edu.ifpe.pdsc_modelo.rest;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.edu.ifpe.pdsc_modelo.ejb.Login;
import br.edu.ifpe.pdsc_modelo.entidades.User;
import br.edu.ifpe.pdsc_modelo.filter.JWTTokenNeeded;
import br.edu.ifpe.pdsc_modelo.util.ClientUtility;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Path("/echo")
@Produces(APPLICATION_JSON)
public class EchoEndpoint {

    // ======================================
    // =          Injection Points          =
    // ======================================

	@Context
	private HttpServletRequest httpRequest;

    // ======================================
    // =          Business methods          =
    // ======================================

    @GET
    public Response echo(@QueryParam("message") String message) {
        return Response.ok().entity(message == null ? "no message" : message).build();
    }

    @GET
    @Path("jwt")
    @JWTTokenNeeded
    @Produces(TEXT_PLAIN)
   public Response echoWithJWTToken(@QueryParam("message") String message) {
    	//Recuperar o usuário é opcional, depende da lógica do método por isso
    	//ele deve ser recuperado em cada método. Isso pode ser melhorado.
    	String authorizationHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
        String token = authorizationHeader.substring("Bearer".length()).trim();
		try {
			Login loginbean = ClientUtility.getLoginBean();
			User user = loginbean.getUserByToken(token);
			if (user != null) {
				String mensagemRetorno = user.getNome() + ": " + message;
		        return Response.ok().entity(mensagemRetorno).build();
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
        
    }
}