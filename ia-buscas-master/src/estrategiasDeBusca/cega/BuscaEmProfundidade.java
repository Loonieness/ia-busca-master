package estrategiasDeBusca.cega;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import espacoDeEstados.Estado;

/**
 * Esta classe implementa uma estrat�gia de busca cega conhecida como "Busca em
 * Profundidade", que � caracter�stica por explorar o espa�o se aprofundando no
 * ramo atual antes de faz�-lo noutra ramifica��o.
 * 
 * @author Leandro C. Fernandes
 *
 */
public class BuscaEmProfundidade extends BuscaCega {
	
	protected Stack<Estado<?>> eAbertos;

	/**
	 * Construtor padr�o.
	 */
	public BuscaEmProfundidade() {
		this(null,null);
	}
	
	/**
	 * Cria uma nova inst�ncia de Busca em Profundidade, definindo os estados
	 * inicial e objetivo do processo.
	 * @param estadoInicial estado inicial de busca
	 * @param estadoMeta estado que cont�m os objetivos da busca
	 */
	public BuscaEmProfundidade(Estado<?> estadoInicial, Estado<?> estadoMeta) {
		super(estadoInicial,estadoMeta);
		super.nomeDaEstrategia = "Busca em Profundidade";
		eAbertos = new Stack<Estado<?>>();
	}
	
	/**
	 * Implementa efetivamente a estrat�gia de busca, iniciando a explora��o do
	 * espa�o a partir do estado inicial e seguindo n�vel a n�vel no ramo atual
	 * at� alcan�ar um estado que atenda os objetivos ou n�o tenha sucessor.
	 * Ao t�rmino, o caminho correspondente a solu��o ter� sido armazenado no
	 * atributo caminho.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void buscar() {
		Estado<?> eCorrente = eInicial;
		while ((eCorrente != null) && (!eCorrente.equals(eObjetivo))) {
			for (Estado<?> estado : (List<Estado<?>>) eCorrente.getSucessores())
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
