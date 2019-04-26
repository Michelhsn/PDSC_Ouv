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

import br.edu.ifpe.pdsc_modelo.entidades.User;
 
@Stateful
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 20)
public class LoginBean implements Login {
 
    @PersistenceContext(unitName = "pu", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;
 
    @PostConstruct
    private void initializeBean(){
    }
 
 
	@Override
	public User cadastrarUsuario(String nome, String senha) {
		User user = new User();
		user.setNome(nome);
		user.setSenha(senha);
		entityManager.persist(user);
		return user;
	}

	@Override
	public User login(String nome, String senha) {
		String jpql = ("select u from User u where u.nome= :pNome and u.senha= :pSenha");
        Query query = entityManager.createQuery(jpql);
        query.setParameter("pNome", nome);
        query.setParameter("pSenha", senha);
        User usuario = (User)query.getSingleResult();
		return usuario;
	}


	@Override
	public User getUsuario(int id) {
		User user = entityManager.find(User.class, id);
		if (user != null)
            return user;
		return null;
	} 


	@Override
	public List<User> getAllUsers() {
		String jpql = ("select u from User u");
        Query query = entityManager.createQuery(jpql, User.class);
        List<User> usuarios = query.getResultList();
        if (usuarios!=null) {
        	return usuarios;
        }
		return null;
	}


	@Override
	public User getUserByToken(String token) {
		String jpql = ("select u from User u where u.token= :pToken");
        Query query = entityManager.createQuery(jpql);
        query.setParameter("pToken", token);
        User usuario = (User)query.getSingleResult();
		return usuario;
	}


	@Override
	public void updateUser(User user) {	
		entityManager.merge(user);
	}

    
}