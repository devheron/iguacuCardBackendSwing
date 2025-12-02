package org.example.swing.beneficios;

import org.example.model.Beneficio;
import org.example.model.Plano;
import org.example.service.BeneficioService;
import org.example.service.PlanoService;

import javax.swing.*;
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
        setSize(400,250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,2));

        JTextField nome = new JTextField();
        JComboBox<Plano> planoBox = new JComboBox<>();

        for (Plano p : planoService.listar()) planoBox.addItem(p);

        JButton salvar = new JButton("Salvar");

        add(new JLabel("Nome:"));
        add(nome);
        add(new JLabel("Plano:"));
        add(planoBox);
        add(new JLabel());
        add(salvar);

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
