package com.aps;

import java.util.Random;

public class InstanciadorDeProcessos {

    private GerenciadorRecursos gerenciador;
    private Random random = new Random();

    public InstanciadorDeProcessos(GerenciadorRecursos gerenciador) {
        this.gerenciador = gerenciador;
    }

    public void criarProcessos(int quantidade, Conta[] contas) {
        for (int i = 0; i < quantidade; i++) {
            int id = 100 + i; // id alto para não conflitar
            Conta origem = contas[random.nextInt(contas.length)];
            Conta destino = contas[random.nextInt(contas.length)];
            while (destino == origem) {
                destino = contas[random.nextInt(contas.length)];
            }
            double valor = 50 + random.nextInt(100);
            Processo p = new Processo(id, origem, destino, valor, gerenciador);
            Thread t = new Thread(p);
            gerenciador.registrarProcesso(p, t);
            t.start();
        }
    }
}
