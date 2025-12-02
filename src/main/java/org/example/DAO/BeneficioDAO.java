package org.example.DAO;

import jakarta.persistence.EntityManager;
import org.example.config.JPAUtil;
import org.example.model.Beneficio;

import java.util.List;

public class BeneficioDAO {

    public void save(Beneficio b) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        em.persist(b);
        em.getTransaction().commit();
        em.close();
    }

    public Beneficio update(Beneficio b) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Beneficio merged = em.merge(b);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public List<Beneficio> findAll() {
        EntityManager em = JPAUtil.getEM();
        List<Beneficio> lista = em.createQuery(
                "SELECT b FROM Beneficio b WHERE b.status='ATIVO'",
                Beneficio.class
        ).getResultList();
        em.close();
        return lista;
    }

    public Beneficio find(Long id) {
        EntityManager em = JPAUtil.getEM();
        Beneficio b = em.find(Beneficio.class, id);
        em.close();
        return b;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Beneficio b = em.find(Beneficio.class, id);
        if (b != null) b.setStatus("INATIVO");
        em.getTransaction().commit();
        em.close();
    }
}
