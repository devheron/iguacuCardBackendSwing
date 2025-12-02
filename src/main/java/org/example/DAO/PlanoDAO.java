package org.example.DAO;

import jakarta.persistence.EntityManager;
import org.example.config.JPAUtil;
import org.example.model.Empresa;
import org.example.model.Plano;

import java.util.List;

public class PlanoDAO {

    public Plano save(Plano p) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
        em.close();
        return p;
    }

    public Plano update(Plano p) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Plano merged = em.merge(p);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public Plano findById(Long id) {
        EntityManager em = JPAUtil.getEM();
        Plano p = em.find(Plano.class, id);
        em.close();
        return p;
    }

    public List<Plano> findAll() {
        EntityManager em = JPAUtil.getEM();
        List<Plano> lista = em.createQuery("SELECT p FROM Plano p WHERE p.status = 'ATIVO'", Plano.class).getResultList();
        em.close();
        return lista;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Plano p = em.find(Plano.class, id);
        if (p != null) em.remove(p);
        em.getTransaction().commit();
        em.close();
    }

   /* public List<Cartao> findByUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEM();
        List<Cartao> lista = em.createQuery("SELECT c FROM Cartao c WHERE c.usuario = :usuario", Cartao.class)
                .setParameter("usuario", usuario).getResultList();
        em.close();
        return lista;
    }*/

    public List<Plano> findByEmpresa(Empresa empresa) {
        EntityManager em = JPAUtil.getEM();
        List<Plano> lista = em.createQuery("SELECT p FROM Plano p WHERE p.empresa = :empresa", Plano.class)
                .setParameter("empresa", empresa).getResultList();
        em.close();
        return lista;
    }
}