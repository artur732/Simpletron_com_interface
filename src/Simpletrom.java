
public class Simpletrom {
    
    private String[] memoriaSimpletrom;
    private String acumulador;
    private String registradorInstrucoes;
    private int contadorInstrucoes;
    private String texto;
    private boolean input;
    private boolean exibir;
    private int ci;
    
    public enum CaseEnum {
        
        DEUCERTO, SINTAXERRO, TERMINOU, LEITURA, EXIBICAO;
    }
    
    //iniciando o simpletrom com 100 espaços de memória
    public Simpletrom() {
        
        this.memoriaSimpletrom = new String[100];
        this.contadorInstrucoes=0;
        this.acumulador="0000";
        this.registradorInstrucoes="0000";
        this.input=false;
        this.exibir=false;
        this.ci=0;
    }
    
    public void iniciaMemoria() {
        
        for(int i=0;i<100;i++) {
            
            this.memoriaSimpletrom[i]="0000";
        }
    }
    
    public void passaDoTextoAMemoria(){
        
        String[] linhas = this.texto.split("\n");
        
        if(linhas.length>100) {
            //erro né
        }
        
        for(int i=0;i<linhas.length;i++) {
            
            this.memoriaSimpletrom[i]=linhas[i];
            
        }
        
    }
    
    public void passaDaMemoriaAoTexto() {
        
        this.texto="";
        int i=0;
        while(i<100) {
            
            this.texto=this.texto+this.memoriaSimpletrom[i];
            this.texto=this.texto+"\n";
            i++;
        }
      
    }
    
    public String atualizaRegistradores() {
        
        String reg = this.getRegistradorInstrucoes();
        char[] regInst=reg.toCharArray();
        String codOperacao=String.valueOf(new char[] {regInst[0], regInst[1]});
        String codOperand=String.valueOf(new char[] {regInst[2], regInst[3]});
        
        return ("Registrador de instruções:\n"+this.getRegistradorInstrucoes()+
        "\n\n\nACC:\n"+this.getAcumulador()+"\n\n\nContador de instruções:\n"+String.valueOf(this.getCi())+
        "\n\n\nCódigo de operação:\n"+codOperacao+"\n\n\nOperando:\n"+codOperand);
    }
    
    public int tudo() {
        
        int recebe;
        while(true) {
            
            recebe=this.umPasso();
            if(recebe!=CaseEnum.DEUCERTO.ordinal()) {
                
                return recebe;
                /*if(recebe==CaseEnum.SINTAXERRO.ordinal()) {
                    return CaseEnum.SINTAXERRO.ordinal();
                }
                
                if(recebe==CaseEnum.TERMINOU.ordinal()) {
                    return CaseEnum.TERMINOU.ordinal();
                }*/
            }
        }
    }
    
    public void incrementaContador() {
        this.contadorInstrucoes+=1;
    }
    public int umPasso() {
        
        this.registradorInstrucoes=this.memoriaSimpletrom[this.contadorInstrucoes];
        this.ci=this.contadorInstrucoes;
        CaseEnum recebe = this.cases();
        
        this.contadorInstrucoes+=1;
        
        return recebe.ordinal();
    }

    public CaseEnum cases() {
        
        int enderecoInt;
        String endereco;
        char[] cadeia = this.registradorInstrucoes.toCharArray();
        
        if(cadeia[0]=='1') {
            
            if(cadeia[1]=='0') {
                
                //ler do teclado e armazenar em lugar da memória                
                return CaseEnum.LEITURA;
            }
            
            if(cadeia[1]=='1') {
                
                //exibir na tela a palavra armazenada em endereço expecífico
                
               return CaseEnum.EXIBICAO;
            } else {
                return CaseEnum.SINTAXERRO;
                //comando inválido
            }
        }
        
        if(cadeia[0]=='2') {
            
            endereco=""+cadeia[2]+cadeia[3];
            enderecoInt=Integer.parseInt(endereco);
            
            if(cadeia[1]=='0') {
                
                //copia de indereço beta para o acumulador
                
                this.acumulador=this.memoriaSimpletrom[enderecoInt];
                return CaseEnum.DEUCERTO;
            }
            
            if(cadeia[1]=='1') {
                
                //copia doacumulador para o endereço beta
                this.memoriaSimpletrom[enderecoInt]=this.acumulador;
                return CaseEnum.DEUCERTO;
            } else {
                return CaseEnum.SINTAXERRO;
                //comando inválido
                
            }
        }
        

        if(cadeia[0]=='3') {
            
            endereco=""+cadeia[2]+cadeia[3];
            enderecoInt=Integer.parseInt(endereco);
            int valorMemoria=Integer.parseInt(this.memoriaSimpletrom[enderecoInt]);
            int valorAcumulador=Integer.parseInt(this.acumulador);
            
            if(cadeia[1]=='0') {
                
                //soma o valor da memória ao acumulador
                this.acumulador=String.valueOf((valorMemoria+valorAcumulador));
                while(this.acumulador.length()<4) {
                    this.acumulador="0"+this.getAcumulador();
                }
                return CaseEnum.DEUCERTO;
            }
            
            if(cadeia[1]=='1') {
                
                //subtrai o valor da memória do valor do acumulador
                this.acumulador=String.valueOf((valorMemoria-valorAcumulador));
                return CaseEnum.DEUCERTO;
            } 
            
            if(cadeia[1]=='2') {
             
                //divide o valor do acumulador pelo valor da memória 
                this.acumulador=String.valueOf((valorAcumulador/valorMemoria));
                return CaseEnum.DEUCERTO;
            }
            
            if(cadeia[1]=='3') {
                
                //multiplica o valor do acumulador pelo valor da memória
                this.acumulador=String.valueOf((valorAcumulador*valorMemoria));
                return CaseEnum.DEUCERTO;
            }else {
                
                return CaseEnum.SINTAXERRO;
                //comando inválido
            }
        }
        
        if(cadeia[0]=='4') {
            
            endereco=""+cadeia[2]+cadeia[3];
            enderecoInt=Integer.parseInt(endereco);
            if(cadeia[1]=='0') {
                
                this.contadorInstrucoes=enderecoInt-1;
                return CaseEnum.DEUCERTO;
            }
            
            if(cadeia[1]=='1') {
              
                //JMP se negativo endereço específico
                int valorAcumulador=Integer.parseInt(this.acumulador);
                if(valorAcumulador<0) {
                    
                    this.contadorInstrucoes=enderecoInt-1;
                }
                return CaseEnum.DEUCERTO;
            } 
    
            if(cadeia[1]=='2') {
             
                //JMP if acumulador==0
                int valorAcumulador=Integer.parseInt(this.acumulador);
                if(valorAcumulador==0) {
                    
                    this.contadorInstrucoes=enderecoInt-1;
                }
                return CaseEnum.DEUCERTO;
            }
            
            if(cadeia[1]=='3') {
                return CaseEnum.TERMINOU;
                //fim de programa
                
            }else {
                
                return CaseEnum.SINTAXERRO;
                //comando inválido
            }
        }
        return CaseEnum.SINTAXERRO;
    }
    
    public void setContadorInstrucoes(int contadorInstrucoes) {
        this.contadorInstrucoes=contadorInstrucoes;
    }
     
    public void setTexto(String texto) {
        this.texto=texto;
    }
    
    public String getText() {
        return this.texto;
    }    
    
    public void setAcumulador(String acumulador) {
        this.acumulador=acumulador;
    }
    
    public String getAcumulador() {
        return this.acumulador;
    }
    
    public void setRegistradorInstrucoes(String RI) {
        this.registradorInstrucoes=RI;
    }
    
    public String getRegistradorInstrucoes() {
        return this.registradorInstrucoes;
    }
    
    public void setInput(boolean bool) {
        this.input=bool;
    }
    
    public boolean getInput() {
        return this.input;
    }
        
    public void setMemoriaSimpletrom(String[] memoria) {
        this.memoriaSimpletrom=memoria;
    }
    
    public String[] getMemoriaSimpletrom() {
        return this.memoriaSimpletrom;
    }

    public void setExibir(boolean exibir) {
        this.exibir=exibir;
    }
    
    public boolean getExibir() {
        return this.exibir;
    }
    
    public int getContadorDeInstrucoes() {
        return this.contadorInstrucoes;
    }
    
    public int getCi() {
        return this.ci;
        
    }
    }
    

