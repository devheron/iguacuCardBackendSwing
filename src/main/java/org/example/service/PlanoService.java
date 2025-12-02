package org.example.service;

import org.example.DAO.PlanoDAO;
import org.example.DAO.CartaoDAO;
import org.example.model.Empresa;
import org.example.model.Plano;
import org.example.model.Usuario;
import org.example.model.Cartao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class PlanoService {

    private final PlanoDAO dao = new PlanoDAO();
    private final CartaoDAO cartaoDAO = new CartaoDAO();

    public Plano salvar(Plano p) {
        return dao.save(p);
    }

    public Plano atualizar(Plano p) {
        return dao.update(p);
    }

    //public List<Plano> findByUsuario(Usuario usuario) {
    //    return dao.findByUsuario(usuario);
    //}

    public List<Plano> findByEmpresa(Empresa empresa) {
        return dao.findByEmpresa(empresa);
    }

    public Plano buscar(Long id) {
        return dao.findById(id);
    }

    public List<Plano> listar() {
        return dao.findAll();
    }

    public void deletar(Long id) {
        dao.delete(id);
    }

    // ✅ Vínculo com usuário: gera cartão social
    public Cartao vincularPlanoAoUsuario(Long planoId, Usuario usuario) {
        Plano plano = dao.findById(planoId);
        if (plano == null || usuario == null) throw new IllegalArgumentException("Dados inválidos");

        Cartao cartao = new Cartao();
        cartao.setNumero(gerarNumero());
        cartao.setUsuario(usuario);
        cartao.setPlano(plano);
        cartao.setEmpresa(plano.getEmpresa());
        cartao.setSaldo(BigDecimal.ZERO);
        cartao.setTipo("SOCIAL");
        cartao.setStatus("ATIVO");
        cartao.setValidade(LocalDate.now().plusYears(5).toString());

        return cartaoDAO.save(cartao);
    }

    private String gerarNumero() {
        Random rng = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) sb.append(rng.nextInt(10));
        return sb.toString();
    }
}