package com.proyecto.gemcrush;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Inicio extends AppCompatActivity {
    public int vidas;
    public boolean isRinging;
    TextView tvVidas, tvTiempo;
    int diferencia_segundos, diferencia_en_minutos, diferencia_en_horas;
    Date lostTime, currentTime;
    SharedPreferences prefs;
    long lostMillis, currentMillis;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        prefs =  this.getSharedPreferences("GEMCRUSH", Context.MODE_PRIVATE);
        Button btnAgradecimientos = findViewById(R.id.btnAgradecimientos);
        Button btnIdiomas = findViewById(R.id.btnIdiomas);
        Button btnJuegos = findViewById(R.id.btnJuego);
        tvVidas = findViewById(R.id.tvVida);
        tvTiempo = findViewById(R.id.tvTiempo);
        Context context = this;
        btnAgradecimientos.setOnClickListener(v -> Dialog_Agradecimiento.dialog_Agradecimiento(context));
        vidas = prefs.getInt("vidas", 0);
        vidas = Integer.parseInt(tvVidas.getText().toString());
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                //TODO your background code
                gestionarVidas();
            }
        });
        btnIdiomas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Idioma dialog = new Dialog_Idioma(Inicio.this, getBaseContext());
                dialog.show();
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
        while(vidas<3){
            lostMillis = prefs.getLong("losttime",-1);
            if (lostMillis==-1) {
                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(new Date());               // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, 2);      // adds one hour
                lostTime = cal.getTime();
                lostMillis = lostTime.getTime();
                prefs.edit().putLong("losttime", lostMillis).apply();
            }
            currentTime = Calendar.getInstance().getTime();
            currentMillis = currentTime.getTime();
            long diferencia = lostMillis - currentMillis;
            Date diferenciaDate = new Date();
            diferenciaDate.setTime(diferencia);
            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss", Locale.getDefault());
            String strDate = dateFormat.format(diferenciaDate);
            if (diferencia<0){
                vidas=vidas+1;
                prefs.edit().putInt("vidas",vidas+1).apply();
                tvVidas.setText(Integer.toString(vidas));
                Calendar cal = Calendar.getInstance(); // creates calendar
                cal.setTime(new Date());               // sets calendar time/date
                cal.add(Calendar.HOUR_OF_DAY, 2);      // adds one hour
                lostTime = cal.getTime();
                lostMillis = lostTime.getTime();
                prefs.edit().putLong("losttime", lostMillis).apply();

            }
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    // change UI elements here
                    tvTiempo.setText(strDate);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        prefs.edit().putInt("vidas",vidas).apply();
        super.onDestroy();
    }

}
