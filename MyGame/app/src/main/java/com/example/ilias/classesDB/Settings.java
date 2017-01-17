package com.example.ilias.classesDB;

/**
 * Created by hamza on 15/05/15.
 */
public class Settings {
    private int son;
    private int etat_cursor;
    private int score_etat;
    private int score_max;
    private int replay_code;

    public int getReplay_code() {
        return replay_code;
    }

    public void setReplay_code(int replay_code) {
        this.replay_code = replay_code;
    }

    public int getSon() {
        return son;
    }

    public int getEtat_cursor() {
        return etat_cursor;
    }

    public int getScore_etat() {
        return score_etat;
    }

    public int getScore_max() {
        return score_max;
    }

    public void setSon(int son) {
        this.son = son;
    }

    public void setEtat_cursor(int etat_cursor) {
        this.etat_cursor = etat_cursor;
    }

    public void setScore_etat(int score_etat) {
        this.score_etat = score_etat;
    }

    public void setScore_max(int score_max) {
        this.score_max = score_max;
    }
}
