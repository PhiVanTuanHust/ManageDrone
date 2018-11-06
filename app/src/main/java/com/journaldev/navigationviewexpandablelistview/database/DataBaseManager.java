package com.journaldev.navigationviewexpandablelistview.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 31/10/2018.
 */

public class DataBaseManager extends SQLiteOpenHelper {
    private static DataBaseManager dataBaseManager;
    public static final String NAME="Tuongtacnguoimay";
    public static final int VERSION=1;
    private Cursor cursor=null;
    private SQLiteDatabase db;
    private String sql;
    public static final String CREATE_TABLE_ZONE="CREATE TABLE `zone` (\n" +
            "\t`id`\tINTEGER NOT NULL,\n" +
            "\t`stt`\tINTEGER NOT NULL,\n" +
            "\t`latitude`\tREAL NOT NULL,\n" +
            "\t`longitude`\tREAL NOT NULL,\n" +
            "\tPRIMARY KEY(`id`)\n" +
            ");";
    public static final String CREATE_TABLE_COUNT_ZONE="CREATE TABLE `count_zone` (\n" +
            "\t`id`\tINTEGER NOT NULL,\n" +
            "\t`id_zone`\tINTEGER NOT NULL,\n" +
            "\tPRIMARY KEY(`id`)\n" +
            ");";
    public static DataBaseManager getInstance(Context context){
        return dataBaseManager;
    }
    public DataBaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ZONE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public PolygonOptions getZone(){
        db=getReadableDatabase();

        PolygonOptions polygonOptions=new PolygonOptions();
        List<LatLng> latLngs=new ArrayList<>();
        polygonOptions.addAll(latLngs);


        return polygonOptions;
    }
}
