package com.example.ilias.mygame;

import com.example.ilias.classesDB.*;
import com.example.ilias.database.*;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private static final int DIALOG_SETTING = 1;
    private static final int DIALOG_OUT = 2;
    GestionDB gestion;
    ImageView image = null;
    TextView incomplet = null;
    Button button1 = null;
    Button button2 = null;
    Button correct;
    TextView next = null;
    TextView resultat;
    Cursor c;
    String picture;
    String mot_incomplet;
    String let1;
    String let2;
    double aleatoire = 0;
    int faux = 0;
    int nblignes = 0;
    int id;
    MediaPlayer son;
    int score = 0;
    int cpt = 0;
    boolean click_false = true;
    boolean click_true = true;
    Settings seting;
    int etat_son,etat_cursor;
    Temporaire temp ;
    int tempId;
    String tempNom,tempTaille,tempIncomplet,tempLettre1,tempLettre2,tempImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreMe(savedInstanceState);



        son = MediaPlayer.create(getApplicationContext(), R.raw.sound6);

        son.setLooping(true);
        gestion = new GestionDB(this);
        gestion.open();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String choixTable = extras.getString("choixTable");
            gestion.setTABLE(choixTable);
        }

        seting = gestion.getSettings();
        etat_son = seting.getSon();
        etat_cursor = seting.getEtat_cursor();
        score = seting.getScore_etat();
        //score_max = seting.getScore_max();
        if(etat_son == 1)
            son.start();

        if(etat_cursor == 0) {
            c = gestion.getDataBase();
            gestion.deleteTemporary();
        }
        else
            c = gestion.getTemporary();
        c.moveToFirst();
        nblignes = c.getCount();
        next = (TextView) findViewById(R.id.suivant);
        resultat = (TextView) findViewById(R.id.rsl);
        incomplet = (TextView) findViewById(R.id.incomplet);
        button1 = (Button) findViewById(R.id.btn1);
        button2 = (Button) findViewById(R.id.btn2);
        image = (ImageView) findViewById(R.id.picture);
        /*if(etat_cursor == 0) {
            c.moveToFirst();
        }
        else{
            c.moveToPosition(etat_cursor);
        }*/
        picture = c.getString(6);
        mot_incomplet = c.getString(3);
        let1 = c.getString(4);
        let2 = c.getString(5);
        id = getResources().getIdentifier(picture, "mipmap", getPackageName());
        image.setImageResource(id);
        incomplet.setText(mot_incomplet);
        aleatoire = Math.random();
        if (aleatoire < 0.5) {
            button1.setText(let1);
            button2.setText(let2);
            correct = button1;
        } else {
            button1.setText(let2);
            button2.setText(let1);
            correct = button2;
        }
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void restoreMe(Bundle state){
        if(state != null){
            onRestoreInstanceState(state);
        }
    }

    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {


            if (v == correct) {
                if(click_true) {
                    next.setText(R.string.next);
                    resultat.setText("إجابة صحيحة");
                    resultat.setTextColor(Color.GREEN);
                    String mot_complet = c.getString(1);
                    incomplet.setText(mot_complet);
                    next.setOnClickListener(nextlistener);
                    click_false = false;
                    click_true = false;
                }


            } else {
                if(click_false) {
                    cpt++;
                    resultat.setText("إجابة خاطئة");
                    resultat.setTextColor(Color.RED);
                    faux++;
                }
                else{
                    resultat.setText(" لقد اخترت الإجابة الصحيحة ");
                    resultat.setTextColor(Color.MAGENTA);
                }
            }


        }
    };
    OnClickListener nextlistener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            click_false = true;
            click_true = true;
            next.setText("");
            if (cpt == 0)
                score += 20;
            else if (cpt == 1)
                score += 10;
            cpt = 0;
            if (c.getPosition() == nblignes - 1) {
                seting.setEtat_cursor(0);
                seting.setScore_etat(0);
                seting.setReplay_code(0);
                /*if(score > score_max){
                    seting.setScore_max(score);
                }*/
                gestion.insertSettings(seting);
                gestion.close();

                Intent i = new Intent("com.example.ilias.mygame.RESULTAT");
                /*i.putExtra("nb_faux", Integer.toString(faux));
                i.putExtra("nb_ligne", Integer.toString(nblignes));*/
                i.putExtra("score", Integer.toString(score));
                //i.putExtra("score_max",Integer.toString(score_max));
                startActivity(i);
            } else {
                resultat.setText(R.string.resultat);
                resultat.setTextColor(Color.BLACK);

                c.moveToNext();
                picture = c.getString(6);
                mot_incomplet = c.getString(3);
                let1 = c.getString(4);
                let2 = c.getString(5);
                id = getResources().getIdentifier(picture, "mipmap", getPackageName());
                image.setImageResource(id);
                incomplet.setText(mot_incomplet);
                aleatoire = Math.random();
                if (aleatoire < 0.5) {
                    button1.setText(let1);
                    button2.setText(let2);
                    correct = button1;
                } else {
                    button1.setText(let2);
                    button2.setText(let1);
                    correct = button2;
                }
                button1.setOnClickListener(listener);
                button2.setOnClickListener(listener);
            }
        }
    };



    @Override
    protected void onPause() {
        son.release();
        super.onPause();
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings)
            showDialog(DIALOG_SETTING);
        else if (id == R.id.action_sortir)
            showDialog(DIALOG_OUT);
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {

            case DIALOG_SETTING:
                builder.setTitle(R.string.settings);
                Switch switchSound = new Switch(this);
                switchSound.setText(R.string.son);
                if(etat_son == 1) {
                    switchSound.setChecked(true);
                }
                switchSound.setOnCheckedChangeListener(ListenerSon);
                builder.setView(switchSound);
                return builder.create();

            case DIALOG_OUT:
                builder.setTitle(R.string.sortir);
                builder.setMessage(R.string.sortir_question);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        seting.setSon(etat_son);
                        seting.setEtat_cursor(c.getPosition());
                        seting.setScore_etat(score);
                        seting.setReplay_code(1);
                        gestion.insertSettings(seting);
                        temp = new Temporaire();
                        if (c.getPosition() < nblignes - 1) {
                            gestion.deleteTemporary();
                            do{
                                temp.setId(c.getInt(0));
                                temp.setNom(c.getString(1));
                                temp.setTaille(c.getString(2));
                                temp.setMot_incomplet(c.getString(3));
                                temp.setLettre1(c.getString(4));
                                temp.setLettre2(c.getString(5));
                                temp.setImage(c.getString(6));
                                gestion.insertTemporary(temp);
                            }while (c.moveToNext());

                        }

                        gestion.close();
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                return builder.create();


        }
        return super.onCreateDialog(id);
    }

    CompoundButton.OnCheckedChangeListener ListenerSon = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                son.start();
                etat_son = 1;
            }
            else {
                son.pause();
                etat_son = 0;
            }
        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode){
            case KeyEvent.KEYCODE_BACK :
                showDialog(DIALOG_OUT);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

