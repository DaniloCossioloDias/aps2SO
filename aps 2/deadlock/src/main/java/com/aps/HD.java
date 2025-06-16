package com.aps;

import java.util.Arrays;

public class HD {
    private final int tamanho; // total de blocos
    private final boolean[] blocos;

    public HD(int tamanho) {
        this.tamanho = tamanho;
        this.blocos = new boolean[tamanho];
    }

    public synchronized boolean ocupar(int blocosParaOcupar) {
        int ocupados = 0;
        for (int i = 0; i < tamanho && ocupados < blocosParaOcupar; i++) {
            if (!blocos[i]) {
                blocos[i] = true;
                ocupados++;
            }
        }
        return ocupados == blocosParaOcupar;
    }

    public synchronized void liberar(int blocosParaLiberar) {
        int liberados = 0;
        for (int i = 0; i < tamanho && liberados < blocosParaLiberar; i++) {
            if (blocos[i]) {
                blocos[i] = false;
                liberados++;
            }
        }
    }

    public int getTamanho() {
        return tamanho;
    }

    public synchronized int getBlocosOcupados() {
        int count = 0;
        for (boolean b : blocos) {
            if (b) count++;
        }
        return count;
    }

    public synchronized double getPercentualFragmentacao() {
        // Fragmentação simples: número de blocos ocupados que não são contíguos
        // Aqui fazemos uma estimativa simples: conta quantos "buracos" existem

        int buracos = 0;
        boolean dentroBuraco = false;

        for (boolean ocupado : blocos) {
            if (!ocupado && !dentroBuraco) {
                buracos++;
                dentroBuraco = true;
            } else if (ocupado) {
                dentroBuraco = false;
            }
        }
        // Fragmentação como percentual dos buracos sobre tamanho
        return 100.0 * buracos / tamanho;
    }
}
