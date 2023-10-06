package com.helppets.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsuarioModel {
    private Integer usuarioId;
    private String nome;
    private String email;
    @JsonIgnore
    private byte[] senha; // it's bcrypt
}