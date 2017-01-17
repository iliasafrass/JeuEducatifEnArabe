package com.example.ilias.mygame;

import com.example.ilias.classesDB.*;
import com.example.ilias.database.*;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by hamza on 18/05/15.
 */
public class Categorie extends Activity {

    Button [] buttonChoix ;
    String choixTable;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choix_categorie);
        String logo = "logo";
        btn1 = (Button)findViewById(R.id.btn_choix1);
        btn2 = (Button)findViewById(R.id.btn_choix2);
        btn3 = (Button)findViewById(R.id.btn_choix3);
        btn4 = (Button)findViewById(R.id.btn_choix4);
        btn5 = (Button)findViewById(R.id.btn_choix5);
        btn6 = (Button)findViewById(R.id.btn_choix6);
        btn7 = (Button)findViewById(R.id.btn_choix7);
        btn8 = (Button)findViewById(R.id.btn_choix8);
        btn9 = (Button)findViewById(R.id.btn_choix9);
        btn10 = (Button)findViewById(R.id.btn_choix10);
        btn11 = (Button)findViewById(R.id.btn_choix11);
        btn12 = (Button)findViewById(R.id.btn_choix12);

        btn1.setOnClickListener(choixListener);
        btn2.setOnClickListener(choixListener);
        btn3.setOnClickListener(choixListener);
        btn4.setOnClickListener(choixListener);
        btn5.setOnClickListener(choixListener);
        btn6.setOnClickListener(choixListener);
        btn7.setOnClickListener(choixListener);
        btn8.setOnClickListener(choixListener);
        btn9.setOnClickListener(choixListener);
        btn10.setOnClickListener(choixListener);
        btn11.setOnClickListener(choixListener);
        btn12.setOnClickListener(choixListener);

    }
    OnClickListener choixListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.btn_choix1 :
                    choixTable = "animal";
                    break;
                case R.id.btn_choix2 :
                    choixTable = "body";
                    break;
                case R.id.btn_choix3 :
                    choixTable = "colors";
                    break;
                case R.id.btn_choix4 :
                    choixTable = "fruits";
                    break;
                case R.id.btn_choix5 :
                    choixTable = "furnitures";
                    break;
                case R.id.btn_choix6 :
                    choixTable = "geometric_forms";
                    break;
                case R.id.btn_choix7 :
                    choixTable = "jobs";
                    break;
                case R.id.btn_choix8 :
                    choixTable = "kitchen";
                    break;
                case R.id.btn_choix9 :
                    choixTable = "school";
                    break;
                case R.id.btn_choix10 :
                    choixTable = "season";
                    break;
                case R.id.btn_choix11 :
                    choixTable = "transport";
                    break;
                case R.id.btn_choix12 :
                    choixTable = "vegetables";
                    break;
            }
            Intent i = new Intent("com.example.ilias.mygame.MAINACTIVITY");
            i.putExtra("choixTable",choixTable);
            startActivity(i);
        }
    };
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
