package estrategiasDeBusca.heuristica;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import espacoDeEstados.Estado;

public class HillClimbing extends BuscaInformada {
	
	protected Stack<Estado<?>> eAbertos;
	
	/**
	 * Construtor padr�o.
	 */
	public HillClimbing() {
		this(null,null);
	}
	
	/**
	 * Cria uma nova inst�ncia da busca Hill-Climbing, definindo os estados
	 * inicial e objetivo do processo.
	 * @param estadoInicial estado inicial de busca
	 * @param estadoMeta estado que cont�m os objetivos da busca
	 */
	public HillClimbing(Estado<?> estadoInicial, Estado<?> estadoMeta) {
		super(estadoInicial,estadoMeta);
		super.nomeDaEstrategia = "Hill-Climbing (Otmiza��o Discreta)";
		eAbertos = new Stack<Estado<?>>();
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void buscar() {
		Estado<?> eCorrente = eInicial;
		while ((eCorrente != null) && (!eCorrente.equals(eObjetivo))) {
			List<?> eSucessores = eCorrente.getSucessores();							// Obt�m os sucessores do estado corrente,
			Collections.sort((List<Estado<?>>) eSucessores, new OrdenarPorAvaliacao());	// Ordena-os de modo crescente de avalia��o (heur�stica),
			Collections.reverse((List<Estado<?>>) eSucessores); 						// Inverte para que sejam empilhados corretamente (menor em cima), e
			for (Estado<?> estado : (List<Estado<?>>) eSucessores)						// Empilha os estados
				eAbertos.push(estado);
			eCorrente = eAbertos.pop();
		}
		// Se o la�o foi encerrado por um estado v�lido ...
		if (eCorrente != null) {
			// ent�o constru�mos o caminho da solu��o (da folha at� a raiz)
			caminho.add(eCorrente);
			while (eCorrente.getAncestral() != null) {
				eCorrente = eCorrente.getAncestral();
				caminho.add(eCorrente);
			}
			Collections.reverse(caminho);
		}		
	}

}
