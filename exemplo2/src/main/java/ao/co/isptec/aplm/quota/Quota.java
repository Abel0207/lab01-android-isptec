package ao.co.isptec.aplm.quota;

/**
 * Classe de domínio para gerir citações famosas.
 * Uma citação contém um ID, o autor e o texto da citação.
 */
public class Quota {

    private Long id;
    private String autor;
    private String texto;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return "Quota{id=" + id + ", autor='" + autor + "', texto='" + texto + "'}";
    }

    // Método main para executar a aplicação
    public static void main(String[] args) {
        System.out.println("Aplicação Quota em execução");
        Quota q = new Quota();
        q.setId(new Long(1));
        q.setAutor("Joao Costa");
        q.setTexto("Deus é vida.");
        System.out.println(q);
    }
}
