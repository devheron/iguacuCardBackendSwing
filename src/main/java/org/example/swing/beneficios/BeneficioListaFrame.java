package org.example.swing.beneficios;

import org.example.model.Beneficio;
import org.example.service.BeneficioService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BeneficioListaFrame extends JFrame {

    private BeneficioService service = new BeneficioService();
    private JTable tabela;

    public BeneficioListaFrame() {

        setTitle("Benefícios");
        setSize(700,400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topo = new JPanel();
        JButton novo = new JButton("Novo Benefício");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");

        topo.add(novo);
        topo.add(editar);
        topo.add(excluir);

        tabela = new JTable();
        carregar();

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

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
    }
}
