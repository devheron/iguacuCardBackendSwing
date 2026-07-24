package org.example.swing.empresas;

import org.example.model.Empresa;
import org.example.service.EmpresaService;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EmpresaListaFrame extends JFrame {

    private EmpresaService service = new EmpresaService();
    private JTable tabela;

    public EmpresaListaFrame() {

        setTitle("Empresas");
        setSize(820, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar("Empresas", "Cadastre e gerencie empresas parceiras"), BorderLayout.NORTH);

        JPanel topo = UITheme.toolbar();
        JButton novo    = UITheme.successButton("➕  Nova Empresa");
        JButton editar  = UITheme.primaryButton("✎  Editar");
        JButton excluir = UITheme.dangerButton("🗑  Excluir");
        topo.add(novo);
        topo.add(editar);
        topo.add(excluir);

        tabela = new JTable();
        UITheme.styleTable(tabela);
        carregarTabela();

        JScrollPane sp = new JScrollPane(tabela);
        sp.setBorder(new EmptyBorder(0, 16, 16, 16));
        sp.getViewport().setBackground(UITheme.CARD_BG);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(UITheme.BG);
        body.add(topo, BorderLayout.NORTH);
        body.add(sp, BorderLayout.CENTER);
        add(body, BorderLayout.CENTER);

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
        UITheme.styleTable(tabela);
    }
}
