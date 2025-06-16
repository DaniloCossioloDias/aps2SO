// Recurso.java
public class Recurso {
    private String nome;
    private int total;
    private int disponivel;

    public Recurso(String nome, int total) {
        this.nome = nome;
        this.total = total;
        this.disponivel = total;
    }

    public synchronized boolean alocar(int quantidade) {
        if (quantidade <= disponivel) {
            disponivel -= quantidade;
            return true;
        }
        return false;
    }

    public synchronized void liberar(int quantidade) {
        disponivel += quantidade;
        if (disponivel > total) disponivel = total;
    }

    public int getDisponivel() {
        return disponivel;
    }

    public String getNome() {
        return nome;
    }
}
