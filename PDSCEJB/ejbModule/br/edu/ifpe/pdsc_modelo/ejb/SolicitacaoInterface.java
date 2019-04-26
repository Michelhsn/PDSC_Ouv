package br.edu.ifpe.pdsc_modelo.ejb;

import java.util.List;

import javax.ejb.Local;

import br.edu.ifpe.pdsc_modelo.entidades.Solicitacao;
 
@Local
public interface SolicitacaoInterface {
 
      Solicitacao cadastrarSolicitacao(Solicitacao solicitacao);
      
      Solicitacao getSolicitacao(int id);
      
      List<Solicitacao> getSolicitacoes();
      
      void updateSolicitacao(Solicitacao solicitacao);

	void removeSolicitacao(String solicitacao);
}