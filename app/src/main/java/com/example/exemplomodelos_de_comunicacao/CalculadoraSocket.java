package com.example.exemplomodelos_de_comunicacao;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
/**
 * Cliente para o serviço de calculadora disponibilizado via sockets.
 * Estende uma AsyncTask, responsável por realizar uma tarefa em segundo plano, cujo resultado é publicado na thread da interface
 * Tal artíficio é utilizado para que a interface não fique bloqueada, enquanto a operação é realizada
 */
public class CalculadoraSocket extends AsyncTask<Void, Void, String> {
    //Cria os objetos necessários
    //tv é utilizado para exibir texto na tela do app
    TextView tv;
    //Strings representando os dois operadores e a operação da calculadora (1-soma;2-subtração;3-multiplicação;4-divisão)
    String oper1,oper2,op;
    //Objeto responsável por instanciar e executar a CalculadoraSocket
    PrecisaCalcular pc;
    //Construtores tanto para o caso de instanciar o objeto com uma TextView, quanto para o caso de instanciar com um objeto PrecisaCalcular
    //Ao usar a TextView, no próprio onPostExecute escreve o resultado da operação na tela do usuário
    //Ao usar o objeto PrecisaCalcular, passa para ele o resultado e ele que ficará responsável por imprimí-lo na tela do usuário
    public CalculadoraSocket(TextView tv, String oper1, String oper2, String op){
        this.tv=tv;
        this.oper1=oper1;
        this.oper2=oper2;
        this.op=op;

    }
    public CalculadoraSocket(PrecisaCalcular pc, String oper1, String oper2, String op){
        this.tv=tv;
        this.oper1=oper1;
        this.oper2=oper2;
        this.pc=pc;
        this.op=op;

    }
    //Ação propriamente dita que será realizada assincronamente, não travando a tela do usuário enquanto executa
    //Seu retorno é passado por parâmetro para o método onPostExecute
    @Override
    protected String doInBackground(Void... voids) {
        String result="";

        try {

            //Conexão com o Servidor
            //Vamos criar uma conexão via socket na porta 9090 do servidor que está rodando o CalculadoraServerSocket.java
            Socket clientSocket = new Socket("192.168.1.65", 9090);
            /*
             * O envio dos dados para o servidor será feito através de um outputStream do
             * socket. Para isso, será utilizada uma instância da classe concreta
             * DataOutputStream
             */
            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());

            //Enviando os dados
            /*
             * A stream enviada será composta por operador, operando 1 e operando 2, nesta
             * ordem. Após cada um deles, será incluída uma quebra de linha ("\n"), afim de
             * delimitar os dados.
             */
            socketSaidaServer.writeBytes(op+"\n");
            socketSaidaServer.writeBytes(oper1+ "\n");
            socketSaidaServer.writeBytes( oper2+ "\n");
            /*
             * descarrega o buffer na stream, se ainda houver, que será então encaminhado ao
             * servidor.
             */
            socketSaidaServer.flush();

            //Recebendo a resposta
            /*
             * O recebimento do resultado do servidor será feito através de um InputStream
             * do socket. A stream recebida é formada pelo resultado seguido de uma quebra
             * de linha. Como o resultado encontra-se delimitado por quebra de linha ("\n"),
             * é conveniente instanciar um BufferedReader a partir da InputStream do socket
             * para, logo em seguida, utilizar o método readLine para obtê-lo.
             */
            BufferedReader messageFromServer = new BufferedReader
                    (new InputStreamReader(clientSocket.getInputStream()));
            result=messageFromServer.readLine();

            // Fecha o socket.
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Retorna o resultado enviado pelo servidor que será recebido como parâmetro no método onPostExecute
        return result;
        //Codigo
    }

        /* Este método é executado antes da Thread ser iniciada.
         * Não é obrigatório
         * Executa na mesma thread da interface gráfica
         */
    @Override
protected void onPreExecute() {
        //Codigo
        }

    /* Este método é quem recebe o retorno do doInBackground
     * Executa na mesma thread da interface gráfica
     */
@Override
protected void onPostExecute(String result) {
    /* Se o TextView não for nulo, seta-o com o resultado
     * Caso contrário, chama o método do objeto PrecisaCalcular, que foi quem chamou essa classe, a fim de passar o resultado da operação, para que ela imprima o resultado na tela
     */
           if(this.tv!=null) {
               this.tv.setText(result);
           }else {
               this.pc.result_calculoRemoto(result);
           }
        }

}
