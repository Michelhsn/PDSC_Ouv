package br.edu.ifpe.pdsc_modelo.relatorio;
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.pdf.codec.Base64.InputStream;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import br.edu.ifpe.pdsc_modelo.entidades.Solicitacao;
 
public class GeraRelatorio implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String path; //Caminho base
	
	private String pathToReportPackage; // Caminho para o package onde estão armazenados os relatorios Jarper
	
	//Recupera os caminhos para que a classe possa encontrar os relatórios
	public GeraRelatorio() {
		this.path = this.getClass().getClassLoader().getResource("").getPath();
		this.pathToReportPackage = "C:/Users/miche/eclipse-workspace/PDSCREST/src/br/edu/ifpe/pdsc_modelo/relatorio/";
		System.out.println(path);
	}
	
	
	//Imprime/gera uma lista de Clientes
	public void imprimir(List<Solicitacao> solicitacoes) throws Exception	
	{
		java.io.InputStream template = GeraRelatorio.class.getResourceAsStream("/br/edu/ifpe/pdsc_modelo/relatorio/" +
				 "relatorioSolicitacao.jrxml");
		JasperReport report = JasperCompileManager.compileReport(template);
		
		JasperPrint print = JasperFillManager.fillReport(report, null, new JRBeanCollectionDataSource(solicitacoes));
 
		//JasperExportManager.exportReportToPdfFile(print, "C:/Users/miche/eclipse-workspace/Relatorio_Solicitacao.pdf");	
		
		/*
		 * java.io.InputStream template =
		 * this.getClass().getResourceAsStream(this.getPathToReportPackage() +
		 * "relatorioSolicitacao.jrxml"); // compile the report from the stream
		 * JasperReport report1 = JasperCompileManager.compileReport(template); // fill
		 * out the report into a print object, ready for export. JasperPrint print1 =
		 * JasperFillManager.fillReport(report1, null, new
		 * JRBeanCollectionDataSource(solicitacoes));
		 */
		OutputStream os = null;
			FacesContext context = FacesContext.getCurrentInstance();
	        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
	        os = response.getOutputStream();
	        byte[] pdf = null;
	        pdf = JasperExportManager.exportReportToPdf(print);
	        response.setContentType("application/pdf");
	        response.setContentLength(pdf.length);
	        response.setHeader("Content-disposition", "attachment; filename=\""+"relatorioSolici"+"\"");

	        context.responseComplete();

	        os.write(pdf);
	        os.close();
		
		//File pdf1 = File.createTempFile("output.", ".pdf");
		//JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(pdf1));
	}
 
	public String getPathToReportPackage() {
		return this.pathToReportPackage;
	}
	
	public String getPath() {
		return this.path;
	}
}