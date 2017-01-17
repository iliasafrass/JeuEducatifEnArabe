package com.example.ilias.mygame;


import com.example.ilias.classesDB.*;
import com.example.ilias.database.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by ilias on 30/04/2015.
 */
public class Resultat extends Activity {
    Button rej;
    Button sor;
    TextView score_jeu;
    TextView textScoreMax;

    private static final int DIALOG_OUT = 1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resultat);
        score_jeu = (TextView)findViewById(R.id.score);
        //textScoreMax = (TextView)findViewById(R.id.score_max);
        rej = (Button) findViewById(R.id.replay);
        sor = (Button) findViewById(R.id.out);
        sor.setOnClickListener(lis2);
        rej.setOnClickListener(lis);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String score = extras.getString("score");
            //String score_max = extras.getString("score_max");
            /*if(Integer.parseInt(score) < Integer.parseInt(score_max)) {
                score_jeu.setText("لقد حصلت على " + score + " نقطة ");
                //textScoreMax.setText(" أحسن نتيجة : " + score_max + " نقطة ");
            }
            else{
                score_jeu.setText(" أحسنت لقد حصلت على أعلى نتيجة : "+score+"");
            }*/
            score_jeu.setText("لقد حصلت على " + score + " نقطة ");

        }



    }

    OnClickListener lis = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent("com.example.ilias.mygame.CATEGORIE");
            startActivity(i);
        }
    };
    OnClickListener lis2 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            /*Intent i = new Intent("com.example.ilias.mygame.CATEGORIE");
            startActivity(i);*/
            showDialog(DIALOG_OUT);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        switch (id) {

            case DIALOG_OUT:
                builder.setTitle(R.string.sortir);
                builder.setMessage(R.string.sortir_question);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.no, null);
                return builder.create();


        }
        return super.onCreateDialog(id);
    }

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
