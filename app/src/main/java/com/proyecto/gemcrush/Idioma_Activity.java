package com.proyecto.gemcrush;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Idioma_Activity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idioma);
        prefs =  this.getSharedPreferences("GEMCRUSH", Context.MODE_PRIVATE);
        editor= prefs.edit();

        Button btnMenu = findViewById(R.id.btnMenu);
        Button btnAudio = findViewById(R.id.btnSonido);
        if (prefs.getBoolean("mute?",false)) {
            btnAudio.setText(R.string.desactivar_audio);
            btnAudio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_volume_off_24, 0, 0, 0);
        }else{
            btnAudio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_volume_up_24, 0, 0, 0);
            btnAudio.setText(R.string.activar_audio);
        }
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Idioma_Activity.this, Inicio.class);
                startActivity(intent);
                stopService(new Intent(Idioma_Activity.this, MusicaFondo.class));
                finish();
            }
        });
        btnAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prefs.getBoolean("mute?",false)) {
                    startService(new Intent(Idioma_Activity.this, MusicaFondo.class));
                    btnAudio.setText(R.string.desactivar_audio);
                    btnAudio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_volume_off_24, 0, 0, 0);
                    editor.putBoolean("mute?",false);
                }else{
                    stopService(new Intent(Idioma_Activity.this, MusicaFondo.class));
                    btnAudio.setText(R.string.activar_audio);
                    btnAudio.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_baseline_volume_up_24, 0, 0, 0);
                    editor.putBoolean("mute?",true);
                }
                editor.apply();

            }
        });
    }

}