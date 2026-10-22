package ao.co.isptec.aplm.api;

/**
 * Interface da API - define o contrato que as implementações devem seguir.
 * Subprojecto: api
 */
public interface SaudacaoService {

    /**
     * Retorna uma saudação para o nome fornecido.
     * @param nome Nome da pessoa a saudar
     * @return String com a saudação
     */
    String saudar(String nome);
}
