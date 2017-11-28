package com.edwinacubillos.agendaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by edwin on 22/11/17.
 */

public class AgendaSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate="CREATE TABLE Contactos (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +//0
            "nombre     TEXT," +//1
            "telefono   TEXT," +//2
            "correo     TEXT," +//3
            "cumple     TEXT," +//4
            "lat        TEXT," +//5
            "long       TEXT)";//6

    public AgendaSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contactos");
        db.execSQL(sqlCreate);
    }
}
