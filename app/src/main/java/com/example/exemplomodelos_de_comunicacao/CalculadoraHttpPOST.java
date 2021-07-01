package com.example.exemplomodelos_de_comunicacao;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
/**
 * Cliente HTTP para exemplificar o modelo requisição-resposta
 * Estende uma AsyncTask, responsável por realizar uma tarefa em segundo plano, cujo resultado é publicado na thread da interface
 * Tal artíficio é utilizado para que a interface não fique bloqueada enquanto a operação é realizada
 */
public class CalculadoraHttpPOST extends AsyncTask<Void, Void, String> {
    //Cria os objetos necessários
    //tv é utilizado para exibir texto na tela do app
    TextView tv;
    //Strings representando os dois operadores e a operação da calculadora (1-soma;2-subtração;3-multiplicação;4-divisão)
    String oper1,oper2,op;
    //Objeto responsável por instanciar e executar a CalculadoraHttpPOST
    PrecisaCalcular pc;
    //Construtores tanto para o caso de instanciar o objeto com uma TextView, quanto para o caso de instanciar com um objeto PrecisaCalcular
    //Ao usar a TextView, no próprio onPostExecute escreve o resultado da operação na tela do usuário
    //Ao usar o objeto PrecisaCalcular, passa para ele o resultado e ele que ficará responsável por imprimí-lo na tela do usuário
    public CalculadoraHttpPOST(TextView tv, String oper1, String oper2, String op){
        this.tv=tv;
        this.oper1=oper1;
        this.oper2=oper2;
        this.op=op;

    }
    public CalculadoraHttpPOST(PrecisaCalcular pc, String oper1, String oper2, String op){
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
            //Cria um objeto URL a partir de uma string (endereço do servidor PHP)
           URL url = new URL("https://double-nirvana-273602.appspot.com/?hl=pt-BR");
            //Cria uma instância de uma conexão que representa o objeto remoto referenciado pela URL
           HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            //Seta o máximo de tempo (em ms) de espera pela resposta do servidor, após isso lança SocketTimeoutException
            conn.setReadTimeout(10000);
            //Seta o máximo de tempo (em ms) de espera pelo estabelecimento da conexão com o servidor, após isso lança SocketTimeoutException
            conn.setConnectTimeout(15000);
            //Seta o método para POST
            conn.setRequestMethod("POST");
            //Seta que a conexão será usada tanto para input quanto output
            conn.setDoInput(true);
            conn.setDoOutput(true) ;

            //ENVIO DOS PARAMETROS
            //Cria um objeto OutputStream para a conexão
            OutputStream os = conn.getOutputStream();
            //Cria um objeto BufferedWriter a partir do output da conexão para escrever os parâmetros da requisição
            //Criado passando o outputstream da conexão e o charset suportado
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            //Escreve os parâmetros no objeto writer. No caso, o operador 1, o operador 2 e a operação da requisição, conforme implementado e esperado pelo servidor remoto PHP
            writer.write("oper1="+oper1+"&oper2="+oper2+"&operacao="+op);
            //Limpa o objeto writer para depois fechá-lo
            writer.flush();
            writer.close();
            //Fecha o outputstream da conexão
            os.close();
            //Retorna o status de resposta da requisição
            int responseCode=conn.getResponseCode();
            //Se a resposta da requisição foi sucesso
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                //RECEBIMENTO DOS PARAMETROS

                //Cria um objeto para ler a resposta dada pelo servidor, a partir do inputstream da conexão e indicando o charset suportado
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                //Lê resposta e adiciona no objeto response
                //O método readLine() lê uma linha até encontrar o caractere '\n'
                //Quando chegar ao final e a linha estiver nula, já foram lidas todas as linhas da resposta
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                //Converte o valor do StringBuilder em uma string para ser retornada
                result = response.toString();
            }
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

