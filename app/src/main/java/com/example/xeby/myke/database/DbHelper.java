package com.example.xeby.myke.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC on 2016/9/26.
 */
public class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "StudentClass.db";
    private static final int DB_VERSION = 1;
    //表名
    public static final String TABLE_STUCLS = "stucls";
    //表中的字段
    public static final String STUCLS_NAME_COL = "name";
    public static final String STUCLS_DAY_COL = "day";
    public static final String STUCLS_NUM_COL = "num";
    public static final String TYPE = "type";
    public static final String STUCLS_COUNT_COL = "count";
    public static final String TEACHER_NAME_COL = "teachername";
    public static final String LOCATION_COL = "location";
    public static final String START_WEEK_COL = "startweek";
    public static final String END_WEEK_COL = "endweek";
    public static final String TEST_WEEK = "testweek";
    public static final String TEST_DAY = "testday";
    public static final String TEST_LOCATION = "testlocation";
    public static final String TEST_CLASS_START = "testclassstart";
    public static final String TEST_CLASS_COUNT = "testclasscount";

    //    public static final String STUCLS_COLOR_COL = "color";
    //创建表
    private static final String CREATE_STUCLS = "create table " + TABLE_STUCLS +
            " (" + STUCLS_NAME_COL + " TEXT," +
            STUCLS_DAY_COL + " integer," +
            STUCLS_NUM_COL + " integer," +
            STUCLS_COUNT_COL + " integer," +
            TEACHER_NAME_COL + " TEXT," +
            LOCATION_COL + " TEXT," +
            START_WEEK_COL + " integer," +
            END_WEEK_COL + " integer," +
            TEST_WEEK + " integer," +
            TEST_DAY + " integer," +
            TYPE + " integer," +
            TEST_LOCATION + " TEXT," +
            TEST_CLASS_START + " integer," +
            TEST_CLASS_COUNT + " integer" +
            ");";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUCLS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //暂时先不写
    }
}
