package com.example.exemplomodelos_de_comunicacao;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class CalculadoraSocket extends AsyncTask<Void, Void, String> {

    TextView tv;
    String oper1,oper2,op;
    PrecisaCalcular pc;
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
    @Override
    protected String doInBackground(Void... voids) {
        String result="";

        try {

            //Conexão com o Servidor
            Socket clientSocket = new Socket("192.168.1.65", 9090);
            DataOutputStream socketSaidaServer = new DataOutputStream(clientSocket.getOutputStream());

            //Enviando os dados
            socketSaidaServer.writeBytes(op+"\n");
            socketSaidaServer.writeBytes(oper1+ "\n");
            socketSaidaServer.writeBytes( oper2+ "\n");
            socketSaidaServer.flush();

            //Recebendo a resposta
            BufferedReader messageFromServer = new BufferedReader
                    (new InputStreamReader(clientSocket.getInputStream()));
            result=messageFromServer.readLine();

          //  System.out.println("resultado="+result);
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        return result;
        //Codigo
    }


    @Override
protected void onPreExecute() {
        //Codigo
        }


@Override
protected void onPostExecute(String result) {
        //Codigo
           if(this.tv!=null) {
               this.tv.setText(result);
           }else {
               this.pc.result_calculoRemoto(result);
           }
        }

}
