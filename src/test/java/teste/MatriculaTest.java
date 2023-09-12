package teste;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MatriculaTest {

	@Test
	@DisplayName("O aluno aprovado quando média, notas e frequência estão acima do limite")
	void testQuandoAprovado() throws NullPointerException {
		Matricula m = new Matricula();

		m.registrarNota1(BigDecimal.valueOf(10l));
		m.registrarNota2(BigDecimal.valueOf(10l));
		m.registrarNota3(BigDecimal.valueOf(10l));
		m.registrarFrequencia(100);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.APR, m.status());
	}

	@Test
	@DisplayName("Quando o aluno passa por média mas possui uma unidade abaixo de 3")
	void testQuandoPassaComMenosDeTresEmAlgumaUnidade() throws NullPointerException {
		Matricula m = new Matricula();

		m.registrarNota1(BigDecimal.valueOf(10.0));
		m.registrarNota2(BigDecimal.valueOf(10.0));
		m.registrarNota3(BigDecimal.valueOf(2.0));
		m.registrarFrequencia(75);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REC, m.status());
	}

	@Test
	@DisplayName("Quando há valores inválidos/não processados de alguma unidade ao fazer a consolidação")
	void testeValoresInvalidosAntesDaConsolidacao() throws NullPointerException {
		Matricula m = new Matricula();

		m.registrarNota1(BigDecimal.valueOf(6.0));
		m.registrarNota2(BigDecimal.valueOf(8.0));

		assertThrows(NullPointerException.class, () -> m.consolidarParcialmente());
	}

	@Test
	@DisplayName("Quando aluno está abaixo de 3 na média com frequencia acima do limite")
	void testeAlunoReprovadoPorNotaAbaixoDeTresSomente() {
		Matricula m = new Matricula();

		m.registrarNota1(BigDecimal.valueOf(3.4));
		m.registrarNota2(BigDecimal.valueOf(2.0));
		m.registrarNota3(BigDecimal.valueOf(1.0));
		m.registrarFrequencia(100);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REP, m.status());
	}

	@Test
	@DisplayName("Quando o aluno está acima ou igual à média mas com frequência abaixo do limite")
	void testeAlunoAbaixoDaFrequenciaEMediaAcima() {
		Matricula m = new Matricula();

		m.registrarNota1(BigDecimal.valueOf(5.0));
		m.registrarNota2(BigDecimal.valueOf(10.0));
		m.registrarNota3(BigDecimal.valueOf(6.0));
		m.registrarFrequencia(60);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REPF, m.status());
	}

	@Test
	@DisplayName("Quando o aluno está abaixo da média mas com frequência abaixo do limite")
	void testeAlunosComFrequenciaEMediaBaixa() {
		Matricula m = new Matricula();

		m.registrarNota1(BigDecimal.valueOf(5.0));
		m.registrarNota2(BigDecimal.valueOf(1.0));
		m.registrarNota3(BigDecimal.valueOf(2.0));
		m.registrarFrequencia(60);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REMF, m.status());
	}

	@Test
	@DisplayName("Quando o aluno está acima da media parcial e abaixo da media")
	void testeAlunosComMediaParcial() {
		Matricula m = new Matricula();

		m.registrarNota1(BigDecimal.valueOf(5.0));
		m.registrarNota2(BigDecimal.valueOf(5.0));
		m.registrarNota3(BigDecimal.valueOf(6.0));
		m.registrarFrequencia(75);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.APRN, m.status());
	}

	@Test
	@DisplayName("Quando o aluno está acima da media parcial e abaixo da media e abaixo do limite de frequencia")
	void testeAlunosComMediaParcialEFrequenciaBaixa() {
		Matricula m = new Matricula();

		m.registrarNota1(BigDecimal.valueOf(5.0));
		m.registrarNota2(BigDecimal.valueOf(5.0));
		m.registrarNota3(BigDecimal.valueOf(6.0));
		m.registrarFrequencia(40);

		m.consolidarParcialmente();

		Assertions.assertEquals(StatusAprovacao.REPF, m.status());
	}
}
