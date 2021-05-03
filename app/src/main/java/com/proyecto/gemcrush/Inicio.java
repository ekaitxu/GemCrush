package com.proyecto.gemcrush;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
        tvVidas = findViewById(R.id.tvVida);
        tvTiempo = findViewById(R.id.tvTiempo);
        Context context = this;
        btnAgradecimientos.setOnClickListener(v -> Dialog_Agradecimiento.dialog_Agradecimiento(context));
        gestionarVidas();
        btnIdiomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inicio.this, Idioma_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void gestionarVidas(){
        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(Inicio.this);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        primeraVez = myPreferences.getBoolean("PrimeraVez?", true);
        if (primeraVez) {
            myEditor.putInt("Vidas", 3);
            myEditor.putBoolean("VidaPerdida?", false);
            myEditor.putBoolean("PrimeraVez?", false);
        }
        vidaPerdida = myPreferences.getBoolean("VidaPerdida?", true);
        vidas = myPreferences.getInt("Vidas", 0);
        vidaPerdidaHora = myPreferences.getString("VidaPerdidaHora", "");
        vidaPerdida =true;
        vidas=0;
        if(vidaPerdida){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            vidaPerdidaHora = myPreferences.getString("VidaPerdidaHora", "");
            if (vidaPerdidaHora.equals("")){
                fechaPerdidaVida = Calendar.getInstance(Locale.getDefault()).getTime();
                vidaPerdidaHora = dateFormat.format(fechaPerdidaVida);
            }else {
                try {
                fechaPerdidaVida =  new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault()).parse(vidaPerdidaHora);
                } catch (ParseException e) {
                    e.printStackTrace();

                }
            }


                    fechaActual=Calendar.getInstance(Locale.getDefault()).getTime();
                    long diferencia =Math.abs(fechaPerdidaVida.getTime()-fechaActual.getTime());
                    diferencia_segundos = (int)((diferencia / 1000) % 60);
                    diferencia_en_minutos = (int)((diferencia / (1000 * 60)) % 60);
                    diferencia_en_horas = (int)((diferencia / (1000 * 60 * 60)) % 24);

                    runnable = new Runnable() {
                        public void run() {

                            if(diferencia_segundos>0) {
                                diferencia_segundos = diferencia_segundos - 1;
                            }else{
                                vidas=vidas+1;
                                if (diferencia_en_minutos==0&&diferencia_segundos==0&&diferencia_en_horas==0){
                                    if (vidas==3){
                                        vidaPerdida = false;
                                    }else{
                                        fechaPerdidaVida = Calendar.getInstance(Locale.getDefault()).getTime();
                                    }
                                }else {
                                diferencia_segundos = 59;
                                if(diferencia_en_minutos>0) {
                                    diferencia_en_minutos = diferencia_en_minutos - 1;
                                }else {
                                    diferencia_en_minutos = 59;
                                    if (diferencia_en_horas >= 0) {
                                        diferencia_en_horas = diferencia_en_horas - 1;
                                    }
                                }
                                }
                            }
                            String tiempo = diferencia_en_horas+":"+diferencia_en_minutos+":"+diferencia_segundos;
                            tvTiempo.setText(tiempo);
                            vidaPerdidaHora = tiempo;
                            handler.postDelayed(runnable, 1000);
                        }
                    };

                    Log.e("TAG", String.valueOf(vidas));
                    runnable.run();
        }else{
            handler.removeCallbacks(runnable);
            vidaPerdidaHora = "";
        }
        myEditor.putString("VidaPerdidaHora", vidaPerdidaHora);
        myEditor.putBoolean("VidaPerdida?", vidaPerdida);
        myEditor.putBoolean("PrimeraVez?", false);
        myEditor.putInt("Vidas", vidas);
        myEditor.apply();
    }


}
