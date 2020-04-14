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
        Button bt = findViewById(R.id.button);
        tv= findViewById(R.id.textView);
        bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               PrecisaCalcular shc = new PrecisaCalcular(tv);
               //shc.calculoRemoto();
                //shc.calculoLocal()
                shc.calculoRemotoHTTP();

            }

        });
    }
}
