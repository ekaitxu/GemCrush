package com.proyecto.gemcrush;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

public class Dialog_Rendirse extends Dialog{
    Activity mActivity;
    SharedPreferences prefs;

    public Dialog_Rendirse(Activity activity) {
        super(activity);
        mActivity = activity;
        setContentView(R.layout.dialog_rendirse);
        Button btnSi =findViewById(R.id.btn_Si);
        Button btnNo = findViewById(R.id.btn_No);
        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs =  mActivity.getSharedPreferences("GEMCRUSH", Context.MODE_PRIVATE);
                prefs.edit().putInt("vidas",prefs.getInt("vidas",0)-1).apply();
                Intent intent = new Intent(mActivity, Inicio.class);
                mActivity.startActivity(intent);
                dismiss();
                mActivity.finish();

            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /*public static void dialog_Rendirse(Context contexto){
        final Dialog dialogo = new Dialog(contexto);
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_rendirse);
        dialogo.show();



    }*/

}
