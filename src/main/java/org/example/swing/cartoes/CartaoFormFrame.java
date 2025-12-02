package org.example.swing.cartoes;

import org.example.model.Cartao;
import org.example.model.Plano;
import org.example.model.Usuario;
import org.example.service.CartaoService;
import org.example.service.PlanoService;
import org.example.service.UsuarioService;

import javax.swing.*;
import java.awt.*;

public class CartaoFormFrame extends JFrame {

    private Cartao cartao;
    private CartaoListaFrame parent;
    private CartaoService service = new CartaoService();
    private UsuarioService usuarioService = new UsuarioService();
    private PlanoService planoService = new PlanoService();

    public CartaoFormFrame(Cartao c, CartaoListaFrame parent) {

        this.cartao = c;
        this.parent = parent;

        setTitle(c == null ? "Novo Cartão" : "Editar Cartão");
        setSize(400,300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6,2));

        JTextField numero = new JTextField();
        JTextField validade = new JTextField();

        JComboBox<Usuario> usuarioBox = new JComboBox<>();
        JComboBox<Plano> planoBox = new JComboBox<>();

        for (Usuario u : usuarioService.listar()) usuarioBox.addItem(u);
        for (Plano p : planoService.listar()) planoBox.addItem(p);

        JButton salvar = new JButton("Salvar");

        add(new JLabel("Número:"));
        add(numero);
        add(new JLabel("Validade:"));
        add(validade);
        add(new JLabel("Usuário:"));
        add(usuarioBox);
        add(new JLabel("Plano:"));
        add(planoBox);
        add(new JLabel());
        add(salvar);

        if (c != null) {
            numero.setText(c.getNumero());
            validade.setText(c.getValidade());
            usuarioBox.setSelectedItem(c.getUsuario());
            planoBox.setSelectedItem(c.getPlano());
        }

        salvar.addActionListener(e -> {

            if (cartao == null) cartao = new Cartao();

            cartao.setNumero(numero.getText());
            cartao.setValidade(validade.getText());
            cartao.setUsuario((Usuario) usuarioBox.getSelectedItem());
            cartao.setPlano((Plano) planoBox.getSelectedItem());

            if (cartao.getId() == null) service.salvar(cartao);
            else service.atualizar(cartao);

            parent.carregar();
            dispose();
        });
    }
}

