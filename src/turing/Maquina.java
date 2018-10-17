package turing;

import java.util.Scanner;

public class Maquina {
   public static String BRANCO = "_";
   String[] descricao;
   Estado[] registrador;
   String estadoAtual;
   Fita[] fita;   
   boolean terminou;   

   Maquina() {
      descricao = null;
      terminou = false;
      
      Scanner scanner = new Scanner(System.in);
      
      int numEstados = scanner.nextInt();
      int numSimbolosEntrada =  scanner.nextInt();
      int numSimbolosSaida =  scanner.nextInt();
      int numTransicoes =  scanner.nextInt();
      int numFitas = scanner.nextInt();
      
      registrador = new Estado[numEstados];
      
      for (int i = 0; i < numEstados; i++) {
         registrador[i] = null;
      }
      
      fita = new Fita[numFitas];
      
      for (int i = 0; i < numFitas; i++) {
         fita[i] = null;
      }
      
      //offset controla em que linhas a informacao comeca
      int offset = 5;
      
      Estado estado_;
      for(int i = offset; i < offset + numEstados ; i++) {
         estado_ = new Estado(scanner.next());
         adicionaEstado(estado_);         
      }
      
      estadoAtual = registrador[0].nome;
      
      int oldOffset = offset;
      offset += numSimbolosEntrada + numSimbolosSaida; 
      
      for(int i = oldOffset; i < offset; i++) {
         scanner.next();
      }
      
      for(int i = offset; i < offset + numTransicoes; i++) {
         geraTransicao(scanner.next());         
      }      
      
                           
      for(int j = 0; scanner.hasNext(); j++) {         
         if (fita[j] == null)
            fita[j] = new Fita(scanner.next());         
      }
      
      for(int i = 0; i < fita.length; i++) {
         if(fita[i] == null) {
            fita[i] = new Fita("");
         }         
      }
      
      rodaMaquina();
      
   }
   
   Maquina(String[] args) {
      descricao = args;
      terminou = false;            
      
      inicializa();
      
      rodaMaquina();
   }
         
   private void adicionaEstado(Estado estado) {
      for(int i = 0; i < registrador.length; i++) {
         if(registrador[i] == null)
         {
            //System.out.println("Estado adicionado foi: " + estado.nome);
            registrador[i] = estado;
            break;
         }
      }
   }
   
    private void geraTransicao(String definicao) {
      
      String[] quebraTransicao = definicao.split("=");      
      
      //cadeia tem informacao de uma transicao completa, elementos vai guardar os detalhes da transicao
      String[] cadeia = new String[quebraTransicao.length];
      String[] elementos;
      String temp;
      
      //remove parenteses
      for(int i = 0; i < cadeia.length; i++) {
         temp = quebraTransicao[i].replaceAll("\\(","");
         cadeia[i] = temp.replaceAll("\\)", "");         
      }                  
      
      String nomeEstado = "";
      String nomeNovoEstado = "";
      String move = "";
      String escreve = "";
      String le = "";
      int j;
      
      //a primeira cadeia contem o estado referenciado, caracteres lidos e estado pro qual avanca, as outras escritas e movimentos     
      for(int i = 0; i < cadeia.length; i++) {
         elementos = cadeia[i].trim().split(",");         
         if (i == 0 ) {
            nomeEstado = elementos[0];
            for(j = 1; j < elementos.length -1; j++) {
               le+= elementos[j];
            }
            nomeNovoEstado = elementos[j];
         }
         else {
            escreve += elementos[0];
            move += elementos[1];
         }         
         
      }
      
      Transicao transicao = new Transicao(le, nomeNovoEstado, escreve, move);      
      
      //se existe o estado, adiciona a transicao, se por algum motivo nao, cria o estado
      if(contemEstado(nomeEstado)) {                 
         getEstado(nomeEstado).adicionaTransicao(transicao);
            
      }
      else
      {         
         Estado estado = new Estado(nomeEstado);
         estado.adicionaTransicao(transicao);
         adicionaEstado(estado);         
      }
      if(!contemEstado(nomeNovoEstado)) {
         Estado novoEstado = new Estado(nomeNovoEstado);         
         adicionaEstado(novoEstado);
      }

   }
   
   private boolean contemEstado(String nomeEstado) {
       for(int i = 0; i < registrador.length; i++) {

          if(registrador[i].nome.equals(nomeEstado)) {
             return true;
          }         
       }
       return false;
   }

   private Estado getEstado(String nomeEstado)   {      
      for(int i = 0; i < registrador.length; i++) {
         if(registrador[i].nome.equals(nomeEstado))
         {
            return registrador[i];
         }          
       }
       return null;
    }
   
  
   
   private void rodaMaquina() {

      while(!terminou)
      {
         proximoPasso();
      }
   }

    private void proximoPasso()
   {
      
      String le = "";
      for (int i = 0; i < fita.length; i++) {
         le += fita[i].leFita();
      }
       
      //System.out.println("O valor lido foi " + le);
      Estado estado = getEstado(estadoAtual);
      //System.out.println("O nome do estado e " + ((estado == null) ? "nulo" : estado.nome) );
      Transicao transicao = estado.getTransicao(le);
      if(transicao == null)
      {
         if(getEstado(estadoAtual).isEstadoFinal())
         {
            System.out.println("Palavra aceita");
            terminou = true;
         }
         else
         {              
            terminou = true;
            System.out.println("Ultimo estado = " + getEstado(estadoAtual).nome + ", final = " + getEstado(estadoAtual).isEstadoFinal());              
              
         }
         for (int i = 0; i < fita.length; i++) {
            System.out.println("Fita"+ (i+1) + ": " + fita[i].imprimeFita());
         }
      }
      else {
         char[] escreve_ = transicao.escreve.toCharArray();
         char[] move_ = transicao.move.toCharArray();
         for (int i = 0; i < fita.length; i++) {
            //System.out.println("escreve: " + escreve_[i] + " e move: " + move_[i]);
            fita[i].escreveNaFita( new String(escreve_[i] + ""));
            fita[i].movimento( new String(move_[i]+ ""));
         estadoAtual = transicao.novoEstado;
         //System.out.println("O novo estado e " + transicao.novoEstado);
         }                             
      }
      
    }
    
    /*carrega a descricao pelos argumentos passados nas configuracoes de projeto*/
   private void inicializa() {            
      
      int numEstados = Integer.parseInt(descricao[0]);
      int numSimbolosEntrada =  Integer.parseInt(descricao[1]);
      int numSimbolosSaida =  Integer.parseInt(descricao[2]);
      int numTransicoes =  Integer.parseInt(descricao[3]);
      int numFitas = Integer.parseInt(descricao[4]);
      
      registrador = new Estado[numEstados];
      
      for (int i = 0; i < numEstados; i++) {
         registrador[i] = null;
      }
      
      fita = new Fita[numFitas];
      
      for (int i = 0; i < numFitas; i++) {
         fita[i] = null;
      }
      
      //offset controla em que linhas a informacao comeca
      int offset = 5;
      
      Estado estado_;
      for(int i = offset; i < offset + numEstados ; i++) {
         estado_ = new Estado(descricao[i]);
         adicionaEstado(estado_);         
      }
      
      estadoAtual = registrador[0].nome;
      
      offset += numSimbolosEntrada + numSimbolosSaida + numEstados; 
      
      for(int i = offset; i < offset + numTransicoes; i++) {
         geraTransicao(descricao[i]);         
      }      
        
      offset += numTransicoes;

      for(int i = offset, j = 0; i < descricao.length; i++, j++) {         
         if (fita[j] == null)
            fita[j] = new Fita(descricao[i]);         
      }
      
      for(int i = 0; i < fita.length; i++) {
         if(fita[i] == null) {
            fita[i] = new Fita("");
         }         
      }
      
   }
   

}
