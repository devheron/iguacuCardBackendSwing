package org.example.DAO;

import jakarta.persistence.EntityManager;
import org.example.config.JPAUtil;
import org.example.model.Empresa;

import java.util.List;

public class EmpresaDAO {

    public Empresa save(Empresa e) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
        em.close();
        return e;
    }

    public Empresa update(Empresa e) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Empresa merged = em.merge(e);
        em.getTransaction().commit();
        em.close();
        return merged;
    }

    public Empresa findById(Long id) {
        EntityManager em = JPAUtil.getEM();
        Empresa e = em.find(Empresa.class, id);
        em.close();
        return e;
    }

    public List<Empresa> findAll() {
        EntityManager em = JPAUtil.getEM();
        List<Empresa> lista = em.createQuery("SELECT e FROM Empresa e WHERE e.status = 'ATIVO'", Empresa.class).getResultList();
        em.close();
        return lista;
    }

    public void delete(Long id) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Empresa e = em.find(Empresa.class, id);
        if (e != null) em.remove(e);
        em.getTransaction().commit();
        em.close();
    }

    public void softDelete(Long id) {
        EntityManager em = JPAUtil.getEM();
        em.getTransaction().begin();
        Empresa e = em.find(Empresa.class, id);
        if (e != null) {
            e.setStatus("INATIVO");
            em.merge(e);
        }
        em.getTransaction().commit();
        em.close();
    }
}