package org.example.service;

import org.example.DAO.BeneficioDAO;
import org.example.model.Beneficio;

import java.util.List;

public class BeneficioService {

    private final BeneficioDAO dao = new BeneficioDAO();

    public void salvar(Beneficio b) { dao.save(b); }
    public Beneficio atualizar(Beneficio b) { return dao.update(b); }
    public List<Beneficio> listar() { return dao.findAll(); }
    public Beneficio buscar(Long id) { return dao.find(id); }
    public void deletar(Long id) { dao.delete(id); }
}

