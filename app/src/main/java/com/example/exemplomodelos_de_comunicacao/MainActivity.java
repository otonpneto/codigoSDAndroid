package com.example.exemplomodelos_de_comunicacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bt_som = findViewById(R.id.button_som);
        Button bt_sub = findViewById(R.id.button_sub);
        Button bt_mul = findViewById(R.id.button_mul);
        Button bt_div = findViewById(R.id.button_div);
        tv= findViewById(R.id.textView);
        bt_som.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               PrecisaCalcular shc = new PrecisaCalcular(tv);
               shc.calculoRemoto("1");
                //shc.calculoRemotoHTTP("1");

            }

        });
        bt_sub.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tv);
                shc.calculoRemoto("2");
                //shc.calculoRemotoHTTP("2");

            }

        });
        bt_mul.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tv);
                shc.calculoRemoto("3");
                //shc.calculoRemotoHTTP("3");

            }

        });
        bt_div.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                PrecisaCalcular shc = new PrecisaCalcular(tv);
                shc.calculoRemoto("4");
                //shc.calculoRemotoHTTP("4");

            }

        });
    }
}
