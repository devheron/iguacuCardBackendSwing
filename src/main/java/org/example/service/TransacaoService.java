package org.example.service;

import org.example.DAO.TransacaoDAO;
import org.example.DAO.CartaoDAO;
import org.example.model.Transacao;
import org.example.model.Cartao;
import org.example.model.Empresa;
import org.example.model.Usuario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TransacaoService {

    private final TransacaoDAO dao = new TransacaoDAO();
    private final CartaoDAO cartaoDAO = new CartaoDAO();

    public Transacao registrarTransacao(Long cartaoId, BigDecimal valor, String descricao) {
        Cartao cartao = cartaoDAO.findById(cartaoId);
        if (cartao == null) throw new IllegalArgumentException("Cartão não encontrado");

        Transacao t = new Transacao();
        t.setCartao(cartao);
        t.setEmpresa(cartao.getEmpresa());
        t.setValor(valor);
        t.setDescricao(descricao);
        t.setDataHora(LocalDateTime.now());

        return dao.save(t);
    }
    public List<Transacao> findByUsuario(Usuario usuario) {
        return dao.findByUsuario(usuario);
    }

    public List<Transacao> listarTodos() {
        return dao.findAll();
    }

    public List<Transacao> findByEmpresa(Empresa empresa) {
        return dao.findByEmpresa(empresa);
    }

    public List<Transacao> findByCartao(Cartao cartao) {
        return dao.findByCartao(cartao);
    }

    public Transacao buscar(Long id) {
        return dao.findById(id);
    }

    public void deletar(Long id) {
        dao.delete(id);
    }
}