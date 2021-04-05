package com.proyecto.gemcrush;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

public class Dialog_Agradecimiento {
    public static void dialog_Agradecimiento(Context contexto){
        final Dialog dialogo = new Dialog(contexto) {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                // Tap anywhere to close dialog.
                this.dismiss();
                return true;
            }
        };
        dialogo.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogo.setCancelable(true);
        dialogo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogo.setContentView(R.layout.dialog_agradecimientos);
        dialogo.show();
    }
}
