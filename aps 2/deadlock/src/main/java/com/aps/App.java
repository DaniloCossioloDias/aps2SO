package com.aps;

public class App {

    public static void main(String[] args) throws InterruptedException {

        Conta a = new Conta(1, 300);
        Conta b = new Conta(2, 300);
        Conta c = new Conta(3, 300);
        Conta d = new Conta(4, 300);
        Conta e = new Conta(5, 300);
        Conta f = new Conta(6, 300);

        GerenciadorRecursos gerenciador = new GerenciadorRecursos();

        Processo p1 = new Processo(1, a, b, 150, gerenciador);
        Processo p2 = new Processo(2, c, d, 150, gerenciador);
        Processo p4 = new Processo(4, e, f, 150, gerenciador);
        Processo p5 = new Processo(5, f, e, 150, gerenciador); // cria deadlock circular

        Thread t1 = new Thread(p1);
        Thread t2 = new Thread(p2);
        Thread t4 = new Thread(p4);
        Thread t5 = new Thread(p5);

        gerenciador.registrarProcesso(p1, t1);
        gerenciador.registrarProcesso(p2, t2);
        gerenciador.registrarProcesso(p4, t4);
        gerenciador.registrarProcesso(p5, t5);

        // Criação do HD e RAM
        HD hd = new HD(100);
        RAM ram = new RAM(20);

        // Simula fragmentação inicial ocupando blocos aleatórios
        hd.ocupar(70);
        System.out.println("Fragmentação inicial do HD: " + hd.getPercentualFragmentacao() + "%");

        // Processo de desfragmentação
        ProcessoDesfragmentacao defrag = new ProcessoDesfragmentacao(999, hd, ram, 10, gerenciador);
        Thread tDesfrag = new Thread(defrag);
        gerenciador.registrarProcesso(defrag, tDesfrag);

        // Iniciando o monitor de deadlock
        Thread monitor = new Thread(() -> {
            try {
                gerenciador.verificarDeadlockEmLoop(1000);
            } catch (InterruptedException error) {
                System.out.println("Monitor encerrado.");
            }
        });

        monitor.start();
        t1.start();
        t2.start();
        t4.start();
        t5.start();
        tDesfrag.start();

        // Instanciador cria mais processos aleatórios
        InstanciadorDeProcessos instanciador = new InstanciadorDeProcessos(gerenciador);
        Conta[] contas = {a, b, c, d, e, f};
        instanciador.criarProcessos(3, contas);

        t1.join();
        t2.join();
        t4.join();
        t5.join();
        tDesfrag.join();

        monitor.interrupt();
        monitor.join();

        System.out.println("Todos os processos foram finalizados.");
    }
}
