package org.example.DAO;

import jakarta.persistence.EntityManager;
import org.example.config.JPAUtil;
import org.example.model.Cartao;
import org.example.model.Empresa;
import org.example.model.Transacao;
import org.example.model.Usuario;

import java.util.List;

public class TransacaoDAO {

    public Transacao save(Transacao t) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
        em.close();
        return t;
    }

    public Transacao update(Transacao t) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Transacao merged = em.merge(t);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public Transacao findById(Long id) {
        EntityManager em = JPAUtil.getEM();
        Transacao t = em.find(Transacao.class, id);
        em.close();
        return t;
    }

    public List<Transacao> findAll() {
        EntityManager em = JPAUtil.getEM();
        List<Transacao> lista = em.createQuery("SELECT t FROM Transacao t", Transacao.class).getResultList();
        em.close();
        return lista;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Transacao t = em.find(Transacao.class, id);
        if (t != null) em.remove(t);
        em.getTransaction().commit();
        em.close();
    }

    public List<Transacao> findByEmpresa(Empresa empresa) {
        EntityManager em = JPAUtil.getEM();
        List<Transacao> lista = em.createQuery("SELECT t FROM Transacao t WHERE t.empresa = :empresa", Transacao.class)
                .setParameter("empresa", empresa).getResultList();
        em.close();
        return lista;
    }

    public List<Transacao> findByUsuario(Usuario usuario) {
        EntityManager em = JPAUtil.getEM();
        List<Transacao> lista = em.createQuery(
                        "SELECT t FROM Transacao t WHERE t.cartao.usuario = :usuario", Transacao.class)
                .setParameter("usuario", usuario).getResultList();
        em.close();
        return lista;
    }

    public List<Transacao> findByCartao(Cartao cartao) {
        EntityManager em = JPAUtil.getEM();
        List<Transacao> lista = em.createQuery("SELECT t FROM Transacao t WHERE t.cartao = :cartao", Transacao.class)
                .setParameter("cartao", cartao).getResultList();
        em.close();
        return lista;
    }
}