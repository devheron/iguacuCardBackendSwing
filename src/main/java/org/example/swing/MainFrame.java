package org.example.swing;

import org.example.security.Sessao;

import javax.swing.*;

public abstract class MainFrame extends JFrame {

    public MainFrame(String titulo) {
        setTitle("IguaçuCard - " + titulo);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        configurarMenu();


    }

    private void configurarMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu contaMenu = new JMenu("Conta");
        JMenuItem logoutItem = new JMenuItem("Sair");

        logoutItem.addActionListener(e -> {
            Sessao.logout();
            new LoginFrame().setVisible(true);
            dispose();
        });

        contaMenu.add(logoutItem);
        menuBar.add(contaMenu);

        setJMenuBar(menuBar);
    }
}
