package br.edu.ifpe.pdsc_modelo.ejb;

import java.util.List;

import javax.ejb.Local;

import br.edu.ifpe.pdsc_modelo.entidades.User;
 
@Local
public interface Login {
 
      User cadastrarUsuario(String nome, String senha);
      
      User login(String nome, String senha);
      
      User getUsuario(int id);
      
      User getUserByToken(String token);
      
      List<User> getAllUsers();

	void updateUser(User user);
}