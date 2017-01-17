package com.example.ilias.database;


//Cette calsse ext une classe qui permet la gestion de base de données

import com.example.ilias.classesDB.*;
import com.example.ilias.mygame.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class GestionDB {

    //Declaration des noms des colonnes de la table SETTINGS
    private static final String SETTING_COL_SON = "son";
    private static final String SETTING_COL_ETAT_CURSOR = "etat_cursor";
    private static final String SETTING_COL_SCORE_ETAT = "score_etat";
    private static final String SETTING_COL_SCORE_MAX = "score_max";
    private static final String SETTING_COL_REPLAY = "replay_code";
    private static final String TABLE_SETTINGS = "Settings";
    private String TABLE ;

    //Declaration des noms des colonnes de la table TEMPORAIRE
    private static final String TEMPORARY_COL_ID = "id";
    private static final String TEMPORARY_COL_NOM = "nom";
    private static final String TEMPORARY_COL_TAILLE = "taille";
    private static final String TEMPORARY_COL_INCOMPLET = "mot_incomplet";
    private static final String TEMPORARY_COL_LETTRE1 = "lettre1";
    private static final String TEMPORARY_COL_LETTRE2 = "lettre2";
    private static final String TEMPORARY_COL_IMAGE = "image";
    private static final String TABLE_TEMPORARY = "temporaire";


    private MyDataBase myBaseSQLite;
    private SQLiteDatabase bdd;

    public GestionDB(Context context){
        myBaseSQLite = new MyDataBase(context,"jeu_educ.db",null,1);
    }

    public void open(){
        bdd = myBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public Cursor getDataBase(){
        return bdd.rawQuery("select * from "+TABLE+" order by random()" , null);
    }

    public long insertSettings(Settings set){
        ContentValues values = new ContentValues();
        values.put(SETTING_COL_SON,set.getSon());
        values.put(SETTING_COL_ETAT_CURSOR,set.getEtat_cursor());
        values.put(SETTING_COL_SCORE_ETAT,set.getScore_etat());
        values.put(SETTING_COL_SCORE_MAX,set.getScore_max());
        values.put(SETTING_COL_REPLAY,set.getReplay_code());
        bdd.delete(TABLE_SETTINGS,null,null);
        return bdd.insert(TABLE_SETTINGS, null, values);
    }

    public Settings getSettings(){
        Cursor c = bdd.rawQuery("select * from "+TABLE_SETTINGS+"",null);
        return cursorToSettings(c);
    }

    private Settings cursorToSettings(Cursor c){
        if(c.getCount() == 0)
            return null;
        c.moveToFirst();
        Settings seting = new Settings();
        seting.setSon(c.getInt(0));
        seting.setEtat_cursor(c.getInt(1));
        seting.setScore_etat(c.getInt(2));
        seting.setScore_max(c.getInt(3));
        seting.setReplay_code(c.getInt(4));
        c.close();
        return seting;
    }

    public long insertTemporary(Temporaire temp){
        ContentValues values = new ContentValues();
        values.put(TEMPORARY_COL_ID,temp.getId());
        values.put(TEMPORARY_COL_NOM,temp.getNom());
        values.put(TEMPORARY_COL_TAILLE,temp.getTaille());
        values.put(TEMPORARY_COL_INCOMPLET,temp.getMot_incomplet());
        values.put(TEMPORARY_COL_LETTRE1,temp.getLettre1());
        values.put(TEMPORARY_COL_LETTRE2,temp.getLettre2());
        values.put(TEMPORARY_COL_IMAGE, temp.getImage());
        return bdd.insert(TABLE_TEMPORARY, null, values);
    }

    public void deleteTemporary(){
        bdd.delete(TABLE_TEMPORARY,null,null);
    }

    public Cursor getTemporary(){
        return bdd.rawQuery("select * from "+TABLE_TEMPORARY+"",null);
    }

    public void setTABLE(String TABLE) {
        this.TABLE = TABLE;
    }
}

