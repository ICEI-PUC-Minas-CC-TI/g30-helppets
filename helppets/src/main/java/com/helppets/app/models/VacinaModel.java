package com.helppets.app.models;

import lombok.Data;

import java.sql.Date;

@Data
public class VacinaModel {
    private Integer vacinaId;
    private String nome;
    private Date data;
    private String descricao;
    private Boolean tomou;
    private Integer pets_petsId;
}