package com.proyecto.gemcrush;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Idioma_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idioma);
        Button btnMenu = findViewById(R.id.btnMenu);
        Button btnAudio = findViewById(R.id.btnSonido);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Idioma_Activity.this, Inicio.class);
                startActivity(intent);
                finish();
            }
        });
        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnAudio.getText().toString().equals(getResources().getString(R.string.activar_audio))) {
                    startService(new Intent(Idioma_Activity.this, MusicaFondo.class));
                    btnAudio.setText(R.string.desactivar_audio);
                    btnAudio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_volume_off_24, 0, 0, 0);
                }else{
                    stopService(new Intent(Idioma_Activity.this, MusicaFondo.class));
                    btnAudio.setText(R.string.activar_audio);
                    btnAudio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_volume_up_24, 0, 0, 0);
                }

            }
        });
    }

}