package com.wk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wk.entity.Pessoa;
import com.wk.service.PessoaService;

@RestController
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	PessoaService pessoaService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody List<Pessoa> pessoa) {
		return pessoaService.save(pessoa);
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		return pessoaService.findAll();
	}

	@GetMapping("/quantidade_pessoa_estado")
	public ResponseEntity<?> quantidadePessoaEstado() {
		return pessoaService.quantidadePessoaEstado();
	}

	@GetMapping("/percentual_obesidade")
	public ResponseEntity<?> percentualObesidade() {
		return pessoaService.percentualObesidade();
	}

	@GetMapping("/media_idade_tipo_sanguineo")
	public ResponseEntity<?> mediaIdadeTipoSanguineo() {
		return pessoaService.mediaIdadeTipoSanguineo();
	}

	@GetMapping("/quantidade_doador_tipo_sanguineo")
	public ResponseEntity<?> quantitadeDoadorTipoSanguineo() {
		return pessoaService.quantitadeDoadorTipoSanguineo();
	}

	@GetMapping("/imaMediaFaixaIdade")
	public ResponseEntity<?> imaMediaFaixaIdade() {
		return pessoaService.imaMediaFaixaIdade();
	}

}
