package br.edu.unifor;

import java.net.*;
import java.io.*;

public class main {

    public static void main(String[] args) {

        int pacotes_recebidos = 0;

        InetAddress group = null;
        int port = 0;
        // Lê o endereço do grupo (IP + porta) da linha de comando
        try {
            group = InetAddress.getByName("all-systems.mcast.net");
            port = Integer.parseInt("1024");
        }
        catch (Exception e) {
            // Erro na leitura dos argumentos ou endereço inválido
            System.err.println("Uso: java MulticastSniffer endereço porta");
            System.exit(1);
        }

        MulticastSocket ms = null;
        try {
            // Cria um socket associado ao endereço do grupo
            ms = new MulticastSocket(port);
            ms.joinGroup(group);
            // Cria uma área de dados para receber conteúdo dos pacotes
            byte[] buffer = new byte[80];
            // Laço para recebimento de pacotes e impressão do conteúdo
            while (true) {
                System.out.println("Escutando 1...");
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                ms.receive(dp);

                pacotes_recebidos++;

                String s = new String(dp.getData());
                System.out.println("Mensagem recebida: " + s);

                System.out.println("Pacotes recebidos foram: " + pacotes_recebidos);

                ms.send(dp);
            }
        }
        catch (IOException e) {
            System.err.println(e);
        }

        // Em caso de erro ou interrupção do programa,
        // sinaliza saída do grupo e fecha socket
        finally {
            if (ms != null) {
                try {
                    ms.leaveGroup(group);
                    ms.close();
                }
                catch (IOException e) {}
            } // if
        } // finally

    }

}
