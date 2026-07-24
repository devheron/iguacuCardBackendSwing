package org.example.swing;

import org.example.model.Usuario;
import org.example.model.Cartao;
import org.example.service.CartaoService;
import org.example.session.SessionContext;
import org.example.swing.planos.PlanoListaFrame;
import org.example.swing.cartoes.CartaoListaFrame;
import org.example.swing.transacoes.TransacaoListaFrame;
import org.example.swing.ui.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class PainelUsuario extends JFrame {

    private final CartaoService cartaoService = new CartaoService();

    public PainelUsuario() {
        setTitle("Painel do Usuário");
        setSize(820, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.BG);

        Usuario u = SessionContext.getCurrentUser();
        if (u == null) {
            JOptionPane.showMessageDialog(this, "Usuário não logado.");
            dispose();
            new LoginFrame().setVisible(true);
            return;
        }

        add(UITheme.headerBar("Bem-vindo, " + u.getNome(), "Área do usuário — visualize seus cartões e transações"),
                BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);
        lista.setFont(UITheme.FONT_BASE);
        lista.setBackground(UITheme.CARD_BG);
        lista.setBorder(new EmptyBorder(10, 12, 10, 12));

        JScrollPane scroll = new JScrollPane(lista);
        scroll.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        scroll.getViewport().setBackground(UITheme.CARD_BG);
        add(scroll, BorderLayout.CENTER);

        model.addElement("=== Meus Cartões ===");
        List<Cartao> cartoes = cartaoService.findByUsuario(u);
        for (Cartao c : cartoes) {
            model.addElement("Cartão: " + c.getNumero() + " | Saldo: " + c.getSaldo() + " | Tipo: " + c.getTipo());
        }

        JPanel botoes = UITheme.footerBar();
        JButton btnCartoes    = UITheme.primaryButton("💳  Ver Cartões");
        JButton btnPlanos     = UITheme.primaryButton("📋  Ver Planos");
        JButton btnTransacoes = UITheme.primaryButton("💰  Ver Transações");
        JButton btnSair       = UITheme.dangerButton("↩  Sair");

        btnCartoes.addActionListener(e -> new CartaoListaFrame().setVisible(true));
        btnPlanos.addActionListener(e -> new PlanoListaFrame().setVisible(true));
        btnTransacoes.addActionListener(e -> new TransacaoListaFrame().setVisible(true));
        btnSair.addActionListener(e -> {
            SessionContext.logout();
            dispose();
            new LoginFrame().setVisible(true);
        });

        botoes.add(btnCartoes);
        botoes.add(btnPlanos);
        botoes.add(btnTransacoes);
        botoes.add(btnSair);
        add(botoes, BorderLayout.SOUTH);
    }
}
