package teste;

public class Main {
	public static void main(String[] args) {
		Turma turma = new Turma();
		
		turma.consolidarParcialmente();
		
		Matricula m = new Matricula();
		
		m.registrarNota1(null);
		
		m.nota1();
	}
}
