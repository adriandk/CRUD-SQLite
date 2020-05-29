package com.example.projectsql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KoneksiDB extends SQLiteOpenHelper {

    static final String database = "cobacobalagi",
            tabel = "cobahapehp",
            id = "_id",
            namadepan = "_namadepan",
            namabelakang = "_namabelakang",
            nohp = "_nohp",
            organisasi = "_organisasi",
            gender = "_gender",
            email ="_email";
    static final int version = 1;

    public KoneksiDB(Context c) {
        super(c, database, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " +tabel + " ( " + id
        + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                namadepan + " text, " +
                namabelakang + " text, " +
                nohp + " text, " +
                organisasi + " text, " +
                gender + " text, " +
                email + " text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop Table " + tabel);
        onCreate(db);
    }
}
