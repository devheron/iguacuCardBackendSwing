package org.example.swing;

import org.example.model.Transacao;
import org.example.service.TransacaoService;
import org.example.DAO.CartaoDAO;
import org.example.model.Cartao;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;


public class TransacaoPanel extends JPanel {
    private final TransacaoService service = new TransacaoService();
    private final CartaoDAO cartaoDAO = new CartaoDAO();

    private final JList<String> lista = new JList<>();

    public TransacaoPanel() {
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNova = new JButton("Nova Transação");
        JButton btnAtual = new JButton("Atualizar");
        top.add(btnNova);
        top.add(btnAtual);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(lista), BorderLayout.CENTER);


        btnNova.addActionListener(e -> {
            try {
                String idStr = JOptionPane.showInputDialog(this, "ID do cartão:");
                if (idStr == null) return;
                Long id = Long.parseLong(idStr);
                Cartao c = cartaoDAO.findById(id);
                if (c == null) { JOptionPane.showMessageDialog(this, "Cartão não encontrado"); return; }

                String valStr = JOptionPane.showInputDialog(this, "Valor:");
                BigDecimal v = new BigDecimal(valStr);
                String desc = JOptionPane.showInputDialog(this, "Descrição:");

                service.registrarTransacao(id, v, desc);
                carregar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnAtual.addActionListener(e -> carregar());
        carregar();
    }

    private void carregar() {
        List<Transacao> todas = service.listarTodos();
        DefaultListModel<String> m = new DefaultListModel<>();
        for (Transacao t : todas) {
            String num = t.getCartao() != null ? t.getCartao().getNumero() : "-";
            m.addElement(String.format("%d | %s | %s | %s", t.getId(), t.getDataHora(), num, t.getValor()));
        }
        lista.setModel(m);
    }
}
