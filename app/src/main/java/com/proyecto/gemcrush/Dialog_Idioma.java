package com.proyecto.gemcrush;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class Dialog_Idioma extends Dialog{
    Activity mActivity;
    SharedPreferences prefs;

    public Dialog_Idioma(Activity activity, Context context) {
        super(activity);
        mActivity = activity;
        setContentView(R.layout.dialog_idioma);
        Button btnCastellano =findViewById(R.id.btn_Castellano);
        Button btnEuskera = findViewById(R.id.btn_Euskera);
        btnCastellano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("es");
                Locale.setDefault(locale);
                Resources resources = context.getResources();
                Configuration config = resources.getConfiguration();
                config.setLocale(locale);
                resources.updateConfiguration(config, resources.getDisplayMetrics());
                Intent intent = mActivity.getIntent();
                dismiss();
                mActivity.finish();
                mActivity.startActivity(intent);

            }
        });
        btnEuskera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Locale locale = new Locale("eu");
                Locale.setDefault(locale);
                Resources resources = context.getResources();
                Configuration config = resources.getConfiguration();
                config.setLocale(locale);
                resources.updateConfiguration(config, resources.getDisplayMetrics());
                Intent intent = mActivity.getIntent();
                dismiss();
                mActivity.finish();
                mActivity.startActivity(intent);

            }
        });
    }


}
