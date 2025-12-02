package org.example.DAO;

import jakarta.persistence.EntityManager;
import org.example.config.JPAUtil;
import org.example.model.Usuario;

import java.util.List;

public class UsuarioDAO {

    public Usuario save(Usuario u) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
        em.close();
        return u;
    }

    public Usuario update(Usuario u) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Usuario merged = em.merge(u);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public Usuario findById(Long id) {
        EntityManager em = JPAUtil.getEM();
        Usuario u = em.find(Usuario.class, id);
        em.close();
        return u;
    }

    public List<Usuario> findAll() {
        EntityManager em = JPAUtil.getEM();
        List<Usuario> lista = em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        em.close();
        return lista;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Usuario u = em.find(Usuario.class, id);
        if (u != null) em.remove(u);
        em.getTransaction().commit();
        em.close();
    }

    public Usuario findByLogin(String login) {
        EntityManager em = JPAUtil.getEM();
        List<Usuario> lista = em.createQuery(
                        "SELECT u FROM Usuario u LEFT JOIN FETCH u.empresa WHERE u.login = :login", Usuario.class)
                .setParameter("login", login).getResultList();
        em.close();
        return lista.isEmpty() ? null : lista.get(0);
    }
}