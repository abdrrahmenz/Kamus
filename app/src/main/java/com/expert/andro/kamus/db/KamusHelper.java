package com.expert.andro.kamus.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.expert.andro.kamus.model.KamusModel;

import java.util.ArrayList;

/**
 * Created by adul on 20/09/17.
 */

public class KamusHelper {

    private static String DATABASE_TABLE_ID_ENG = DbHelper.TABLE_NAME_ID_ENG;
    private static String DATABASE_TABLE_ENG_ID = DbHelper.TABLE_NAME_ENG_ID;
    private Context context;
    private DbHelper dbHelper;

    private SQLiteDatabase database;

    public KamusHelper(Context context) {
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public Cursor searchQeryByNameIdEng(String query){
        return database.rawQuery("SELECT * FROM "+DATABASE_TABLE_ID_ENG+" WHERE "+DbHelper.FIELD_NAME+
        " LIKE '%"+query+"%'",null);
    }

    public ArrayList<KamusModel> getDataIdEng(String kata){
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = searchQeryByNameIdEng(kata);
        cursor.moveToFirst();

        KamusModel kamusModel;
        if (cursor.getCount()>0){
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.FIELD_ID)));
                kamusModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.FIELD_NAME)));
                kamusModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.FIELD_DESCRIPTION)));

                arrayList.add(kamusModel);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor searchQeryByNameEngId(String query){
        return database.rawQuery("SELECT * FROM "+DATABASE_TABLE_ENG_ID+" WHERE "+DbHelper.FIELD_NAME+
                " LIKE '%"+query+"%'",null);
    }

    public ArrayList<KamusModel> getDataEngId(String kata){
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = searchQeryByNameEngId(kata);
        cursor.moveToFirst();

        KamusModel kamusModel;
        if (cursor.getCount()>0){
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.FIELD_ID)));
                kamusModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.FIELD_NAME)));
                kamusModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.FIELD_DESCRIPTION)));

                arrayList.add(kamusModel);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public Cursor queryAllData(){
        return database.rawQuery("SELECT * FROM "+DATABASE_TABLE_ID_ENG+" ORDER BY "+DbHelper.FIELD_ID+" ASC",null);
    }

    public ArrayList<KamusModel> getAllData(){
        ArrayList<KamusModel> arrayList = new ArrayList<>();
        Cursor cursor = queryAllData();
        cursor.moveToFirst();
        KamusModel kamusModel;

        if (cursor.getCount()>0){
            do {
                kamusModel = new KamusModel();
                kamusModel.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DbHelper.FIELD_ID)));
                kamusModel.setName(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.FIELD_NAME)));
                kamusModel.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.FIELD_DESCRIPTION)));

                arrayList.add(kamusModel);

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public void insertTransactionEngId(ArrayList<KamusModel> kamusModels){
        String sql = "INSERT INTO "+DATABASE_TABLE_ENG_ID+" ("+DbHelper.FIELD_NAME+", "+DbHelper.FIELD_DESCRIPTION+") VALUES (?, ?)";
        database.beginTransaction();

        SQLiteStatement stmt = database.compileStatement(sql);

        for (int i = 0; i < kamusModels.size(); i++) {
            stmt.bindString(1, kamusModels.get(i).getName());
            stmt.bindString(2, kamusModels.get(i).getDescription());
            stmt.execute();
            stmt.clearBindings();
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void insertTransactionIdEng(ArrayList<KamusModel> kamusModels){
        String sql = "INSERT INTO "+DATABASE_TABLE_ID_ENG+" ("+DbHelper.FIELD_NAME+", "+DbHelper.FIELD_DESCRIPTION+") VALUES (?, ?)";
        database.beginTransaction();

        SQLiteStatement stmt = database.compileStatement(sql);

        for (int i = 0; i < kamusModels.size(); i++) {
            stmt.bindString(1, kamusModels.get(i).getName());
            stmt.bindString(2, kamusModels.get(i).getDescription());
            stmt.execute();
            stmt.clearBindings();
        }

        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void update(KamusModel kamusModel){
        ContentValues args = new ContentValues();
        args.put(DbHelper.FIELD_NAME, kamusModel.getName());
        args.put(DbHelper.FIELD_DESCRIPTION, kamusModel.getDescription());
        database.update(DATABASE_TABLE_ID_ENG, args, DbHelper.FIELD_ID+"= '"+kamusModel.getId()+"'",null);
    }

    public void delete(int id){
        database.delete(DATABASE_TABLE_ID_ENG, DbHelper.FIELD_ID + " = '" + id + "'",null);
    }
}
