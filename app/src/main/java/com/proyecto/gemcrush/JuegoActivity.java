package com.proyecto.gemcrush;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JuegoActivity extends AppCompatActivity {

    int[] gemas = {
            R.drawable.diamante,
            R.drawable.citrina,
            R.drawable.esmeralda,
            R.drawable.ruby
    };

    int anchuraDelBloque, numDeBloques = 8, anchuraDeLaPantalla;
    ArrayList<ImageView> gema = new ArrayList<>();
    int gemaAMover, gemaAReemplazar;
    int noGema = R.drawable.ic_launcher_background;
    Handler mHandler;
    int intervalo = 100;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        //Se recogen las medidas de la pantalla y se calcula la anchura de cada bloque (gema)
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        anchuraDeLaPantalla = displayMetrics.widthPixels;
        int alturaDeLaPantalla = displayMetrics.heightPixels;
        anchuraDelBloque = anchuraDeLaPantalla / numDeBloques;

        crearTablero();

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

                    intercambioDeGemas();                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();

                    gemaAMover = imageView.getId();
                    gemaAReemplazar = gemaAMover + numDeBloques;

                    intercambioDeGemas();                  }
            });
        }
        mHandler = new Handler();
        empezarRepeticion();
    }

    private void comprobarLineaDeTres() {
        for (int i = 0; i < 62; i++) {
            int gemaElegida = (int) gema.get(i).getTag();
            boolean esBlanco = (int) gema.get(i).getTag() == noGema;
            Integer[] noValido = {6, 7, 14, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55};
            List<Integer> lista = Arrays.asList(noValido);
            if (!lista.contains(i)) {
                int x = i;
                if ((int) gema.get(x++).getTag() == gemaElegida && esBlanco &&
                        (int) gema.get(x++).getTag() == gemaElegida &&
                        (int) gema.get(x).getTag() == gemaElegida)
                {
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
    }

    Runnable repetirComprobacion = new Runnable() {
        @Override
        public void run() {
            try {
                comprobarLineaDeTres();
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

}