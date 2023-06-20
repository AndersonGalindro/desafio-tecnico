package com.wk.service;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wk.dto.FaixaIdade;
import com.wk.dto.ImcMedioFaixaIdade;
import com.wk.dto.PercentualObesidade;
import com.wk.dto.RespostaImcMedioFaixaIdade;
import com.wk.dto.TipoSanguineo;
import com.wk.entity.Pessoa;
import com.wk.repository.PessoaRepository;

@SuppressWarnings({ "rawtypes", "unchecked", "removal" })
@Service
public class PessoaService {

	PercentualObesidade percentualObesidade = new PercentualObesidade();
	TipoSanguineo tipoSanguineo = new TipoSanguineo();
	FaixaIdade faixaIdade = new FaixaIdade();
	ImcMedioFaixaIdade imcMedioFaixaIdade = new ImcMedioFaixaIdade();
	RespostaImcMedioFaixaIdade respostaImcMedioFaixaIdade = new RespostaImcMedioFaixaIdade();

	List Masculino = new ArrayList<>();
	List Feminino = new ArrayList<>();

	int mediaImcFemininoObesa = 0;
	int mediaImcFemininoNaoObesa = 0;
	int mediaImcMasculinoObeso = 0;
	int mediaImcMasculinoNaoObeso = 0;
	int quantidadeDoadorAPositivo = 0;
	int somaIdadeDoadorAPositivo = 0;
	int quantidadeDoadorBPositivo = 0;
	int somaIdadeDoadorBPositivo = 0;
	int quantidadeDoadorABPositivo = 0;
	int somaIdadeDoadorABPositivo = 0;
	int quantidadeDoadorOPositivo = 0;
	int somaIdadeDoadorOPositivo = 0;
	int quantidadeDoadorANegativo = 0;
	int somaIdadeDoadorANegativo = 0;
	int quantidadeDoadorBNegativo = 0;
	int somaIdadeDoadorBNegativo = 0;
	int quantidadeDoadorABNegativo = 0;
	int somaIdadeDoadorABNegativo = 0;
	int quantidadeDoadorONegativo = 0;
	int somaIdadeDoadorONegativo = 0;

	@Autowired
	PessoaRepository pessoaRepository;

	public ResponseEntity<?> save(List<Pessoa> pessoa) {

		try {
			for (int i = 0; i < pessoa.size(); i++) {
				pessoaRepository.save(pessoa.get(i));
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(pessoaRepository.findAll(), HttpStatus.OK);
	}

	public ResponseEntity<?> quantidadePessoaEstado() {
		return new ResponseEntity<>(pessoaRepository.findEstadoCountGroupEstado(), HttpStatus.OK);
	}

	public ResponseEntity<?> percentualObesidade() {

		var ListaObjetoPessoa = pessoaRepository.fingNomeAndSexoAndImc();

		for (int i = 0; i < ListaObjetoPessoa.size();) {

			var ListPesso = List.of(ListaObjetoPessoa.get(i));

			if (ListPesso.get(1).equals("Feminino")) {

				listaPessoaFeminina(ListPesso.get(1), ListPesso.get(2), ListaObjetoPessoa.get(i));

			} else {

				listaPessoaMasculino(ListPesso.get(1), ListPesso.get(2), ListaObjetoPessoa.get(i));

			}
			i++;
		}

		percentualObesidade.setPercentual_obesidade_feminino(
				(mediaImcFemininoObesa * 100) / (mediaImcFemininoNaoObesa + mediaImcFemininoObesa));
		percentualObesidade.setPercentual_obesidade_masculino(
				(mediaImcMasculinoObeso * 100) / (mediaImcMasculinoNaoObeso + mediaImcMasculinoObeso));
		return new ResponseEntity<>(percentualObesidade, HttpStatus.OK);
	}

	public ResponseEntity<?> quantitadeDoadorTipoSanguineo() {

		quantidadeDoadorAPositivo = 0;
		quantidadeDoadorBPositivo = 0;
		quantidadeDoadorABPositivo = 0;
		quantidadeDoadorOPositivo = 0;
		quantidadeDoadorANegativo = 0;
		quantidadeDoadorBNegativo = 0;
		quantidadeDoadorABNegativo = 0;
		quantidadeDoadorONegativo = 0;

		var FindTipoSanguineoAndDataNasc = pessoaRepository.findTipoSanguineoAndPesoAndDataNasc();

		FindTipoSanguineoAndDataNasc.stream().forEach(pessoa -> {
			if (toStringFromInteger(List.of(pessoa).get(1).toString()) > 50
					&& (calculoIdade(List.of(pessoa).get(2).toString()) >= 16
							&& calculoIdade(List.of(pessoa).get(2).toString()) <= 69)) {

				if (List.of(pessoa).get(0).equals("A+")) {

					quantidadeDoadorAPositivo++;

				}
				if (List.of(pessoa).get(0).equals("A-")) {

					quantidadeDoadorANegativo++;

				}
				if (List.of(pessoa).get(0).equals("AB+")) {

					quantidadeDoadorABPositivo++;

				}
				if (List.of(pessoa).get(0).equals("AB-")) {

					quantidadeDoadorABNegativo++;

				}
				if (List.of(pessoa).get(0).equals("B+")) {

					quantidadeDoadorBPositivo++;

				}
				if (List.of(pessoa).get(0).equals("B-")) {

					quantidadeDoadorBNegativo++;

				}
				if (List.of(pessoa).get(0).equals("O+")) {

					quantidadeDoadorOPositivo++;

				}
				if (List.of(pessoa).get(0).equals("O-")) {

					quantidadeDoadorONegativo++;

				}
			}
		});

		tipoSanguineo.setAPositivo(quantidadeDoadorAPositivo + quantidadeDoadorANegativo + quantidadeDoadorOPositivo
				+ quantidadeDoadorONegativo);

		tipoSanguineo.setANegativo(quantidadeDoadorANegativo + quantidadeDoadorONegativo);

		tipoSanguineo.setBPositivo(quantidadeDoadorABPositivo + quantidadeDoadorBNegativo + quantidadeDoadorOPositivo
				+ quantidadeDoadorONegativo);

		tipoSanguineo.setBNegativo(quantidadeDoadorBNegativo + quantidadeDoadorONegativo);

		tipoSanguineo.setABPositivo(quantidadeDoadorABNegativo + quantidadeDoadorABPositivo + quantidadeDoadorANegativo
				+ quantidadeDoadorAPositivo + quantidadeDoadorBNegativo + quantidadeDoadorBPositivo
				+ quantidadeDoadorONegativo + quantidadeDoadorOPositivo);

		tipoSanguineo.setABNegativo(quantidadeDoadorANegativo + quantidadeDoadorBNegativo + quantidadeDoadorONegativo
				+ quantidadeDoadorABNegativo);

		tipoSanguineo.setOPositivo(quantidadeDoadorOPositivo + quantidadeDoadorONegativo);

		tipoSanguineo.setONegativo(quantidadeDoadorONegativo);

		return new ResponseEntity<>(tipoSanguineo, HttpStatus.OK);

	}

	public ResponseEntity<?> mediaIdadeTipoSanguineo() {
		var FindTipoSanguineoAndDataNasc = pessoaRepository.findTipoSanguineoAndDataNasc();

		FindTipoSanguineoAndDataNasc.stream().forEach(pessoa -> {
			if (List.of(pessoa).get(0).equals("A+")) {

				somaIdadeDoadorAPositivo += calculoIdade(List.of(pessoa).get(1).toString());
				quantidadeDoadorAPositivo++;

			}
			if (List.of(pessoa).get(0).equals("A-")) {

				somaIdadeDoadorANegativo += calculoIdade(List.of(pessoa).get(1).toString());
				quantidadeDoadorANegativo++;

			}
			if (List.of(pessoa).get(0).equals("AB+")) {

				somaIdadeDoadorABPositivo += calculoIdade(List.of(pessoa).get(1).toString());
				quantidadeDoadorABPositivo++;

			}
			if (List.of(pessoa).get(0).equals("AB-")) {

				somaIdadeDoadorABNegativo += calculoIdade(List.of(pessoa).get(1).toString());
				quantidadeDoadorABNegativo++;

			}
			if (List.of(pessoa).get(0).equals("B+")) {

				somaIdadeDoadorBPositivo += calculoIdade(List.of(pessoa).get(1).toString());
				quantidadeDoadorBPositivo++;

			}
			if (List.of(pessoa).get(0).equals("B-")) {

				somaIdadeDoadorBNegativo += calculoIdade(List.of(pessoa).get(1).toString());
				quantidadeDoadorBNegativo++;

			}
			if (List.of(pessoa).get(0).equals("O+")) {

				somaIdadeDoadorOPositivo += calculoIdade(List.of(pessoa).get(1).toString());
				quantidadeDoadorOPositivo++;

			}
			if (List.of(pessoa).get(0).equals("O-")) {

				somaIdadeDoadorONegativo += calculoIdade(List.of(pessoa).get(1).toString());
				quantidadeDoadorONegativo++;

			}
		});

		mediaTotal(quantidadeDoadorAPositivo, somaIdadeDoadorAPositivo, quantidadeDoadorBPositivo,
				somaIdadeDoadorBPositivo, quantidadeDoadorABPositivo, somaIdadeDoadorABPositivo,
				quantidadeDoadorOPositivo, somaIdadeDoadorOPositivo, quantidadeDoadorANegativo,
				somaIdadeDoadorANegativo, quantidadeDoadorBNegativo, somaIdadeDoadorBNegativo,
				quantidadeDoadorABNegativo, somaIdadeDoadorABNegativo, quantidadeDoadorONegativo,
				somaIdadeDoadorONegativo);

		return new ResponseEntity<>(tipoSanguineo, HttpStatus.OK);

	}

	public ResponseEntity<?> imaMediaFaixaIdade() {

		List<Object[]> listDataNascAndImc = pessoaRepository.fingDataNascAndImc();

		listDataNascAndImc.stream().forEach(p -> {
			if (calculoIdade(List.of(p).get(0).toString()) > 0 && calculoIdade(List.of(p).get(0).toString()) <= 10) {

				faixaIdade.setZeroADez(faixaIdade.getZeroADez() + 1);
				imcMedioFaixaIdade.setZeroADez(
						imcMedioFaixaIdade.getZeroADez() + toStringFromDouble(List.of(p).get(1).toString()));

			}
			if (calculoIdade(List.of(p).get(0).toString()) >= 11 && calculoIdade(List.of(p).get(0).toString()) <= 20) {

				faixaIdade.setOnzeAVinte(faixaIdade.getOnzeAVinte() + 1);
				imcMedioFaixaIdade.setOnzeAVinte(
						imcMedioFaixaIdade.getOnzeAVinte() + toStringFromDouble(List.of(p).get(1).toString()));

			}
			if (calculoIdade(List.of(p).get(0).toString()) >= 21 && calculoIdade(List.of(p).get(0).toString()) <= 30) {

				faixaIdade.setVinteEUmATrinta(faixaIdade.getVinteEUmATrinta() + 1);
				imcMedioFaixaIdade.setVinteEUmATrinta(
						imcMedioFaixaIdade.getVinteEUmATrinta() + toStringFromDouble(List.of(p).get(1).toString()));

			}
			if (calculoIdade(List.of(p).get(0).toString()) >= 31 && calculoIdade(List.of(p).get(0).toString()) <= 40) {

				faixaIdade.setTrintaEUmAQuarenta(faixaIdade.getTrintaEUmAQuarenta() + 1);
				imcMedioFaixaIdade.setTrintaEUmAQuarenta(
						imcMedioFaixaIdade.getTrintaEUmAQuarenta() + toStringFromDouble(List.of(p).get(1).toString()));

			}
			if (calculoIdade(List.of(p).get(0).toString()) >= 41 && calculoIdade(List.of(p).get(0).toString()) <= 50) {

				faixaIdade.setQuarentaEUmACinquenta(faixaIdade.getQuarentaEUmACinquenta() + 1);
				imcMedioFaixaIdade.setQuarentaEUmACinquenta(imcMedioFaixaIdade.getQuarentaEUmACinquenta()
						+ toStringFromDouble(List.of(p).get(1).toString()));

			}
			if (calculoIdade(List.of(p).get(0).toString()) >= 51 && calculoIdade(List.of(p).get(0).toString()) <= 60) {

				faixaIdade.setCinquentaEUmASessenta(faixaIdade.getCinquentaEUmASessenta() + 1);
				imcMedioFaixaIdade.setCinquentaEUmASessenta(imcMedioFaixaIdade.getCinquentaEUmASessenta()
						+ toStringFromDouble(List.of(p).get(1).toString()));

			}
			if (calculoIdade(List.of(p).get(0).toString()) >= 61 && calculoIdade(List.of(p).get(0).toString()) <= 70) {

				faixaIdade.setSessentaEUmASetenta(faixaIdade.getSessentaEUmASetenta() + 1);
				imcMedioFaixaIdade.setSessentaEUmASetenta(
						imcMedioFaixaIdade.getSessentaEUmASetenta() + toStringFromDouble(List.of(p).get(1).toString()));

			}
			if (calculoIdade(List.of(p).get(0).toString()) >= 71 && calculoIdade(List.of(p).get(0).toString()) <= 80) {

				faixaIdade.setSetentaEUmAOitenta(faixaIdade.getSetentaEUmAOitenta() + 1);
				imcMedioFaixaIdade.setSetentaEUmAOitenta(
						imcMedioFaixaIdade.getSetentaEUmAOitenta() + toStringFromDouble(List.of(p).get(1).toString()));

			}
			if (calculoIdade(List.of(p).get(0).toString()) >= 81 && calculoIdade(List.of(p).get(0).toString()) <= 90) {

				faixaIdade.setOitentaEUmANoventa(faixaIdade.getOitentaEUmANoventa() + 1);
				imcMedioFaixaIdade.setOitentaEUmANoventa(
						imcMedioFaixaIdade.getOitentaEUmANoventa() + toStringFromDouble(List.of(p).get(1).toString()));

			}
			if (calculoIdade(List.of(p).get(0).toString()) >= 91 && calculoIdade(List.of(p).get(0).toString()) <= 100) {

				faixaIdade.setNoventaEUmACem(faixaIdade.getNoventaEUmACem() + 1);
				imcMedioFaixaIdade.setNoventaEUmACem(
						imcMedioFaixaIdade.getNoventaEUmACem() + toStringFromDouble(List.of(p).get(1).toString()));

			}
		});

		respostaImcMedioFaixaIdade.setZeroADez(imcMedioFaixaIdade.getZeroADez() / faixaIdade.getZeroADez());
		respostaImcMedioFaixaIdade.setOnzeAVinte(imcMedioFaixaIdade.getOnzeAVinte() / faixaIdade.getOnzeAVinte());
		respostaImcMedioFaixaIdade
				.setVinteEUmATrinta(imcMedioFaixaIdade.getVinteEUmATrinta() / faixaIdade.getVinteEUmATrinta());
		respostaImcMedioFaixaIdade
				.setTrintaEUmAQuarenta(imcMedioFaixaIdade.getTrintaEUmAQuarenta() / faixaIdade.getTrintaEUmAQuarenta());
		respostaImcMedioFaixaIdade.setQuarentaEUmACinquenta(
				imcMedioFaixaIdade.getQuarentaEUmACinquenta() / faixaIdade.getQuarentaEUmACinquenta());
		respostaImcMedioFaixaIdade.setCinquentaEUmASessenta(
				imcMedioFaixaIdade.getCinquentaEUmASessenta() / faixaIdade.getCinquentaEUmASessenta());
		respostaImcMedioFaixaIdade.setSessentaEUmASetenta(
				imcMedioFaixaIdade.getSessentaEUmASetenta() / faixaIdade.getSessentaEUmASetenta());
		respostaImcMedioFaixaIdade
				.setSetentaEUmAOitenta(imcMedioFaixaIdade.getSetentaEUmAOitenta() / faixaIdade.getSetentaEUmAOitenta());
		respostaImcMedioFaixaIdade
				.setOitentaEUmANoventa(imcMedioFaixaIdade.getOitentaEUmANoventa() / faixaIdade.getOitentaEUmANoventa());
		respostaImcMedioFaixaIdade
				.setNoventaEUmACem(imcMedioFaixaIdade.getNoventaEUmACem() / faixaIdade.getNoventaEUmACem());

		return new ResponseEntity<>(respostaImcMedioFaixaIdade, HttpStatus.OK);

	}

	private Double toStringFromDouble(String string) {
		return new Double(string);
	}

	private Integer toStringFromInteger(String string) {
		return new Integer(string);
	}

	private void listaPessoaMasculino(Object sexo, Object imc, Object[] ObjectPessoa) {

		if (sexo.equals("Masculino")) {
			Masculino.add(ObjectPessoa);

			if (toStringFromDouble(imc.toString()) > 30) {

				mediaImcMasculinoObeso++;

			} else {

				mediaImcMasculinoNaoObeso++;

			}
		}
	}

	private void listaPessoaFeminina(Object sexo, Object imc, Object[] ObjectPessoa) {

		if (sexo.equals("Feminino")) {
			Feminino.add(ObjectPessoa);

			if (toStringFromDouble(imc.toString()) > 30) {

				mediaImcFemininoObesa++;

			} else {

				mediaImcFemininoNaoObesa++;

			}
		}
	}

	private void mediaTotal(int quantidadeDoadorAPositivo2, int somaIdadeDoadorAPositivo2,
			int quantidadeDoadorBPositivo2, int somaIdadeDoadorBPositivo2, int quantidadeDoadorABPositivo2,
			int somaIdadeDoadorABPositivo2, int quantidadeDoadorOPositivo2, int somaIdadeDoadorOPositivo2,
			int quantidadeDoadorANegativo2, int somaIdadeDoadorANegativo2, int quantidadeDoadorBNegativo2,
			int somaIdadeDoadorBNegativo2, int quantidadeDoadorABNegativo2, int somaIdadeDoadorABNegativo2,
			int quantidadeDoadorONegativo2, int somaIdadeDoadorONegativo2) {

		tipoSanguineo.setAPositivo(somaIdadeDoadorAPositivo2 / quantidadeDoadorAPositivo2);
		tipoSanguineo.setANegativo(somaIdadeDoadorANegativo2 / quantidadeDoadorANegativo2);
		tipoSanguineo.setBPositivo(somaIdadeDoadorBPositivo2 / quantidadeDoadorBPositivo2);
		tipoSanguineo.setBNegativo(somaIdadeDoadorBNegativo2 / quantidadeDoadorBNegativo2);
		tipoSanguineo.setABNegativo(somaIdadeDoadorABNegativo2 / quantidadeDoadorABNegativo2);
		tipoSanguineo.setABPositivo(somaIdadeDoadorABPositivo2 / quantidadeDoadorABPositivo2);
		tipoSanguineo.setONegativo(somaIdadeDoadorONegativo2 / quantidadeDoadorONegativo2);
		tipoSanguineo.setOPositivo(somaIdadeDoadorOPositivo2 / quantidadeDoadorOPositivo2);

	}

	private int calculoIdade(String date) {
		return new Period(new LocalDate(date), LocalDate.now()).getYears();
	}

}
