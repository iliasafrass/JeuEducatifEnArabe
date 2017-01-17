package com.example.ilias.mygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.ilias.classesDB.*;
import com.example.ilias.database.*;

/**
 * Created by hamza on 22/05/15.
 */
public class Debut extends Activity {

    GestionDB gestion;
    Settings seting;
    Button btnCont,btnAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debut);
        btnCont = (Button)findViewById(R.id.continu);
        btnAcc = (Button)findViewById(R.id.debut);
        btnCont.setOnClickListener(listener);
        btnAcc.setOnClickListener(listener);
        gestion = new GestionDB(this);
        gestion.open();
        seting = gestion.getSettings();
    }

    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i;
           if(v.getId() == R.id.continu)
                i = new Intent("com.example.ilias.mygame.MAINACTIVITY");

           else {
               seting.setEtat_cursor(0);
               seting.setScore_etat(0);
               seting.setReplay_code(0);
               gestion.insertSettings(seting);
               gestion.close();
               i = new Intent("com.example.ilias.mygame.CATEGORIE");
           }
            startActivity(i);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
