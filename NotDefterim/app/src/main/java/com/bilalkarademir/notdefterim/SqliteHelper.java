package com.bilalkarademir.notdefterim;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String database_NAME = "NotDB";
    private static final int database_VERSION = 2;
    private static final String table_NOT = "notlar";
    private static final String not_id = "id";
    private static final String not_TITLE = "baslik";
    private static final String not_ICERIK = "icerik";
    private static final String[] COLUMNS = {not_id,not_TITLE,not_ICERIK};

    private static final String CREATE_NOT_TABLE = "CREATE TABLE "
            + table_NOT+ " ("
            + not_id+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + not_TITLE+ " TEXT, "
            + not_ICERIK+ " TEXT )";

    public SqliteHelper( Context context) {
        super(context, database_NAME, null, database_VERSION);
        //super(context, String.valueOf(context.getDatabasePath(database_NAME)), null, database_VERSION); eğer veriler telefon hafızasında tutulmasını istiyorsak
       // super(context, new File(Environment.getExternalStorageDirectory(),database_NAME).toString(), null, database_VERSION); hafıza kartında oluşmasını istiyorsan

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    db.execSQL(CREATE_NOT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+table_NOT);
        this.onCreate(db);
    }

    public void NotEkle( Not not){

        SQLiteDatabase db = this.getWritableDatabase();//bir değişiklik yapılıyprsa write
        ContentValues degerler = new ContentValues();
        degerler.put(not_TITLE,not.getBaslik());
        degerler.put(not_ICERIK,not.getIcerik());
        db.insert(table_NOT,null,degerler);
        db.close();


    }

    public List<Not> NotListele(){
        List<Not> notlar = new ArrayList<>();
        String query = "SELECT * FROM "+table_NOT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        Not not = null;
        if(cursor.moveToFirst()){
            do{
                not = new Not();
                not.setId(Integer.parseInt(cursor.getString(0)));
                not.setBaslik(cursor.getString(1));
                not.setIcerik(cursor.getString(2));
                notlar.add(not);
            }while (cursor.moveToNext());


        }
        return  notlar;
    }

    public Not NotOku(int id){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(table_NOT,COLUMNS,"id = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

            Not not = new Not();
            not.setId(Integer.parseInt(cursor.getString(0)));
            not.setBaslik(cursor.getString(1));
            not.setIcerik(cursor.getString(2));
            return not;

    }

    public void NotSil(Not not){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_NOT,not_id+ " = ?",new String[]{String.valueOf(not.getId())});
        db.close();
    }
    //guncelleme de dönüş değeri etkilenen kayıt sayısıdır.
    public int NotGuncelle(Not not){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put("baslik",not.getBaslik());
        degerler.put("icerik",not.getIcerik());
        int i = db.update(table_NOT,degerler,not_id+ " = ?",new String[]{String.valueOf(not.getId())});
        db.close();
        return i;

    }
}


