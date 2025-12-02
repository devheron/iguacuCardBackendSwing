package org.example.service;

import org.example.DAO.EmpresaDAO;
import org.example.model.Empresa;

import java.util.List;

public class EmpresaService {

    private final EmpresaDAO dao = new EmpresaDAO();

    public Empresa salvar(Empresa e) {
        return dao.save(e);
    }

    public Empresa atualizar(Empresa e) {
        return dao.update(e);
    }

    public Empresa buscar(Long id) {
        return dao.findById(id);
    }

    public List<Empresa> listar() {
        return dao.findAll();
    }

    public void deletar(Long id) {
        dao.softDelete(id);
    }
}