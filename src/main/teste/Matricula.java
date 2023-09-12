package teste;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Matricula {
	private Turma turma;

	private Aluno aluno;

	private BigDecimal nota1;

	private BigDecimal nota2;

	private BigDecimal nota3;

	private Integer frequencia;

	private StatusAprovacao status;

	public BigDecimal nota1() {
		return this.nota1;
	}

	public BigDecimal nota2() {
		return this.nota2;
	}

	public BigDecimal nota3() {
		return this.nota3;
	}

	public StatusAprovacao status() {
		return this.status;
	}

	public void registrarNota1(BigDecimal nota1) {
		this.nota1 = nota1;
	}

	public void registrarNota2(BigDecimal nota2) {
		this.nota2 = nota2;
	}

	public void registrarNota3(BigDecimal nota3) {
		this.nota3 = nota3;
	}

	/**
	 * 
	 * @return verdadeiro caso tenha que fazer reposição
	 */
	public boolean verificarSeRecuperacaoImediata() {

		if (!(nota1.doubleValue() >= 3 && nota2.doubleValue() >= 3 && nota3.doubleValue() >= 3)) {
			return true;
		} else {
			return false;
		}
	}

	public void determinarCriterioDoAluno(BigDecimal media) {

		StatusAprovacao status = null;

		if (frequencia < 75 && media.doubleValue() >= 5) {
			status = StatusAprovacao.REPF;

		} else if (frequencia < 75 && media.doubleValue() < 5) {
			status = StatusAprovacao.REMF;

		} else if (frequencia >= 75 && media.doubleValue() < 3) {
			status = StatusAprovacao.REP;

		} else if (frequencia >= 75 && media.doubleValue() >= 7) {

			if (verificarSeRecuperacaoImediata()) {
				status = StatusAprovacao.REC;
			} else {
				status = StatusAprovacao.APR;
			}

		} else if (frequencia >= 75 && media.doubleValue() >= 5 && media.doubleValue() < 7) {

			if (verificarSeRecuperacaoImediata()) {
				status = StatusAprovacao.REC;
			} else {
				status = StatusAprovacao.APRN;
			}

		} else if (frequencia >= 75 && media.doubleValue() >= 3 && media.doubleValue() < 5) {
			status = StatusAprovacao.REC;
		}

		this.status = status;
	}

	/**
	 * Segue as regras estabelecidas pelos artigos do regulamento de graduação da
	 * UFRN:
	 * http://www.sistemas.ufrn.br/download/sigaa/public/regulamento_dos_cursos_de_graduacao.pdf
	 * 
	 * A partir do artigo 104
	 * 
	 * @throws Exception
	 */
	public void consolidarParcialmente() throws NullPointerException {

		if (nota1 != null && nota2 != null && nota3 != null) {

			BigDecimal notaMedia = (nota1.add(nota2).add(nota3)).divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);
			determinarCriterioDoAluno(notaMedia);                           

		} else {
			throw new NullPointerException("Dados de notas incompleta");
		}

	}

	public Integer frequencia() {
		return frequencia;
	}

	public void registrarFrequencia(Integer frequencia) {
		this.frequencia = frequencia;
	}
}
