package org.example.swing.cartoes;

import org.example.model.Cartao;
import org.example.service.CartaoService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CartaoListaFrame extends JFrame {

    private CartaoService service = new CartaoService();
    private JTable tabela;

    public CartaoListaFrame() {

        setTitle("Cartões");
        setSize(700,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topo = new JPanel();
        JButton novo = new JButton("Novo Cartão");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");

        topo.add(novo);
        topo.add(editar);
        topo.add(excluir);

        tabela = new JTable();
        carregar();

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

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
    }
}
