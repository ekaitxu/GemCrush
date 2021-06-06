package com.proyecto.gemcrush;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class JuegoActivity extends AppCompatActivity implements View.OnClickListener {
    //region Variables
    int[] gemas = {
            R.drawable.diamante,
            R.drawable.citrina,
            R.drawable.esmeralda,
            R.drawable.ruby
    };

    int anchuraDelBloque, numDeBloques = 8, anchuraDeLaPantalla;
    ArrayList<ImageView> gema = new ArrayList<>();
    int gemaAMover, gemaAReemplazar;
    long temporizador;
    int noGema = R.drawable.transparente;
    Handler mHandler;
    int intervalo = 100;
    TextView puntos;
    int puntuacion = 0;
    //endregion
    Button btnRendirse;
    TextView contador;
    ImageButton btnAudio;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    @SuppressLint({"ClickableViewAccessibility", "NonConstantResourceId", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        prefs =  this.getSharedPreferences("GEMCRUSH", Context.MODE_PRIVATE);
        editor= prefs.edit();
        btnAudio = findViewById(R.id.btnAudio);
        btnRendirse = findViewById(R.id.btnRendirse);
        btnAudio.setOnClickListener(this);
        btnRendirse.setOnClickListener(this);
        if (prefs.getBoolean("mute?",false)) {
            startService(new Intent(JuegoActivity.this, MusicaFondo.class));
            btnAudio.setImageDrawable( ContextCompat.getDrawable(this, R.drawable.ic_baseline_volume_up_24));
            btnAudio.setTag("R.drawable.ic_baseline_volume_up_24");
        }else{
            btnAudio.setImageDrawable( ContextCompat.getDrawable(this, R.drawable.ic_baseline_volume_off_24));
            btnAudio.setTag("R.drawable.ic_baseline_volume_off_24");
        }
        //region Asignar variables
        puntos = findViewById(R.id.puntos);
        contador = findViewById(R.id.tvContador);
        temporizador = Integer.parseInt(contador.getText().toString())*1000;
        //Se recogen las medidas de la pantalla y se calcula la anchura de cada bloque (gema)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        anchuraDeLaPantalla = displayMetrics.widthPixels;
        int alturaDeLaPantalla = displayMetrics.heightPixels;
        anchuraDelBloque = anchuraDeLaPantalla / numDeBloques;
        //endregion

        crearTablero();



                new CountDownTimer(temporizador, 1000) {
                    @SuppressLint("SetTextI18n")
                    public void onTick(long millisUntilFinished) {
                        int restante= ((int)millisUntilFinished)/1000;
                        contador.setText(Integer.toString(restante));
                    }

                    public void onFinish() {
                        GifImageView game_over = findViewById(R.id.gifGameOver);
                        GridLayout tablero = findViewById(R.id.gridTablero);
                        game_over.setVisibility(View.VISIBLE);
                        tablero.setVisibility(View.GONE);
                        contador.setVisibility(View.INVISIBLE);
                        btnRendirse.setText(getResources().getString(R.string.salir));
                    }
                }.start();

        //region Mover Gema
        /*Listener para controlar las acciones cuando una "gema" se mueva en x dirección, esto
        * se controla mediante la clase "OnSwipeListener"*/
        for (ImageView imageView : gema) {
            imageView.setOnTouchListener(new OnSwipeListener(this) {
                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();

                    gemaAMover = imageView.getId();
                    gemaAReemplazar = gemaAMover - 1;

                    intercambioDeGemas();
                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();

                    gemaAMover = imageView.getId();
                    gemaAReemplazar = gemaAMover + 1;

                    intercambioDeGemas();
                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();

                    gemaAMover = imageView.getId();
                    gemaAReemplazar = gemaAMover - numDeBloques;

                    intercambioDeGemas();
                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();

                    gemaAMover = imageView.getId();
                    gemaAReemplazar = gemaAMover + numDeBloques;

                    intercambioDeGemas();
                }
            });
        }
        //endregion

        mHandler = new Handler();
        empezarRepeticion();

    }

    //Función para comprobar si hay 3 gemas iguales en una línea
    private void comprobarTresEnRaya() {
        for (int i = 0; i < 62; i++) {
            int gemaElegida = (int) gema.get(i).getTag();
            boolean esBlanco = (int) gema.get(i).getTag() == noGema;

            /*
            * Serie de index que representan la penúltima y última columna de cada fila, ya que
            * no llegarían a ser 3
            */
            Integer[] noValido = {6, 7, 14, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55};
            List<Integer> lista = Arrays.asList(noValido);
            if (!lista.contains(i)) {
                int x = i;
                if ((int) gema.get(x++).getTag() == gemaElegida && !esBlanco &&
                        (int) gema.get(x++).getTag() == gemaElegida &&
                        (int) gema.get(x).getTag() == gemaElegida)
                {
                    puntuacion = puntuacion + 3;
                    puntos.setText(String.valueOf(puntuacion));
                    //3 veces porque son 3 gemas por línea las que queremos
                    gema.get(x).setImageResource(noGema);
                    gema.get(x).setTag(noGema);
                    x--;
                    gema.get(x).setImageResource(noGema);
                    gema.get(x).setTag(noGema);
                    x--;
                    gema.get(x).setImageResource(noGema);
                    gema.get(x).setTag(noGema);
                }
            }
        }
        moverGemaAbajo();
    }

    //Función para comprobar si hay 3 gemas iguales en una columna
    private void comprobarTresEnColumna() {
        for (int i = 0; i < 47; i++) {
            int gemaElegida = (int) gema.get(i).getTag();
            boolean esBlanco = (int) gema.get(i).getTag() == noGema;

            int x = i;
            if ((int) gema.get(x).getTag() == gemaElegida && !esBlanco &&
                    (int) gema.get(x + numDeBloques).getTag() == gemaElegida &&
                    (int) gema.get(x + 2 * numDeBloques).getTag() == gemaElegida)
            {
                puntuacion = puntuacion + 3;
                puntos.setText(String.valueOf(puntuacion));
                //3 veces porque son 3 gemas por columna las que queremos
                gema.get(x).setImageResource(noGema);
                gema.get(x).setTag(noGema);
                x = x + numDeBloques;
                gema.get(x).setImageResource(noGema);
                gema.get(x).setTag(noGema);
                x = x + numDeBloques;
                gema.get(x).setImageResource(noGema);
                gema.get(x).setTag(noGema);
            }
        }
        moverGemaAbajo();
    }

    //Función para mover las gemas hacia abajo cuando completas algún trío
    private void moverGemaAbajo() {
        Integer[] primeraLinea = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Integer> lista = Arrays.asList(primeraLinea);

        for (int i = 55; i >= 0; i--) {
            if ((int) gema.get(i + numDeBloques).getTag() == noGema) {
                gema.get(i + numDeBloques).setImageResource((int) gema.get(i).getTag());
                gema.get(i + numDeBloques).setTag(gema.get(i).getTag());
                gema.get(i).setImageResource(noGema);
                gema.get(i).setTag(noGema);

                if (lista.contains(i) && (int) gema.get(i).getTag() == noGema) {
                    int gemaAleatoria = (int) Math.floor(Math.random() * gemas.length);
                    gema.get(i).setImageResource(gemas[gemaAleatoria]);
                    gema.get(i).setTag(gemas[gemaAleatoria]);
                }
            }
        }

        //Bucle para evitar que en la primera fila queden huecos sin gemas
        for (int i = 0; i < 8; i++) {
            if ((int) gema.get(i).getTag() == noGema) {
                int gemaAleatoria = (int) Math.floor(Math.random() * gemas.length);
                gema.get(i).setImageResource(gemas[gemaAleatoria]);
                gema.get(i).setTag(gemas[gemaAleatoria]);
            }
        }
    }

    Runnable repetirComprobacion = new Runnable() {
        @Override
        public void run() {
            try {
                comprobarTresEnRaya();
                comprobarTresEnColumna();
                moverGemaAbajo();
            }
            finally {
                mHandler.postDelayed(repetirComprobacion, intervalo);
            }
        }
    };

    void empezarRepeticion() {
        repetirComprobacion.run();
    }

    private void intercambioDeGemas() {
        int fondo = (int) gema.get(gemaAReemplazar).getTag();
        int fondo1 = (int) gema.get(gemaAMover).getTag();

        gema.get(gemaAMover).setImageResource(fondo);
        gema.get(gemaAReemplazar).setImageResource(fondo1);
        gema.get(gemaAMover).setTag(fondo);
        gema.get(gemaAReemplazar).setTag(fondo1);
    }

    private void crearTablero() {
        /*Se crea el GridLayout utilizando las medidas de la pantalla y la cantidad de
        * bloques (gemas) elegido*/
        GridLayout gridLayout = findViewById(R.id.gridTablero);
        gridLayout.setRowCount(numDeBloques);
        gridLayout.setColumnCount(numDeBloques);
        gridLayout.getLayoutParams().width = anchuraDeLaPantalla;
        gridLayout.getLayoutParams().height = anchuraDeLaPantalla;

        //Generar aleatoriamente las gemas
        for (int i = 0; i < numDeBloques * numDeBloques; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(anchuraDelBloque, anchuraDelBloque));
            imageView.setMaxWidth(anchuraDelBloque);
            imageView.setMaxHeight(anchuraDelBloque);

            int gemaAleatoria = (int) Math.floor(Math.random() * gemas.length);
            imageView.setImageResource(gemas[gemaAleatoria]);
            imageView.setTag(gemas[gemaAleatoria]);
            gema.add(imageView);
            gridLayout.addView(imageView);

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRendirse:
                // do your code
                if (btnRendirse.getText().toString().equals(getString(R.string.rendirse))) {
                    Dialog_Rendirse dialog = new Dialog_Rendirse(this);
                    dialog.show();
                }else{
                    Intent intent = new Intent(JuegoActivity.this, Inicio.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.btnAudio:
                if (btnAudio.getTag().equals("R.drawable.ic_baseline_volume_off_24")) {
                    startService(new Intent(JuegoActivity.this, MusicaFondo.class));
                    btnAudio.setImageDrawable( ContextCompat.getDrawable(JuegoActivity.this, R.drawable.ic_baseline_volume_up_24));
                    btnAudio.setTag("R.drawable.ic_baseline_volume_up_24");
                    editor.putBoolean("mute?",false);
                }else{
                    stopService(new Intent(JuegoActivity.this, MusicaFondo.class));
                    btnAudio.setImageDrawable( ContextCompat.getDrawable(JuegoActivity.this, R.drawable.ic_baseline_volume_off_24));
                    btnAudio.setTag("R.drawable.ic_baseline_volume_off_24");
                    editor.putBoolean("mute?",true);
                }
                editor.apply();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(JuegoActivity.this, MusicaFondo.class));
    }

}