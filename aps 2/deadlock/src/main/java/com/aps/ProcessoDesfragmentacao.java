package com.aps;

public class ProcessoDesfragmentacao extends Processo {

    private HD hd;
    private RAM ram;
    private int duracao; // em ciclos de tempo
    private int custoRAM; // blocos RAM usados por ciclo

    public ProcessoDesfragmentacao(int id, HD hd, RAM ram, int duracao, GerenciadorRecursos gerenciador) {
        super(id, null, null, 0, gerenciador);
        this.hd = hd;
        this.ram = ram;
        this.duracao = duracao;
        this.custoRAM = Math.max(1, hd.getBlocosOcupados() / 10); // ex: 10% da ocupação HD
    }

    @Override
    public void run() {
        try {
            System.out.println("Desfragmentação iniciada. RAM necessária por ciclo: " + custoRAM);
            for (int i = 0; i < duracao; i++) {
                while (!ram.alocar(custoRAM)) {
                    System.out.println("Esperando RAM disponível para desfragmentação...");
                    Thread.sleep(1000);
                }
                System.out.println("Desfragmentação em andamento... ciclo " + (i+1));
                // Simula liberar RAM após o uso no ciclo
                Thread.sleep(1000);
                ram.liberar(custoRAM);
            }
            System.out.println("Desfragmentação concluída.");
        } catch (InterruptedException e) {
            System.out.println("Desfragmentação interrompida.");
        }
    }

    @Override
    public String getIdentificador() {
        return "D" + super.getId();
    }
}
