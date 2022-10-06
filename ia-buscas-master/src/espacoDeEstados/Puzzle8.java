package espacoDeEstados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Esta classe modela o famoso quebra-cabe�as chamado 8-Puzzle na forma de um
 * estado para um espa�o. Nele temos um tabuleiro de tr�s linhas e tr�s colunas,
 * oito pe�as numeradas e uma posi��o livre. As pe�as podem ser movimentadas na
 * vertical ou na horizontal, ocupando sempre a posi��o vazia.
 * 
 * Ex: +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+
 *     | 1 | 2 | 3 |  | 1 | 2 | 3 |  | 1 | 2 | 3 |  | 1 | 2 | 3 |  | 1 | 2 | 3 |  | 1 | 2 | 3 |
 *     +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+
 *     | 4 | 5 | 6 |  | 4 | 5 |   |  | 4 |   | 5 |  |   | 4 | 5 |  | 7 | 4 | 5 |  | 7 | 4 | 5 |
 *     +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+
 *     | 7 | 8 |   |  | 7 | 8 | 6 |  | 7 | 8 | 6 |  | 7 | 8 | 6 |  |   | 8 | 6 |  | 8 |   | 6 |
 *     +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+  +---+---+---+
 *     
 * @author Leandro C. Fernandes
 *
 */
public class Puzzle8 extends Estado<char[]> {
	
	public static final char[] TABULEIRO_ORGANIZADO = {'1','2','3','4','5','6','7','8',' '};
	
	private static int contGlobal = 0;	// contador global de inst�ncias
	private char[] tabuleiro;			// vetor que descreve a posi��o das pe�as do quebra-cabe�as
		
	/**
	 * Construtor padr�o. Cria uma inst�ncia que representa uma configura��o
	 * qualquer para o tabuleiro do quebra-cabe�as
	 */
	public Puzzle8() {
		this(new char[9], null, 0);
	}
	
	/**
	 * Permite criar um novo estado e definir, de imediato, qual � a disposi��o
	 * de suas pe�as no tabuleiro. 
	 * @param cfgTabuleiro vetor de caracteres que representa a posi��o das pe�as
	 */
	public Puzzle8(char[] cfgTabuleiro) {
		this(cfgTabuleiro, null, 0);
	}
	
	/**
	 * Permite criar um novo estado, definindo de imediato qual a configura��o
	 * do tabuleiro, o estado que o gerou e tamb�m o n�vel em que este elemento
	 * ocupar� na �rvore de busca. 
	 * @param cfgTabuleiro vetor de caracteres que representa a disposi��o das pe�as.
	 * @param ancestral refer�ncia ao nodo pai do estado corrente.
	 * @param nivel altura do nodo em rela��o a raiz da �rvore de busca.
	 */
	public Puzzle8(char[] cfgTabuleiro, Puzzle8 ancestral, int nivel) {
		super();
		setId( contGlobal++ );
		setNivel(nivel);
		setAncestral(ancestral);
		tabuleiro = cfgTabuleiro.clone();
	}
	
	/**
	 * Define o posicionamento das pe�as do quebra-cabe�as, determinando a
	 * configura��o que ser� assumida pelo tabuleiro. 
	 * @param cfgTabuleiro vetor representando a posi��o dos elementos no tabuleiro 
	 */
	@Override
	public void setEstado(char[] cfgTabuleiro) {
		for (int i = 0; i < cfgTabuleiro.length; i++) {
			this.tabuleiro[i] = cfgTabuleiro[i];	
		}
	}
	
	/**
	 * Retorna a configura��o das pe�as no tabuleiro do quebra-cabe�as.
	 * @return vetor representando a configura��o do tabuleiro
	 */
	@Override
	public char[] getEstado() {
		char[] cfgTabuleiro = new char[9];
		for (int i = 0; i < tabuleiro.length; i++) {
			cfgTabuleiro[i] = tabuleiro[i];
		}
		return cfgTabuleiro;
	}
	
	/**
	 * M�todo global que retorna a meta (estado objetivo) deste problema. 
	 * @return estado correspondente ao tabuleiro ordenado
	 */
	public static Puzzle8 getMeta() {
		
		char[] cfgOrganizada = new char[9];
		cfgOrganizada[0] = '1'; cfgOrganizada[1] = '2'; cfgOrganizada[2] = '3';
		cfgOrganizada[3] = '4'; cfgOrganizada[4] = '5'; cfgOrganizada[5] = '6';
		cfgOrganizada[6] = '7'; cfgOrganizada[7] = '8'; cfgOrganizada[8] = ' ';
		
		return new Puzzle8(cfgOrganizada);
	}
	
	/**
	 * Fun��o que gera os estados sucessores de acordo com os diferentes movimentos poss�veis
	 * a partir da configura��o do tabuleiro.
	 * @return lista de estados sucessores
	 */
	@Override
	public List<Puzzle8> getSucessores() {
		char[] cfgNova;
		Puzzle8 estado;
		List<Puzzle8> estadosSucessores = new ArrayList<Puzzle8>();
		
		int posLivre = 0;
		while (tabuleiro[posLivre] != ' ')
			posLivre++;
		
		switch (posLivre) {
		case 0:
			cfgNova = getEstado();
			cfgNova[0] = cfgNova[1];
			cfgNova[1] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[0] = cfgNova[3];
			cfgNova[3] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			break;
		case 1:
			cfgNova = getEstado();
			cfgNova[1] = cfgNova[0];
			cfgNova[0] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[1] = cfgNova[2];
			cfgNova[2] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[1] = cfgNova[4];
			cfgNova[4] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			break;
		case 2:
			cfgNova = getEstado();
			cfgNova[2] = cfgNova[1];
			cfgNova[1] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[2] = cfgNova[5];
			cfgNova[5] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			break;
		case 3:
			cfgNova = getEstado();
			cfgNova[3] = cfgNova[0];
			cfgNova[0] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estadosSucessores.add(estado);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			cfgNova = getEstado();
			cfgNova[3] = cfgNova[4];
			cfgNova[4] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estadosSucessores.add(estado);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			cfgNova = getEstado();
			cfgNova[3] = cfgNova[6];
			cfgNova[6] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			break;
		case 4:
			cfgNova = getEstado();
			cfgNova[4] = cfgNova[1];
			cfgNova[1] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[4] = cfgNova[5];
			cfgNova[5] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[4] = cfgNova[7];
			cfgNova[7] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[4] = cfgNova[3];
			cfgNova[3] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			break;
		case 5:
			cfgNova = getEstado();
			cfgNova[5] = cfgNova[2];
			cfgNova[2] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[5] = cfgNova[8];
			cfgNova[8] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[5] = cfgNova[4];
			cfgNova[4] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			break;
		case 6:
			cfgNova = getEstado();
			cfgNova[6] = cfgNova[3];
			cfgNova[3] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[6] = cfgNova[7];
			cfgNova[7] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			break;
		case 7:
			cfgNova = getEstado();
			cfgNova[7] = cfgNova[4];
			cfgNova[4] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[7] = cfgNova[8];
			cfgNova[8] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[7] = cfgNova[6];
			cfgNova[6] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			break;
		case 8:
			cfgNova = getEstado();
			cfgNova[8] = cfgNova[5];
			cfgNova[5] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			cfgNova = getEstado();
			cfgNova[8] = cfgNova[7];
			cfgNova[7] = ' ';
			estado = new Puzzle8(cfgNova, this, getNivel()+1);
			estado.setCusto(this.getCusto() + 1);
			estado.setAvaliacao( estado.heuristica(Puzzle8.TABULEIRO_ORGANIZADO) );
			estado.setF( estado.getCusto() + estado.getAvaliacao() );
			estadosSucessores.add(estado);
			break;
		}
		
		return estadosSucessores;
	}
	
	/**
	 * Determina o valor de avalia��o do estado em rela��o a uma configura��o
	 * objetivo (estado meta). A m�trica implementada (heur�stica) consiste em
	 * calcular a dist�ncia de cada pe�a na configura��o atual em rela��o a sua
	 * posi��o esperada no objetivo. 
	 * @param cfgEstadoMeta a configura��o do estado utilizado como meta. 
	 * @return somat�rio das dist�ncias das pe�as em rela��o a posi��o objetivo. 
	 */
	public int heuristica(char[] cfgEstadoMeta) {
		String strEstadoMeta = new String(cfgEstadoMeta);		
		int heurist = 0;
		
		for (int i = 0; i < tabuleiro.length; i++) {
			int posEsperada = strEstadoMeta.indexOf( tabuleiro[i] );
			int linAtual = i / 3;
			int colAtual = i % 3;
			int linEsperada = posEsperada / 3;
			int colEsperada = posEsperada % 3;
			int dist = Math.abs(linEsperada - linAtual) + Math.abs(colEsperada - colAtual);
			heurist += dist;
		}		
		return heurist;
	}
		
	/**
	 * Retorna uma String correspondente a configura��o das pe�as no tabuleiro. 
	 */
	@Override
	public String toString() {
		String msg = "";
		msg += "+---+---+---+";
		msg += "\n| " + tabuleiro[0] + " | " + tabuleiro[1] + " | " + tabuleiro[2] + " |\n";
		msg += "+---+---+---+";
		msg += "\n| " + tabuleiro[3] + " | " + tabuleiro[4] + " | " + tabuleiro[5] + " |  Nodo #" + getId() + "  N�vel: " + getNivel() + "\n";
		msg += "+---+---+---+  g(#" + getId() + ") = " + getCusto() + "  h(#" + getId() + ") = " + getAvaliacao();
		msg += "\n| " + tabuleiro[6] + " | " + tabuleiro[7] + " | " + tabuleiro[8] + " |\n";
		msg += "+---+---+---+";
		return msg;
	}

	/**
	 * Implementa a compara��o entre dois tabuleiros, verificando se a disposi��o
	 * das pe�as � igual nas duas situa��es.
	 */
	@Override
	public boolean equals(Object estado) {
		if (this == estado)
			return true;
		if (estado == null)
			return false;
		if (getClass() != estado.getClass())
			return false;
		Puzzle8 other = (Puzzle8) estado;
		if (!Arrays.equals(tabuleiro, other.tabuleiro))
			return false;
		return true;
	}
	
}
