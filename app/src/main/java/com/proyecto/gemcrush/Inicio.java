package com.proyecto.gemcrush;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Inicio extends AppCompatActivity {

    TextView tvVidas, tvTiempo;
    Handler handler = new Handler();
    int diferencia_segundos, diferencia_en_minutos, diferencia_en_horas,vidas;
    boolean vidaPerdida, primeraVez;
    Runnable runnable;
    Date fechaPerdidaVida = null;
    Date fechaActual;
    String vidaPerdidaHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Button btnAgradecimientos = findViewById(R.id.btnAgradecimientos);
        Button btnIdiomas = findViewById(R.id.btnIdiomas);
        Button btnJuegos = findViewById(R.id.btnJuego);
        tvVidas = findViewById(R.id.tvVida);
        tvTiempo = findViewById(R.id.tvTiempo);
        Context context = this;
        btnAgradecimientos.setOnClickListener(v -> Dialog_Agradecimiento.dialog_Agradecimiento(context));
        Intent intent = new Intent(Inicio.this, MusicaFondo.class);
        startService(intent);
        gestionarVidas();
        btnIdiomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Idioma_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnJuegos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, JuegoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void gestionarVidas(){

    }


}
