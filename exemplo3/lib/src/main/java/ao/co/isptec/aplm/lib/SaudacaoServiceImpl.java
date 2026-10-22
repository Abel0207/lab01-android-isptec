package ao.co.isptec.aplm.lib;

import ao.co.isptec.aplm.api.SaudacaoService;

/**
 * Implementação da interface SaudacaoService.
 * Subprojecto: lib (depende de: api)
 */
public class SaudacaoServiceImpl implements SaudacaoService {

    @Override
    public String saudar(String nome) {
        return "Olá, " + nome + "! Bem-vindo ao ISPTEC.";
    }
}
