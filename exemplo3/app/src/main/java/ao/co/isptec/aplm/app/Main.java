package ao.co.isptec.aplm.app;

import ao.co.isptec.aplm.lib.SaudacaoServiceImpl;
import ao.co.isptec.aplm.api.SaudacaoService;

/**
 * Aplicação principal.
 * Subprojecto: app (depende de: lib e api)
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== Aplicação Multi-Projecto ISPTEC ===");

        SaudacaoService servico = new SaudacaoServiceImpl();

        System.out.println(servico.saudar("Estudante"));
        System.out.println(servico.saudar("Professor Costa"));
        System.out.println("Aplicação concluída com sucesso!");
    }
}
