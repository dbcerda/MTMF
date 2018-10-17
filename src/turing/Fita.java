package turing;

import java.util.ArrayList;

class Fita {

    private String alfabetoLeitura;
    private String alfabetoEscrita;
    private ArrayList<String> conteudo;
    private Integer cabecote;


    
    Fita(String inicial){
        conteudo = new ArrayList<String>(100);
        char[] temp = inicial.toCharArray();
        setCabecote(0);
        int i, j;
        for(i = 0; i < temp.length ; i++){
            conteudo.add(""+temp[i]);
        }

    }

    public String getalfabetoLeitura() {
        return alfabetoLeitura;
    }

    public void setalfabetoLeitura(String alfabetoLeitura) {
        this.alfabetoLeitura = alfabetoLeitura;
    }

    public String getAlfabetoEscrita() {
        return alfabetoEscrita;
    }

    public void setAlfabetoEscrita(String alfabetoEscrita) {
        this.alfabetoEscrita = alfabetoEscrita;
    }

    public ArrayList<String> getFita() {
        return conteudo;
    }

    public void setFita(ArrayList<String> fita) {
        this.conteudo = fita;
    }
    
    public Integer getCabecote() {
        return cabecote;
    }

    public void setCabecote(Integer cabecote) {
        this.cabecote = cabecote;
    }

    public void movimento(String direcao){
        if(direcao.equals("D")){
            this.cabecote ++;
        }
        if(direcao.equals("E")){
            this.cabecote --;
        }
    }

    public String leFita(){
       
      if(cabecote >= conteudo.size())
      {
         //System.out.println("cabecote passou do tamanho");
         return Maquina.BRANCO;
      }
      else
      {
         return getFita().get(cabecote);
      }
    }

    public void escreveNaFita(String entrada){
       if(cabecote >= conteudo.size())
       {
          conteudo.add(entrada);
       }
       else
       {
         this.conteudo.set(cabecote, entrada);
       }
        
       
    }
    public String imprimeFita()
    {
       String retorno = "";
        for (int i = 0; i < conteudo.size(); i++) {
          retorno = retorno + conteudo.get(i);
       }
       return retorno;
       
    }

}
