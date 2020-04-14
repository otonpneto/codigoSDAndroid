package com.example.exemplomodelos_de_comunicacao;

import android.widget.TextView;

public class PrecisaCalcular {
    TextView tv;
    public PrecisaCalcular(TextView tv){
        this.tv=tv;
    }
    public String calculoLocal(){
        Calculadora calc = new Calculadora();
        String result= calc.soma(20.0,20.0)+"";
        return result;
    }

    public void calculoRemoto(){
        CalculadoraSocket shs = new CalculadoraSocket(this, "15", "15");
        shs.execute();

    }
    public void calculoRemotoHTTP(){
        CalculadoraHttpPOST shs = new CalculadoraHttpPOST(this, "15", "15");
        shs.execute();

    }
    public void result_calculoRemoto(String result){
        tv.setText(result);
    }

}
