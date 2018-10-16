package turing;

/* MAQUINA MULTIFITAS - PADRAO DOS ARGUMENTOS
      1a linha: numero de estados, numero de simbolos no alfabeto de entrada, numero de simbolos no alfabeto da fita, numero de transicoes, numero de fitas
      2a linha: estados (estados finais possuem f no nome)
      3a linha: alfabeto de entrada (branco = _ )
      4a linha: alfabeto da fita
      X linhas: funcoes de transicao no formato (estado, leitura1, leitura 2, ..., leituraX, novoEstado) = (escreve1, move1) = ... = (escreveX, moveX)
      Ultimas linha: fitas   */

public class Main {

   public static void main(String[] args) {
      Maquina m;
            
      if(args.length == 0){         
         m = new Maquina();
      }
      else
         m = new Maquina(args);
   }

}
