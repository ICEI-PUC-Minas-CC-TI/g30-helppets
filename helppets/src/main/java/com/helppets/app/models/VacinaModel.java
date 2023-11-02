package com.helppets.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.sql.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VacinaModel {
    @JsonIgnore
    private Integer vacinaId;
    private String nome;
    private Date data;
    private String descricao;
    private Boolean tomou;
    @JsonIgnore
    private Integer pets_petsId;
}