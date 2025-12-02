package org.example.swing.transacoes;

import org.example.model.Transacao;
import org.example.model.Usuario;
import org.example.model.Empresa;
import org.example.service.TransacaoService;
import org.example.session.SessionContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class TransacaoListaFrame extends JFrame {

    private final TransacaoService service = new TransacaoService();
    private JTable tabela;

    public TransacaoListaFrame() {
        setTitle("Transações");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tabela = new JTable();
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        carregarTabela();
    }

    private void carregarTabela() {
        List<Transacao> transacoes;
        if (SessionContext.isAdmin()) {
            transacoes = service.listarTodos();
        } else if (SessionContext.isEmpresa()) {
            transacoes = service.findByEmpresa(SessionContext.getEmpresaLogada());
        } else {
            transacoes = service.findByUsuario(SessionContext.getCurrentUser());
        }

        String[] colunas = {"ID", "Descrição", "Valor", "Cartão", "Empresa", "Data"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);

        for (Transacao t : transacoes) {
            model.addRow(new Object[]{
                    t.getId(),
                    t.getDescricao(),
                    t.getValor(),
                    t.getCartao().getNumero(),
                    t.getEmpresa().getNome(),
                    t.getDataHora()
            });
        }

        tabela.setModel(model);
    }
}