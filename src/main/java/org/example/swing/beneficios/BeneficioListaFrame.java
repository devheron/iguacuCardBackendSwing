package org.example.swing.beneficios;

import org.example.model.Beneficio;
import org.example.service.BeneficioService;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BeneficioListaFrame extends JFrame {

    private BeneficioService service = new BeneficioService();
    private JTable tabela;

    public BeneficioListaFrame() {

        setTitle("Benefícios");
        setSize(820, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar("Benefícios", "Benefícios oferecidos por cada plano"),
                BorderLayout.NORTH);

        JPanel topo = UITheme.toolbar();
        JButton novo    = UITheme.successButton("➕  Novo Benefício");
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

        novo.addActionListener(e -> new BeneficioFormFrame(null,this).setVisible(true));

        editar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row==-1) return;
            Long id = Long.valueOf(tabela.getValueAt(row,0).toString());
            Beneficio b = service.buscar(id);
            new BeneficioFormFrame(b,this).setVisible(true);
        });

        excluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row==-1) return;
            Long id = Long.valueOf(tabela.getValueAt(row,0).toString());
            service.deletar(id);
            carregar();
        });
    }

    public void carregar() {
        DefaultTableModel m = new DefaultTableModel(
                new Object[]{"ID","Benefício","Plano"},0
        );


        for (Beneficio b : service.listar()) {
            String nomePlano = b.getPlano() != null ? b.getPlano().getNome() : "(sem plano)";
            m.addRow(new Object[]{
                    b.getId(), b.getNome(), nomePlano
            });
        }
        tabela.setModel(m);
        UITheme.styleTable(tabela);
    }
}
