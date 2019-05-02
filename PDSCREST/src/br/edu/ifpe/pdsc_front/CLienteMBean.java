package br.edu.ifpe.pdsc_front;

import br.edu.ifpe.pdsc_modelo.entidades.Solicitacao;
import br.edu.ifpe.pdsc_modelo.entidades.TipoEnum;
import br.edu.ifpe.pdsc_modelo.entidades.User;
import br.edu.ifpe.pdsc_modelo.relatorio.GeraRelatorio;
import br.edu.ifpe.pdsc_modelo.util.FacesUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SuppressWarnings("deprecation")
@ManagedBean(name = "clienteMB")
@SessionScoped
public class CLienteMBean {

	private String numero;
	private String nome;
	private String assunto;
	private String tipo;
	private Boolean altera;
	private Solicitacao solicitacao;
	private String acao;
	private String cpf;
	private String email;
	private String descricao;
	private String fone;
	private String unidade;
	private TipoEnum tipoEnum;
	private String nomeUsuario;
	private String senhaUsuario;
	private Boolean adm;
	private Boolean comum;

	@PostConstruct
	public void inicializarCampos() {
		this.numero = "";
	}

	// recupera json da api
	public List<User> getClientes() {
		Client c = Client.create();
		WebResource wr = c.resource("http://localhost:8080/PDSCREST/api/users");
		String json = wr.get(String.class);
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<User>>() {
		}.getType());
	}

	public List<Solicitacao> getSolicitacoes() {
		if (numero == null) {
			Client s = Client.create();
			WebResource wr = s.resource("http://localhost:8080/PDSCREST/api/solicitacoes");
			String json = wr.get(String.class);
			Gson gson = new Gson();
			return gson.fromJson(json, new TypeToken<List<Solicitacao>>() {
			}.getType());
		} else {
			String webResource = "http://localhost:8080/PDSCREST/api/solicitacoes/" + numero;

			Client s = Client.create();
			WebResource wr = s.resource(webResource);
			String json = wr.get(String.class);
			Gson gson = new Gson();
			return gson.fromJson(json, new TypeToken<List<Solicitacao>>() {
			}.getType());
		}

	}

	public void setSolicitacoes() {
		Client s = Client.create();
		java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy");

		String strDia = df.format(new java.util.Date());
		WebResource wr = s.resource("http://localhost:8080/PDSCREST/api/solicitacoes");
		String input = "{\"numeroSolicitacao\":\"" + numero + "\",\"tipo\":\"" + tipo + "\"," + "\"nomeSolicitante\":\""
				+ nome + "\"," + "\"assunto\":\"" + assunto + "\"," + "\"descricaoSolicitacao\":\"" + descricao + "\","
				+ "\"foneSolicitante\":\"" + fone + "\"," + "\"unidade\":\"" + unidade + "\"," + "\"emailSolicitante\":\"" + email + "\"," + "\"cpf\":\"" + cpf
				+ "\"," + "\"dataAbertura\":\"" + strDia + "\"}";
		ClientResponse response = wr.type("application/json").post(ClientResponse.class, input);
		/*
		 * if (response.getStatus() != 201) { throw new
		 * RuntimeException("Failed : HTTP error code : " + response.getStatus()); }
		 */

		FacesUtil.msgInfo("Solicitação Incluída!!");
		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}

	public void setSolicitacoesAlterar() {
		Client s = Client.create();
		WebResource wr = s.resource("http://localhost:8080/PDSCREST/api/solicitacoes/altera");
		String input = "{\"numeroSolicitacao\":\"" + numero + "\",\"tipo\":\"" + tipo + "\"," + "\"nomeSolicitante\":\""
				+ nome + "\"," + "\"assunto\":\"" + assunto + "\"}";
		ClientResponse response = wr.type("application/json").post(ClientResponse.class, input);

		/*
		 * if (response.getStatus() != 201) { throw new
		 * RuntimeException("Failed : HTTP error code : " + response.getStatus()); }
		 */

		FacesUtil.msgInfo("Solicitante alterado!!");
		numero = null;
		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);

	}
	
	public String logout() {
		nomeUsuario = null;
		senhaUsuario = null;
		numero = null;
		adm= false;
		comum = false;
		return "login.xhtml?faces-redirect=true";
	}
	
	public void setUsuario() {
		Client s = Client.create();

		WebResource wr = s.resource("http://localhost:8080/PDSCREST/api/users");
		String input = "{\"nome\":\"" + nomeUsuario + "\",\"senha\":\"" + senhaUsuario + "\"}";
		ClientResponse response = wr.type("application/json").post(ClientResponse.class, input);
		/*
		 * if (response.getStatus() != 201) { throw new
		 * RuntimeException("Failed : HTTP error code : " + response.getStatus()); }
		 */

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
	}
	
	public String logar() {
		Client s = Client.create();

		WebResource wr = s.resource("http://localhost:8080/PDSCREST/api/users/logar");
		String input = "{\"nome\":\"" + nomeUsuario + "\",\"senha\":\"" + senhaUsuario + "\"}";
		//String input = "{\"password\":\"" + nomeUsuario + "\"}";
		ClientResponse response = wr.type("application/json").post(ClientResponse.class, input);
		/*
		 * if (response.getStatus() != 201) { throw new
		 * RuntimeException("Failed : HTTP error code : " + response.getStatus()); }
		 */
		
		if(response.getHeaders().getFirst("Authorization").equals("admin")){
			adm = true;
			comum = false;
		} else {
			comum = true;
			adm = false;
		}
		return "index.xhtml?faces-redirect=true";
		/*
		 * System.out.println("Output from Server .... \n"); String output =
		 * response.getEntity(String.class); System.out.println(output);
		 */
	}

	public String excluir(Solicitacao solicitacao) {
		Client s = Client.create();
		WebResource wr = s
				.resource("http://localhost:8080/PDSCREST/api/solicitacoes/" + solicitacao.getNumeroSolicitacao());
		ClientResponse response = wr.delete(ClientResponse.class);

		FacesUtil.msgInfo("Solicitante Excluído!!");

		System.out.println("Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
		return "tabelaSolicitacoes";

	}

	public String alterar(Solicitacao solicitacao) {

		setAltera(true);
		setAcao(FacesUtil.getParametro("acao"));
		Solicitacao valor = FacesUtil.getParam("cod");
		setSolicitacao(solicitacao);
		setNumero(numero);
		setNome(nome);
		setTipo(tipo);
		setAssunto(assunto);
		setCpf(cpf);
		setDescricao(descricao);
		setEmail(email);
		setFone(fone);
		setUnidade(unidade);
		return "/paginas/cadastroSolicitacoes.xhtml";
	}

	public void carregaCadastro() {
		String valor = FacesUtil.getParametro("cod");
		setAcao(FacesUtil.getParametro("acao"));
		if (valor != null) {
			String webResource = "http://localhost:8080/PDSCREST/api/solicitacoes/" + valor;

			Client s = Client.create();
			WebResource wr = s.resource(webResource);
			String json = wr.get(String.class);
			Gson gson = new Gson();
			List<Solicitacao> sol = gson.fromJson(json, new TypeToken<List<Solicitacao>>() {
			}.getType());

			setNumero(sol.get(0).getNumeroSolicitacao());
			setNome(sol.get(0).getNomeSolicitante());
			setAssunto(sol.get(0).getAssunto());
			setTipo(sol.get(0).getTipo());
			setEmail(sol.get(0).getEmailSolicitante());
			setCpf(sol.get(0).getCpf());
			setDescricao(sol.get(0).getDescricaoSolicitacao());
			setEmail(sol.get(0).getEmailSolicitante());
			setFone(sol.get(0).getFoneSolicitante());
			setUnidade(sol.get(0).getUnidade());

		} else {
			setNumero(null);
			setNome(null);
			setAssunto(null);
			setTipo(null);
			setEmail(null);
			setCpf(null);
			setDescricao(null);
			setEmail(null);
			setFone(null);
			setUnidade(null);
		}
	}

	public void imprimir(Solicitacao solicitacao) {
		List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
		solicitacoes.add(solicitacao);
		GeraRelatorio relatorio = new GeraRelatorio();
		try {
			relatorio.imprimir(solicitacoes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * public List<Solicitacao> getSolicitacoes() { Client s = Client.create();
	 * String api = "http://localhost:8080/PDSCREST/api/solicitacoes/" + numero;
	 * WebResource wr = s.resource(api); String json = wr.get(String.class); Gson
	 * gson = new Gson(); return gson.fromJson(json, new TypeToken<Solicitacao>() {
	 * }.getType()); }
	 */

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public Boolean getAltera() {
		return altera;
	}

	public void setAltera(Boolean altera) {
		this.altera = altera;
	}

	public Solicitacao getSolicitacao() {
		return solicitacao;
	}

	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public TipoEnum getTipoEnum() {
		return tipoEnum;
	}

	public void setTipoEnum(TipoEnum tipoEnum) {
		this.tipoEnum = tipoEnum;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public Boolean getAdm() {
		return adm;
	}

	public void setAdm(Boolean adm) {
		this.adm = adm;
	}

	public Boolean getComum() {
		return comum;
	}

	public void setComum(Boolean comum) {
		this.comum = comum;
	}
	
	

}