package com.aps;

public class RAM {
    private final int capacidade; // blocos disponíveis
    private int usados = 0;

    public RAM(int capacidade) {
        this.capacidade = capacidade;
    }

    public synchronized boolean alocar(int blocos) {
        if (usados + blocos <= capacidade) {
            usados += blocos;
            return true;
        }
        return false;
    }

    public synchronized void liberar(int blocos) {
        usados -= blocos;
        if (usados < 0) usados = 0;
    }

    public synchronized int getUsados() {
        return usados;
    }

    public int getCapacidade() {
        return capacidade;
    }
}
