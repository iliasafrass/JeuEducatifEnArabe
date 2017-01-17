package com.example.ilias.database;

/**
 * Created by ilias on 29/04/2015.
 */

import com.example.ilias.classesDB.*;
import com.example.ilias.mygame.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDataBase extends SQLiteOpenHelper{
    private static String TAG = "DataBaseHelper";
    private final Context myContext;
    private String DB_PATH;

    private final static String DB_NAME="jeu_educ.db";

    public MyDataBase(Context context, String name, CursorFactory factory, int version){
        super(context,name,factory,version);
        myContext = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        if(!checkDataBase()){
            //this.getReadableDatabase();
            //Copy the database from assets
            copyDataBase();
            Log.e(TAG, "createDatabase database created");

        }
    }

    private boolean checkDataBase() {
        // retourne true/false si la bdd existe dans le dossier de l'app
        File dbfile = new File(DB_PATH + DB_NAME);
        return dbfile.exists();
    }//Fin de checkDataBase

    private void copyDataBase() {

        final String outFileName = DB_PATH + DB_NAME;

        InputStream myInput;
        try {
            // Ouvre la bdd de 'assets' en lecture
            myInput = myContext.getAssets().open(DB_NAME);

            // dossier de destination
            File pathFile = new File(DB_PATH);
            if(!pathFile.exists()) {
                if(!pathFile.mkdirs()) {
                    Toast.makeText(myContext, "Erreur : copydatabase(), pathFile.mkdirs()", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // Ouverture en écriture du fichier bdd de destination
            OutputStream myOutput = new FileOutputStream(outFileName);

            // transfert de inputfile vers outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            // Fermeture
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(myContext, "Erreur : copydatabase()", Toast.LENGTH_SHORT).show();
        }

        // on greffe le numéro de version
        try{
            SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            checkdb.setVersion(1);//DATABASE_VERSION =1
        }
        catch(SQLiteException e) {
            // bdd n'existe pas
        }

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
