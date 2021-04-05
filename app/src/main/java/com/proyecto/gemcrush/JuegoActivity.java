package com.proyecto.gemcrush;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

public class JuegoActivity extends AppCompatActivity {

    int[] gemas = {
            R.drawable.diamante,
            R.drawable.citrina,
            R.drawable.esmeralda,
            R.drawable.ruby
    };

    int anchuraDelBloque, numDeBloques = 8, anchuraDeLaPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        anchuraDeLaPantalla = displayMetrics.widthPixels;
        int alturaDeLaPantalla = displayMetrics.heightPixels;
        anchuraDelBloque = anchuraDeLaPantalla / numDeBloques;
        crearTablero();

    }

    private void crearTablero() {
        GridLayout gridLayout = findViewById(R.id.gridTablero);
        gridLayout.setRowCount(numDeBloques);
        gridLayout.setColumnCount(numDeBloques);
        gridLayout.getLayoutParams().width = anchuraDeLaPantalla;
        gridLayout.getLayoutParams().height = anchuraDeLaPantalla;

        //Generar aleatoriamente las gemas
        for (int i = 0; i < numDeBloques * numDeBloques; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(anchuraDelBloque, anchuraDelBloque));
            imageView.setMaxWidth(anchuraDelBloque);
            imageView.setMaxHeight(anchuraDelBloque);

            int gemaAleatoria = (int) Math.floor(Math.random() * gemas.length);
            imageView.setImageResource(gemas[gemaAleatoria]);
            gridLayout.addView(imageView);

        }

    }

}