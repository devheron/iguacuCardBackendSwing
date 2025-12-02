package org.example.dto;

import org.example.model.enums.Role;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String login;
    private Role role;

    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nome, String login, Role role) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.role = role;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}