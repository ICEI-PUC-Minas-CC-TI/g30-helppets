package com.helppets.app.models;

import lombok.Data;

@Data
public class PetsModel {
    private Integer petsId;
    private String nome;
    private String raca;
    private byte[] foto;
    private Integer usuario_usuarioId;
}