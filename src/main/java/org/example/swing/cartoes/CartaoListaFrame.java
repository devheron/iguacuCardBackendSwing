package org.example.swing.cartoes;

import org.example.model.Cartao;
import org.example.service.CartaoService;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CartaoListaFrame extends JFrame {

    private CartaoService service = new CartaoService();
    private JTable tabela;

    public CartaoListaFrame() {

        setTitle("Cartões");
        setSize(820, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar("Cartões", "Todos os cartões sociais emitidos"), BorderLayout.NORTH);

        JPanel topo = UITheme.toolbar();
        JButton novo    = UITheme.successButton("➕  Novo Cartão");
        JButton editar  = UITheme.primaryButton("✎  Editar");
        JButton excluir = UITheme.dangerButton("🗑  Excluir");

        topo.add(novo);
        topo.add(editar);
        topo.add(excluir);

        tabela = new JTable();
        UITheme.styleTable(tabela);
        carregar();

        JScrollPane sp = new JScrollPane(tabela);
        sp.setBorder(new EmptyBorder(0, 16, 16, 16));
        sp.getViewport().setBackground(UITheme.CARD_BG);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(UITheme.BG);
        body.add(topo, BorderLayout.NORTH);
        body.add(sp, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

        novo.addActionListener(e -> new CartaoFormFrame(null,this).setVisible(true));

        editar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) return;
            Long id = Long.valueOf(tabela.getValueAt(row,0).toString());
            Cartao c = service.buscar(id);
            new CartaoFormFrame(c,this).setVisible(true);
        });

        excluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) return;
            Long id = Long.valueOf(tabela.getValueAt(row,0).toString());
            service.deletar(id);
            carregar();
        });
    }

    public void carregar() {
        DefaultTableModel m = new DefaultTableModel(
                new Object[]{"ID","Número","Validade","Usuário","Plano"},0
        );

        for (Cartao c : service.listarTodos()) {
            m.addRow(new Object[]{
                    c.getId(),
                    c.getNumero(),
                    c.getValidade(),
                    c.getUsuario().getNome(),
                    c.getPlano().getNome()
            });
        }

        tabela.setModel(m);
        UITheme.styleTable(tabela);
    }
}
