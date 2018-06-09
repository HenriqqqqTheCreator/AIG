package zapzap.tccetec.com.aig.licao;

public class Quiz {

    private String[] perguntasIniciante = {//MERCADO DE CAPITAIS
            "O mercado de capitais é dividido em que ativos principais?",
    "O mercado de capitais é dividido em quantas partes?",
    "O que é o mercado de capitais?",

    //COMO INVESTIR EM AÇÕES
            "Qual o conceito de investimento?",
            "O que são ações?",
            "A comprar ações o que você se torna",
            "Quantos mais ações você ter em uma empresa:",
            "O que é uma corretora?",
            "Para investir na bolsa você precisa ter",
            "Quais os benefícios que podem acontecer ao investir",
            "Quais os riscos que podem acontecer ao investir?"
    };


    public String getPerguntasIniciante(int x){

        return perguntasIniciante[x];

    }

    public int getPerguntasLenght(){

        int p = perguntasIniciante.length;

        return p;
    }

    private static String[][] respostasIniciante = new String[11][4];

    public String getRespostasIniciante(int x, int y){

        respostasIniciante[0][0] = "Ação, Commercial Papers e Debêndure"; //CERTA
        respostasIniciante[0][1] = "Mercado, ação e investimento";
        respostasIniciante[0][2] = "Commercial Papers, Debêndure e mercado";
        respostasIniciante[0][3] = "Investimento e ações";

        respostasIniciante[1][0] = "É dividida em penas uma";
        respostasIniciante[1][1] = "É dividida em duas";//CERTA
        respostasIniciante[1][2] = "É dividida em três";
        respostasIniciante[1][3] = "É dividida em sete";

        respostasIniciante[2][0] = "O mercado de capitais é um órgão publico que tem o dever de comprar ações";
        respostasIniciante[2][1] = "O mercado de capitais é um mercado que realiza venda de ações";
        respostasIniciante[2][2] = "O mercado de capitais é um mecanismo de distribuição de valores mobiliários, que tem o objetivo de " +
                "gerar liquidez aos títulos emitidos pelas empresas e viabilizar o seu processo de capitalização";//CERTA
        respostasIniciante[2][3] = "O mercado de ações realiza investimentos de matéria prima para as empresas";

        respostasIniciante[3][0] = "Qualquer desembolso que produza expectativas de ganho futuro";//CERTA
        respostasIniciante[3][1] = "Estudos de pesquisa de crescimento de valores";
        respostasIniciante[3][2] = "Ganhar dinheiro";
        respostasIniciante[3][3] = "Fazer o dinheiro trabalhar";

        return respostasIniciante[x][y];
    }

    private int[] respostasCertas = new int[4];

    public boolean getRespostasCertas(int numeroDaPergunta,int respostaSelecionada){

        respostasCertas[0] = 0;
        respostasCertas[1] = 1;
        respostasCertas[2] = 2;
        respostasCertas[3] = 0;

        if(respostaSelecionada == respostasCertas[numeroDaPergunta]){

            return true;

        }else{

            return false;

        }
    }

}
