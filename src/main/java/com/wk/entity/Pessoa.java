package com.wk.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	String nome;
	String cpf;
	String rg;
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonProperty("data_nasc")
	LocalDate dataNasc;
	String sexo;
	String mae;
	String pai;
	String email;
	String cep;
	String endereco;
	int numero;
	String bairro;
	String cidade;
	String estado;
	@JsonProperty("telefone_fixo")
	String telefoneFixo;
	String celular;
	Double altura;
// Acredito que aqui seria melhor Double, entretando, o requisito pede um inteiro e não um flutuante
	int peso;
	@JsonProperty("tipo_sanguineo")
	String tipoSanguineo;

}
