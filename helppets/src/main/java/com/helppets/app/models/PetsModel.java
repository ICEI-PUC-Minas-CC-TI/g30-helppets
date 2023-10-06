package com.helppets.app.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PetsModel {
    private Integer petsId;
    private String nome;
    private String raca;
    private byte[] foto;
    private Integer usuario_usuarioId;
}