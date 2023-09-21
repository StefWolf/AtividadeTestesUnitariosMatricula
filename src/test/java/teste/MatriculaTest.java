package teste;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MatriculaTest {

	@ParameterizedTest
	@CsvSource({
			"10, 10, 10, 100",
			"10, 7, 5, 75"
	})
	@DisplayName("O aluno aprovado quando média, notas e frequência estão acima do limite")
	void alunosComFrequenciaAcimaENotaAcimaDevemSerAprovado(BigDecimal nota1, BigDecimal nota2, BigDecimal nota3,
			Integer frequencia)
			throws NullPointerException {
		Matricula m = new Matricula();

		m.registrarNota1(nota1);
		m.registrarNota2(nota2);
		m.registrarNota3(nota3);
		m.registrarFrequencia(frequencia);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.APR, m.status());
	}

	@ParameterizedTest
	@CsvSource({
			"10, 10, 2, 75",
			"2, 10, 3, 100"
	})
	@DisplayName("Quando o aluno passa por média mas possui uma unidade abaixo de 3")
	void alunosComMenosDeTresEmAlgumaUnidadeEFrequenciaNiveladaDevemFazerRecuperacao(BigDecimal nota1, BigDecimal nota2,
			BigDecimal nota3, Integer frequencia) throws NullPointerException {
		Matricula m = new Matricula();

		m.registrarNota1(nota1);
		m.registrarNota2(nota2);
		m.registrarNota3(nota3);
		m.registrarFrequencia(frequencia);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REC, m.status());
	}

	@Test
	@DisplayName("Quando há valores inválidos/não processados de alguma unidade ao fazer a consolidação")
	void valoresInvalidosAntesDaConsolidacaoDevemSurgirExcessao() throws NullPointerException {
		Matricula m = new Matricula();

		m.registrarNota1(BigDecimal.valueOf(6.0));
		m.registrarNota2(BigDecimal.valueOf(8.0));

		assertThrows(NullPointerException.class, () -> m.consolidarParcialmente());
	}

	@ParameterizedTest
	@CsvSource({
			"3.4, 2, 1, 100",
			"1.5, 3, 3, 75"
	})
	@DisplayName("Quando aluno está abaixo de 3 na média com frequencia acima do limite")
	void alunosComNotaAbaixoDeTresEFrequenciaNiveladaDevemFicarDeRecuperacao(BigDecimal nota1, BigDecimal nota2,
			BigDecimal nota3, Integer frequencia) {
		Matricula m = new Matricula();

		m.registrarNota1(nota1);
		m.registrarNota2(nota2);
		m.registrarNota3(nota3);
		m.registrarFrequencia(frequencia);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REP, m.status());
	}

	@ParameterizedTest
	@CsvSource({
			"3.4, 7, 5, 74",
			"3, 8, 8, 0"
	})
	@DisplayName("Quando o aluno está acima ou igual à média mas com frequência abaixo do limite")
	void alunosAbaixoDaFrequenciaEMediaAcimaDevemSerReprovadosPorFalta(BigDecimal nota1, BigDecimal nota2,
			BigDecimal nota3, Integer frequencia) {
		Matricula m = new Matricula();

		m.registrarNota1(nota1);
		m.registrarNota2(nota2);
		m.registrarNota3(nota3);
		m.registrarFrequencia(frequencia);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REPF, m.status());
	}

	@ParameterizedTest
	@CsvSource({
			"3.4, 7, 4, 74",
			"3, 8, 1, 0"
	})
	@DisplayName("Quando o aluno está abaixo da média e com frequência abaixo do limite")
	void alunosComFrequenciaEMediaBaixaDeveReprovarPorMediaEFalta(BigDecimal nota1, BigDecimal nota2,
			BigDecimal nota3, Integer frequencia) {
		Matricula m = new Matricula();

		m.registrarNota1(nota1);
		m.registrarNota2(nota2);
		m.registrarNota3(nota3);
		m.registrarFrequencia(frequencia);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REMF, m.status());
	}

	@ParameterizedTest
	@CsvSource({
			"5, 5, 6, 75",
			"3, 8, 7, 100"
	})
	@DisplayName("Quando o aluno está acima da media parcial e abaixo da media")
	void alunosComMediaParcialEFrequenciaEstavelDevemSerAprovadoPorNota(BigDecimal nota1, BigDecimal nota2,
			BigDecimal nota3, Integer frequencia) {
		Matricula m = new Matricula();

		m.registrarNota1(nota1);
		m.registrarNota2(nota2);
		m.registrarNota3(nota3);
		m.registrarFrequencia(frequencia);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.APRN, m.status());
	}

	@ParameterizedTest
	@CsvSource({
			"5, 5, 6, 74",
			"5, 5, 5, 5"
	})
	@DisplayName("Quando o aluno está acima da media parcial e abaixo da media e abaixo do limite de frequencia")
	void alunosComMediaParcialEFrequenciaBaixaDevemSerReprovadoPorFalta(BigDecimal nota1, BigDecimal nota2,
			BigDecimal nota3, Integer frequencia) {
		Matricula m = new Matricula();

		m.registrarNota1(nota1);
		m.registrarNota2(nota2);
		m.registrarNota3(nota3);
		m.registrarFrequencia(frequencia);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REPF, m.status());
	}
}
