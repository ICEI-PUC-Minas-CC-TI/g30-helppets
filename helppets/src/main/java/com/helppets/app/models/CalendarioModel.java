package com.helppets.app.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalendarioModel {
    Integer eventoId;
    String descricao;
    Date data;
}
