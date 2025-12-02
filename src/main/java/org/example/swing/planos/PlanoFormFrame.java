package org.example.swing.planos;

import org.example.model.Empresa;
import org.example.model.Plano;
import org.example.service.EmpresaService;
import org.example.service.PlanoService;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public class PlanoFormFrame extends JFrame {

    private Plano plano;
    private PlanoListaFrame parent;
    private final PlanoService service = new PlanoService();
    private final EmpresaService empresaService = new EmpresaService();

    public PlanoFormFrame(Plano p, PlanoListaFrame parent) {
        this.plano = p;
        this.parent = parent;

        setTitle(plano == null ? "Novo Plano" : "Editar Plano");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        JTextField nome = new JTextField();
        JTextField valor = new JTextField();
        JComboBox<Empresa> empresaBox = new JComboBox<>();
        for (Empresa e : empresaService.listar()) empresaBox.addItem(e);

        JButton salvar = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(nome);
        add(new JLabel("Valor:"));
        add(valor);
        add(new JLabel("Empresa:"));
        add(empresaBox);
        add(new JLabel());
        add(salvar);

        if (plano != null) {
            nome.setText(plano.getNome());
            valor.setText(plano.getPreco().toString());
            empresaBox.setSelectedItem(plano.getEmpresa());
        }

        salvar.addActionListener(e -> {
            if (plano == null) plano = new Plano();
            plano.setNome(nome.getText());
            plano.setPreco(new BigDecimal(valor.getText()));
            plano.setEmpresa((Empresa) empresaBox.getSelectedItem());

            if (plano.getId() == null) service.salvar(plano);
            else service.atualizar(plano);

            parent.carregar();
            dispose();
        });
    }
}