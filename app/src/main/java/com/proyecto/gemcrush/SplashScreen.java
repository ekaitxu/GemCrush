package com.proyecto.gemcrush;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        TimerTask animado = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Inicio.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                finish();
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(animado, 4000);
    }
}