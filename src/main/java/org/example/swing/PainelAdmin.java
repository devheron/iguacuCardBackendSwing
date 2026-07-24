package org.example.swing;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import org.example.swing.planos.PlanoAdminFormFrame;
import org.example.swing.ui.UITheme;
import org.example.swing.usuarios.UsuarioListaFrame;
import org.example.swing.empresas.EmpresaListaFrame;
import org.example.swing.planos.PlanoListaFrame;
import org.example.swing.beneficios.BeneficioListaFrame;
import org.example.swing.cartoes.CartaoListaFrame;
import org.example.swing.transacoes.TransacaoListaFrame;

public class PainelAdmin extends JFrame {

    public PainelAdmin() {

        setTitle("Painel Administrativo");
        setSize(720, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        add(UITheme.headerBar("Painel Administrativo", "Gerencie todos os módulos do IguaçuCard"), BorderLayout.NORTH);

        JButton btnUsuarios   = UITheme.primaryButton("👤   Gerenciar Usuários");
        JButton btnEmpresas   = UITheme.primaryButton("🏢   Gerenciar Empresas");
        JButton btnPlanos     = UITheme.primaryButton("📋   Gerenciar Planos");
        JButton btnCriarPlano = UITheme.successButton("➕   Criar Plano");
        JButton btnBeneficios = UITheme.primaryButton("🎁   Gerenciar Benefícios");
        JButton btnCartoes    = UITheme.primaryButton("💳   Cartões dos Usuários");
        JButton btnTransacoes = UITheme.primaryButton("💰   Transações");
        JButton btnVoltar     = UITheme.dangerButton("↩   Sair / Voltar ao Login");

        btnCriarPlano.addActionListener(e -> new PlanoAdminFormFrame().setVisible(true));
        btnUsuarios.addActionListener(e -> new UsuarioListaFrame().setVisible(true));
        btnEmpresas.addActionListener(e -> new EmpresaListaFrame().setVisible(true));
        btnPlanos.addActionListener(e -> new PlanoListaFrame().setVisible(true));
        btnBeneficios.addActionListener(e -> new BeneficioListaFrame().setVisible(true));
        btnCartoes.addActionListener(e -> new CartaoListaFrame().setVisible(true));
        btnTransacoes.addActionListener(e -> new TransacaoListaFrame().setVisible(true));
        btnVoltar.addActionListener(e -> {
            org.example.session.SessionContext.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });

        JPanel grid = new JPanel(new GridLayout(4, 2, 14, 14));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(24, 24, 12, 24));
        grid.add(btnUsuarios);
        grid.add(btnEmpresas);
        grid.add(btnPlanos);
        grid.add(btnCriarPlano);
        grid.add(btnBeneficios);
        grid.add(btnCartoes);
        grid.add(btnTransacoes);
        grid.add(btnVoltar);

        add(grid, BorderLayout.CENTER);
    }
}
