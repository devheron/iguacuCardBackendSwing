package org.example.DAO;

import jakarta.persistence.EntityManager;
import org.example.config.JPAUtil;
import org.example.model.Cartao;
import org.example.model.Empresa;
import org.example.model.Plano;
import org.example.model.Usuario;

import java.util.List;

public class CartaoDAO {

    public Cartao save(Cartao c) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        em.persist(c);
        em.getTransaction().commit();
        em.close();
        return c;
    }

    public Cartao update(Cartao c) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Cartao merged = em.merge(c);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public Cartao findById(Long id) {
        EntityManager em = JPAUtil.getEM();
        Cartao c = em.find(Cartao.class, id);
        em.close();
        return c;
    }

    public List<Cartao> findAll() {
        EntityManager em = JPAUtil.getEM();
        List<Cartao> lista = em.createQuery("SELECT c FROM Cartao c", Cartao.class).getResultList();
        em.close();
        return lista;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Cartao c = em.find(Cartao.class, id);
        if (c != null) em.remove(c);
        em.getTransaction().commit();
        em.close();
    }

    public List<Cartao> findByUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEM();
        List<Cartao> lista = em.createQuery("SELECT c FROM Cartao c WHERE c.usuario = :usuario", Cartao.class)
                .setParameter("usuario", usuario).getResultList();
        em.close();
        return lista;
    }

    public List<Cartao> findByEmpresa(Empresa empresa) {
        EntityManager em = JPAUtil.getEM();
        List<Cartao> lista = em.createQuery("SELECT c FROM Cartao c WHERE c.empresa = :empresa", Cartao.class)
                .setParameter("empresa", empresa).getResultList();
        em.close();
        return lista;
    }

    public List<Cartao> findByPlano(Plano plano) {
        EntityManager em = JPAUtil.getEM();
        List<Cartao> lista = em.createQuery("SELECT c FROM Cartao c WHERE c.plano = :plano", Cartao.class)
                .setParameter("plano", plano).getResultList();
        em.close();
        return lista;
    }

    public Cartao findByNumero(String numero) {
        EntityManager em = JPAUtil.getEM();
        List<Cartao> lista = em.createQuery("SELECT c FROM Cartao c WHERE c.numero = :num", Cartao.class)
                .setParameter("num", numero).getResultList();
        em.close();
        return lista.isEmpty() ? null : lista.get(0);
    }
}