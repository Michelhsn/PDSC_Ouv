package br.edu.ifpe.pdsc_modelo.rest;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.FORBIDDEN;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

import java.util.List;

import javax.naming.NamingException;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
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
import br.edu.ifpe.pdsc_modelo.entidades.User;
import br.edu.ifpe.pdsc_modelo.util.ClientUtility;
import br.edu.ifpe.pdsc_modelo.util.Jwts1;
import br.edu.ifpe.pdsc_modelo.util.PasswordUtils;
/*import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;*/
import io.jsonwebtoken.*;


/**
 * @author Antonio Goncalves http://www.antoniogoncalves.org --
 */
@Path("/users")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
@Transactional
public class UserEndpoint {

	@POST
	@Path("/login")
	@Consumes(APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(@FormParam("login") String login, @FormParam("password") String password) {

		try {
			// Authenticate the user using the credentials provided
			Login loginbean = ClientUtility.getLoginBean();
			User user = loginbean.login(login, PasswordUtils.digestPassword(password));
			if (user == null)
				throw new SecurityException("Invalid user/password");

			// Issue a token for the user
			String token = Jwts.builder()
					.setSubject(login)
					.claim("custom", "myCustom")
					.signWith(
						SignatureAlgorithm.HS256,
						DatatypeConverter.parseBase64Binary("tmMY7VZuZ1DrsTF8JNImtiZ6Im6nx+2lLMEWhvRHneE=")
					)
					.compact();

			user.setToken(token);
			loginbean.updateUser(user);
			
			// Return the token on the response
			return Response.ok().header(AUTHORIZATION, "Bearer " + token).build();

		} catch (SecurityException e) {
			return Response.status(FORBIDDEN).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(UNAUTHORIZED).build();
		}
	}
	
	@POST
	@Path("/logar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response autenticar(User user) {

		try {
			// Authenticate the user using the credentials provided
			Login loginbean = ClientUtility.getLoginBean();
			User usuario = loginbean.login(user.getNome(), PasswordUtils.digestPassword(user.getSenha()));
			if (usuario == null)
				throw new SecurityException("Invalid user/password");
			else {
				
			}

			// Issue a token for the user
			/*
			 * String token = Jwts1.builder() .setSubject(user.getNome()) .claim("custom",
			 * "myCustom") .signWith( SignatureAlgorithm.HS256,
			 * DatatypeConverter.parseBase64Binary(
			 * "tmMY7VZuZ1DrsTF8JNImtiZ6Im6nx+2lLMEWhvRHneE=") ) .compact();
			 * 
			 * 
			 * //user.setToken(token); loginbean.updateUser(user);
			 * 
			 * // Return the token on the response
			 * 
			 */			
			String token = "";
			if (user.getNome().equals("admin")) {
				token = "admin";
			} else {
				token = "comum";
			}
			
			
			return Response.ok().header(AUTHORIZATION, token).build();

		} catch (SecurityException e) {
			return Response.status(FORBIDDEN).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(UNAUTHORIZED).build();
		}
	}


	@POST
	@Consumes(APPLICATION_FORM_URLENCODED)
	public Response create(@FormParam("login") String login, @FormParam("password") String password) {
		try {
			Login loginbean = ClientUtility.getLoginBean();
			User user = loginbean.cadastrarUsuario(login, PasswordUtils.digestPassword(password));
			if (user != null)
				return Response.ok(user).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(User user) {
		try {
			Login loginbean = ClientUtility.getLoginBean();
			User usuario = loginbean.cadastrarUsuario(user.getNome(), PasswordUtils.digestPassword(user.getSenha()));
			if (usuario != null)
				return Response.ok(usuario).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}

	@GET
	@Path("/{id}")
	public Response findById(@PathParam("id") int id) {
		try {
			Login loginbean = ClientUtility.getLoginBean();
			User user = loginbean.getUsuario(id);
			if (user != null)
				return Response.ok(user).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}

	@GET
	public Response findAllUsers() {
		try {
			Login loginbean = ClientUtility.getLoginBean();
			List<User> allUsers = loginbean.getAllUsers();
			if (allUsers != null)
				return Response.ok(allUsers).build();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return Response.status(NOT_FOUND).build();
	}
	
}