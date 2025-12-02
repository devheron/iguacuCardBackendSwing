package org.example.swing;

import org.example.model.Usuario;
import org.example.model.Cartao;
import org.example.service.CartaoService;
import org.example.session.SessionContext;
import org.example.swing.planos.PlanoListaFrame;
import org.example.swing.cartoes.CartaoListaFrame;
import org.example.swing.transacoes.TransacaoListaFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PainelUsuario extends JFrame {

    private final CartaoService cartaoService = new CartaoService();

    public PainelUsuario() {
        setTitle("Painel do Usuário");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Usuario u = SessionContext.getCurrentUser();
        if (u == null) {
            JOptionPane.showMessageDialog(this, "Usuário não logado.");
            dispose();
            new LoginFrame().setVisible(true);
            return;
        }

        JLabel info = new JLabel("Bem-vindo, " + u.getNome(), SwingConstants.CENTER);
        add(info, BorderLayout.NORTH);

        DefaultListModel<String> model = new DefaultListModel<>();
        JList<String> lista = new JList<>(model);
        add(new JScrollPane(lista), BorderLayout.CENTER);

        model.addElement("=== Meus Cartões ===");
        List<Cartao> cartoes = cartaoService.findByUsuario(u);
        for (Cartao c : cartoes) {
            model.addElement("Cartão: " + c.getNumero() + " | Saldo: " + c.getSaldo() + " | Tipo: " + c.getTipo());
        }

        JPanel botoes = new JPanel(new GridLayout(1, 4));
        JButton btnCartoes = new JButton("Ver Cartões");
        JButton btnPlanos = new JButton("Ver Planos");
        JButton btnTransacoes = new JButton("Ver Transações");
        JButton btnSair = new JButton("Sair");

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