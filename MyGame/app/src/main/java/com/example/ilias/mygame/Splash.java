package com.example.ilias.mygame;

import com.example.ilias.classesDB.*;
import com.example.ilias.database.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ilias on 30/04/2015.
 */
public class Splash extends Activity {
    GestionDB gestion;
    Settings seting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        gestion = new GestionDB(this);
        gestion.open();
        seting = gestion.getSettings();
        Thread chrono = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent i;
                    if(seting.getReplay_code() == 0)
                        i = new Intent("com.example.ilias.mygame.CATEGORIE");
                    else
                        i = new Intent("com.example.ilias.mygame.DEBUT");
                    startActivity(i);

                }
            }

        };
        chrono.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
