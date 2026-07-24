package org.example.swing.planos;

import org.example.model.Empresa;
import org.example.model.Plano;
import org.example.service.EmpresaService;
import org.example.service.PlanoService;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(500, 420);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar(plano == null ? "Novo Plano" : "Editar Plano",
                "Configure o plano vinculado à empresa"), BorderLayout.NORTH);

        JTextField nome = new JTextField();
        JTextField valor = new JTextField();
        JComboBox<Empresa> empresaBox = new JComboBox<>();
        UITheme.styleTextField(nome);
        UITheme.styleTextField(valor);
        UITheme.styleComboBox(empresaBox);
        for (Empresa e : empresaService.listar()) empresaBox.addItem(e);

        JButton salvar = UITheme.successButton("💾  Salvar");

        JPanel card = UITheme.card(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(4, 0, 4, 0);
        int y = 0;
        gbc.gridy = y++; card.add(UITheme.formLabel("Nome:"), gbc);
        gbc.gridy = y++; card.add(nome, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Valor:"), gbc);
        gbc.gridy = y++; card.add(valor, gbc);
        gbc.gridy = y++; card.add(UITheme.formLabel("Empresa:"), gbc);
        gbc.gridy = y++; card.add(empresaBox, gbc);
        gbc.gridy = y++;
        gbc.insets = new Insets(14, 0, 4, 0);
        card.add(salvar, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UITheme.BG);
        wrapper.setBorder(new EmptyBorder(16, 24, 16, 24));
        GridBagConstraints wc = new GridBagConstraints();
        wc.fill = GridBagConstraints.HORIZONTAL;
        wc.weightx = 1.0;
        wrapper.add(card, wc);
        add(wrapper, BorderLayout.CENTER);

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
