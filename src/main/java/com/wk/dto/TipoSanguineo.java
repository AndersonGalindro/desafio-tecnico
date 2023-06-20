package com.wk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TipoSanguineo {

	@JsonProperty("A_Positivo")
	int APositivo;
	@JsonProperty("A_Negativo")
	int ANegativo;
	@JsonProperty("B_Positivo")
	int BPositivo;
	@JsonProperty("B_Negativo")
	int BNegativo;
	@JsonProperty("AB_Positivo")
	int ABPositivo;
	@JsonProperty("AB_Negativo")
	int ABNegativo;
	@JsonProperty("O_Positivo")
	int OPositivo;
	@JsonProperty("O_Negativo")
	int ONegativo;

}
