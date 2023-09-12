package teste;

import java.util.List;

public class Turma {
	private int quantidadeDeAlunos;
	
	private int id;
	
	private Docente docente;
	
	private Disciplina disciplina;
	
	private List<Matricula> matriculas;

	public void consolidarParcialmente() {
		for(Matricula m : matriculas)
		{
			m.consolidarParcialmente();
		}
	}
}
