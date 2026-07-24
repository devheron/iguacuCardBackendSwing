package org.example.swing.beneficios;

import org.example.model.Beneficio;
import org.example.model.Plano;
import org.example.service.BeneficioService;
import org.example.service.PlanoService;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class BeneficioFormFrame extends JFrame {

    private Beneficio beneficio;
    private BeneficioListaFrame parent;
    private BeneficioService service = new BeneficioService();
    private PlanoService planoService = new PlanoService();

    public BeneficioFormFrame(Beneficio b, BeneficioListaFrame parent) {

        this.beneficio = b;
        this.parent = parent;

        setTitle(b == null ? "Novo Benefício" : "Editar Benefício");
        setSize(500, 380);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar(b == null ? "Novo Benefício" : "Editar Benefício",
                "Vincule o benefício ao plano correspondente"), BorderLayout.NORTH);

        JTextField nome = new JTextField();
        JComboBox<Plano> planoBox = new JComboBox<>();
        UITheme.styleTextField(nome);
        UITheme.styleComboBox(planoBox);

        for (Plano p : planoService.listar()) planoBox.addItem(p);

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
        gbc.gridy = y++; card.add(UITheme.formLabel("Plano:"), gbc);
        gbc.gridy = y++; card.add(planoBox, gbc);
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

        if (b != null) {
            nome.setText(b.getNome());
            planoBox.setSelectedItem(b.getPlano());
        }

        salvar.addActionListener(e -> {

            if (beneficio == null) beneficio = new Beneficio();

            beneficio.setNome(nome.getText());
            beneficio.setPlano((Plano) planoBox.getSelectedItem());

            if (beneficio.getId() == null) service.salvar(beneficio);
            else service.atualizar(beneficio);

            parent.carregar();
            dispose();
        });
    }
}
