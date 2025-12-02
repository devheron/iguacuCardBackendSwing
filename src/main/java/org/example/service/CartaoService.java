package org.example.service;

import org.example.DAO.CartaoDAO;
import org.example.model.Cartao;
import org.example.model.Empresa;
import org.example.model.Plano;
import org.example.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class CartaoService {

    private final CartaoDAO dao = new CartaoDAO();

    public Cartao salvar(Cartao c) {
        return dao.save(c);
    }

    public Cartao atualizar(Cartao c) {
        return dao.update(c);
    }

    public Cartao buscar(Long id) {
        return dao.findById(id);
    }

    public List<Cartao> listarTodos() {
        return dao.findAll();
    }

    public List<Cartao> findByUsuario(Usuario usuario) {
        return dao.findByUsuario(usuario);
    }

    public List<Cartao> findByEmpresa(Empresa empresa) {
        return dao.findByEmpresa(empresa);
    }


    public List<Cartao> findByPlano(Plano plano) {
        return dao.findByPlano(plano);
    }

    public void deletar(Long id) {
        dao.delete(id);
    }

    public Cartao emitirCartaoSocial(Usuario usuario, Plano plano) {
        Cartao c = new Cartao();
        c.setNumero(gerarNumero());
        c.setUsuario(usuario);
        c.setPlano(plano);
        c.setEmpresa(plano.getEmpresa());
        c.setTitular(usuario.getNome());
        c.setTipo("SOCIAL");
        c.setStatus("ATIVO");
        c.setSaldo(BigDecimal.ZERO);
        c.setValidade(LocalDate.now().plusYears(5).toString());
        return dao.save(c);
    }

    private String gerarNumero() {
        Random rng = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) sb.append(rng.nextInt(10));
        return sb.toString();
    }

}