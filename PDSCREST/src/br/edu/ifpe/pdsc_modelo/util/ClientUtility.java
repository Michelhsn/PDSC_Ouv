package br.edu.ifpe.pdsc_modelo.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.edu.ifpe.pdsc_modelo.ejb.Login;
import br.edu.ifpe.pdsc_modelo.ejb.SolicitacaoInterface;
import br.edu.ifpe.pdsc_modelo.entidades.Solicitacao;


public class ClientUtility {

	public static Context getInitialContext() throws NamingException {
		Properties prop = new Properties();
		prop.setProperty("java.naming.factory.initial", "com.sun.enterprise.naming.SerialInitContextFactory");
		prop.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
		prop.setProperty("java.naming.factory.statel", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
		return new InitialContext(prop);
	}
	
	public static Login getLoginBean() throws NamingException {
		javax.naming.Context ic = getInitialContext();
		Login loginbean = (Login) ic.lookup(
				"java:global/PDSCEJBEAR/PDSCEJB/LoginBean!br.edu.ifpe.pdsc_modelo.ejb.Login");
		return loginbean;
	}
	
	public static SolicitacaoInterface getSolicitacaoBean() throws NamingException {
		javax.naming.Context ic = getInitialContext();
		SolicitacaoInterface solicitacaoBean = (SolicitacaoInterface) ic.lookup(
				"java:global/PDSCEJBEAR/PDSCEJB/SolicitacaoBean!br.edu.ifpe.pdsc_modelo.ejb.SolicitacaoInterface");
		return solicitacaoBean;
	}
	
}