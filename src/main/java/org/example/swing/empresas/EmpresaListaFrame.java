package org.example.swing.empresas;

import org.example.model.Empresa;
import org.example.service.EmpresaService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmpresaListaFrame extends JFrame {

    private EmpresaService service = new EmpresaService();
    private JTable tabela;

    public EmpresaListaFrame() {

        setTitle("Empresas");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel topo = new JPanel();
        JButton novo = new JButton("Nova Empresa");
        JButton editar = new JButton("Editar");
        JButton excluir = new JButton("Excluir");
        topo.add(novo);
        topo.add(editar);
        topo.add(excluir);

        tabela = new JTable();
        carregarTabela();

        add(topo, BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        novo.addActionListener(e -> new EmpresaFormFrame(null, this).setVisible(true));

        editar.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) return;
            Long id = Long.valueOf(tabela.getValueAt(row, 0).toString());
            Empresa emp = service.buscar(id);
            new EmpresaFormFrame(emp, this).setVisible(true);
        });

        excluir.addActionListener(e -> {
            int row = tabela.getSelectedRow();
            if (row == -1) return;
            Long id = Long.valueOf(tabela.getValueAt(row, 0).toString());
            service.deletar(id);
            carregarTabela();
        });
    }

    public void carregarTabela() {

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID","Nome","CNPJ","Telefone"},0
        );

        for (Empresa e : service.listar()) {
            model.addRow(new Object[]{
                    e.getId(), e.getNome(), e.getCnpj(), e.getTelefone()
            });
        }

        tabela.setModel(model);
    }
}
