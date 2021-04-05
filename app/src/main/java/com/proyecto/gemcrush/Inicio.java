package com.proyecto.gemcrush;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Inicio extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button btnAgradecimientos = findViewById(R.id.btnAgradecimientos);
        Button btnIdiomas = findViewById(R.id.btnIdiomas);
        Context context = this;
        btnAgradecimientos.setOnClickListener(v -> Dialog_Agradecimiento.dialog_Agradecimiento(context));
        btnIdiomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Idioma_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
