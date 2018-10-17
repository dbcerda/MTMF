package turing;

class Estado {
   String nome;
   Transicao[] funcoes;
   boolean estadoFinal;
   static int numTrans = 10;


   Estado(String nome_) {
      estadoFinal = nome_.contains("f");
      nome = nome_.replaceAll("f", "");      
      funcoes = new Transicao[numTrans];
      for (int i = 0; i < numTrans; i++) {
         funcoes[i] = null;
      }
   }

   void adicionaTransicao(String le_, String novoEstado_, String escreve_, String move_){
      for (int i = 0; i < numTrans; i++) {
         
         if(funcoes[i] == null)
         {

            funcoes[i] = new Transicao(le_, novoEstado_, escreve_, move_);
            break;
         }
      }           
   }
   void adicionaTransicao(Transicao transicao_) {
      for (int i = 0; i < numTrans; i++) {
         
         if(funcoes[i] == null)
         {
            //System.out.println("Transicao adicionada eh: " + transicao_.le + " " + transicao_.novoEstado + " " + transicao_.escreve + " " + transicao_.move);
            funcoes[i] = transicao_;
            break;
         }
      }
   }

   public boolean isEstadoFinal() {
        return estadoFinal;
    }

    public Transicao getTransicao(String lida){
       int i = 0;
       while(funcoes[i]!= null)
       {
          
          if(funcoes[i].le.equals(lida))
          {
             return funcoes[i];
          }

          i++;
       }
       return null;
    }

}
