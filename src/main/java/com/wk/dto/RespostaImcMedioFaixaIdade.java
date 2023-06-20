package com.wk.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RespostaImcMedioFaixaIdade {

	@JsonProperty("Imc_Medio_0_A_10")
	Double zeroADez;
	@JsonProperty("Imc_Medio_11_A_20")
	Double onzeAVinte;
	@JsonProperty("Imc_Medio_21_A_30")
	Double vinteEUmATrinta;
	@JsonProperty("Imc_Medio_31_A_40")
	Double trintaEUmAQuarenta;
	@JsonProperty("Imc_Medio_41_A_50")
	Double quarentaEUmACinquenta;
	@JsonProperty("Imc_Medio_51_A_60")
	Double CinquentaEUmASessenta;
	@JsonProperty("Imc_Medio_61_A_70")
	Double SessentaEUmASetenta;
	@JsonProperty("Imc_Medio_71_A_80")
	Double SetentaEUmAOitenta;
	@JsonProperty("Imc_Medio_81_A_90")
	Double OitentaEUmANoventa;
	@JsonProperty("Imc_Medio_91_A_100")
	Double NoventaEUmACem;

}
