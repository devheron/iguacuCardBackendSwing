package org.example.swing;

import org.example.model.Plano;
import org.example.service.PlanoService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class PlanoPanel extends JPanel {
    private final PlanoService service = new PlanoService();
    private final JList<String> lista = new JList<>();

    public PlanoPanel() {
        setLayout(new BorderLayout());
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNovo = new JButton("Novo Plano");
        JButton btnAtual = new JButton("Atualizar");
        top.add(btnNovo);
        top.add(btnAtual);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        btnNovo.addActionListener(e -> {
            try {
                String nome = JOptionPane.showInputDialog(this, "Nome do plano:");
                String desc = JOptionPane.showInputDialog(this, "Descrição:");
                String precoStr = JOptionPane.showInputDialog(this, "Preço (ex: 29.90):");
                Plano p = new Plano();
                p.setNome(nome);
                p.setDescricao(desc);
                p.setPreco(new BigDecimal(precoStr));
                service.salvar(p);
                carregar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        btnAtual.addActionListener(e -> carregar());
        carregar();
    }

    private void carregar() {
        List<Plano> listaPlanos = service.listar();
        DefaultListModel<String> m = new DefaultListModel<>();
        for (Plano p : listaPlanos) {
            m.addElement(String.format("%d | %s | %s | %s", p.getId(), p.getNome(), p.getDescricao(), p.getPreco()));
        }
        lista.setModel(m);
    }
}
