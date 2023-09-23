package com.helppets.app.models;

import lombok.Data;

@Data
public class UsuarioModel {
    private Integer usuarioId;
    private String nome;
    private String email;
    private byte[] senha; // it's bcrypt
}