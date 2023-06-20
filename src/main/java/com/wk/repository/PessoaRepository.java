package com.wk.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wk.entity.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {

	@Query(value = "SELECT ESTADO, COUNT(*) AS QUANTIDADE_PESSOA FROM PESSOA GROUP BY ESTADO", nativeQuery = true)
	public List<Object[]> findEstadoCountGroupEstado();

	@Query(value = "SELECT NOME, SEXO, (PESO/ (ALTURA*ALTURA)) AS IMC FROM PESSOA GROUP BY CPF", nativeQuery = true)
	public List<Object[]> fingNomeAndSexoAndImc();

	@Query(value = "SELECT DATA_NASC, (PESO/ (ALTURA*ALTURA)) AS IMC FROM PESSOA GROUP BY CPF", nativeQuery = true)
	public List<Object[]> fingDataNascAndImc();

	@Query(value = "SELECT TIPO_SANGUINEO, DATA_NASC FROM PESSOA", nativeQuery = true)
	public List<Object[]> findTipoSanguineoAndDataNasc();

	@Query(value = "SELECT TIPO_SANGUINEO, PESO, DATA_NASC FROM PESSOA", nativeQuery = true)
	public List<Object[]> findTipoSanguineoAndPesoAndDataNasc();

}
