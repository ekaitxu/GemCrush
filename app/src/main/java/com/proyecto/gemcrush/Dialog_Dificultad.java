package com.proyecto.gemcrush;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class Dialog_Dificultad extends Dialog{
    Activity mActivity;
    SharedPreferences prefs;

    public Dialog_Dificultad(Activity activity, Context context) {
        super(activity);
        mActivity = activity;
        setContentView(R.layout.dialog_dificultad);
        Button btnFacil =findViewById(R.id.btn_Facil);
        Button btnNormal = findViewById(R.id.btn_Normal);
        Button btnDificil = findViewById(R.id.btn_Dificil);
        btnFacil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JuegoActivity.class);
                intent.putExtra("puntos", 200);
                dismiss();
                mActivity.finish();
                mActivity.startActivity(intent);
            }
        });
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JuegoActivity.class);
                intent.putExtra("puntos", 300);
                dismiss();
                mActivity.finish();
                mActivity.startActivity(intent);
            }
        });
        btnDificil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JuegoActivity.class);
                intent.putExtra("puntos", 400);
                dismiss();
                mActivity.finish();
                mActivity.startActivity(intent);
            }
        });
    }


}
