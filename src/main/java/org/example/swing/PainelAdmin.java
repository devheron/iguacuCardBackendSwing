package org.example.swing;

import javax.swing.*;
import java.awt.*;

import org.example.swing.planos.PlanoAdminFormFrame;
import org.example.swing.usuarios.UsuarioListaFrame;
import org.example.swing.empresas.EmpresaListaFrame;
import org.example.swing.planos.PlanoListaFrame;
import org.example.swing.beneficios.BeneficioListaFrame;
import org.example.swing.cartoes.CartaoListaFrame;
import org.example.swing.transacoes.TransacaoListaFrame;

public class PainelAdmin extends JFrame {

    public PainelAdmin() {

        setTitle("Painel Administrativo");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        JButton btnUsuarios = new JButton("Gerenciar Usuários");
        JButton btnEmpresas = new JButton("Gerenciar Empresas");
        JButton btnPlanos = new JButton("Gerenciar Planos");
        JButton btnCriarPlano = new JButton("Criar Plano");
        btnCriarPlano.addActionListener(e -> new PlanoAdminFormFrame().setVisible(true));
        add(btnCriarPlano);
        JButton btnBeneficios = new JButton("Gerenciar Benefícios");
        JButton btnCartoes = new JButton("Cartões dos Usuários");
        JButton btnTransacoes = new JButton("Transações");



        add(btnUsuarios);
        add(btnEmpresas);
        add(btnPlanos);
        add(btnBeneficios);
        add(btnCartoes);
        add(btnTransacoes);

        btnUsuarios.addActionListener(e -> new UsuarioListaFrame().setVisible(true));
        btnEmpresas.addActionListener(e -> new EmpresaListaFrame().setVisible(true));
        btnPlanos.addActionListener(e -> new PlanoListaFrame().setVisible(true));
        btnBeneficios.addActionListener(e -> new BeneficioListaFrame().setVisible(true));
        btnCartoes.addActionListener(e -> new CartaoListaFrame().setVisible(true));
        btnTransacoes.addActionListener(e -> new TransacaoListaFrame().setVisible(true));

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });
        add(btnVoltar, BorderLayout.SOUTH);

    }
}
