package turing;

class Transicao {
   String le;
   String escreve;
   String move;
   String novoEstado;

   Transicao( String le_, String novoEstado_, String escreve_, String move_) {
      le = le_;
      escreve = escreve_;
      move = move_;
      novoEstado = novoEstado_;
   }

}
