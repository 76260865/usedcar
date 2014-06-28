package com.jason.usedcar.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import android.util.Log;

import com.jason.usedcar.db.CarTablesInfo.CarBrand;
import com.jason.usedcar.db.CarTablesInfo.CarModels;
import com.jason.usedcar.db.CarTablesInfo.CarProvince;
import com.jason.usedcar.db.CarTablesInfo.CarSeries;
import com.jason.usedcar.model.db.Brand;
import com.jason.usedcar.model.db.Province;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";

    private static final boolean DEBUG = true;

    private static final String DATABASE_NAME = "used_car.db";

    private static final int DATABASE_VERSION = 1;

    private static DBHelper sInstance;

    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context);
        }
        return sInstance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void createAllTables(SQLiteDatabase db) {
        createCarBrandTable(db);
        createCarSeriesTable(db);
        createCarModelTable(db);
    }

    private void createCarBrandTable(SQLiteDatabase db) {
        String cmd = "CREATE TABLE " + CarBrand.TABLE_NAME + " (" + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CarBrand.BRAND_ID + " INTEGER,"
                + CarBrand.BRAND_NAME + " TEXT," + ");";
        if (DEBUG) {
            Log.d(TAG, "createEcaRulesTable(): cmd = " + cmd);
        }
        db.execSQL(cmd);
    }

    private void createCarSeriesTable(SQLiteDatabase db) {
        String cmd = "CREATE TABLE " + CarSeries.TABLE_NAME + " (" + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CarSeries.SERIES_ID + " INTEGER,"
                + CarSeries.SERIES_NAME + " TEXT," + ");";
        if (DEBUG) {
            Log.d(TAG, "createCarSeriesTable(): cmd = " + cmd);
        }
        db.execSQL(cmd);
    }

    private void createCarModelTable(SQLiteDatabase db) {
        String cmd = "CREATE TABLE " + CarModels.TABLE_NAME + " (" + BaseColumns._ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT," + CarModels.MODEL_ID + " INTEGER,"
                + CarModels.MODEL_NAME + " TEXT," + ");";
        if (DEBUG) {
            Log.d(TAG, "createCarModelTable(): cmd = " + cmd);
        }
        db.execSQL(cmd);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createAllTables(db);
    }

    private void dropAll(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + CarBrand.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CarModels.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CarSeries.TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion < oldVersion) {
            return;
        }

        // TODO: merge data from old version to new version.
        dropAll(db);
        createAllTables(db);
    }

    public void insertBands(ArrayList<Brand> brands) {
        String sql = "INSERT INTO " + CarTablesInfo.CarBrand.TABLE_NAME + " (" + CarBrand.BRAND_ID
                + "," + CarBrand.BRAND_NAME + ") VALUES (?,?)";
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (Brand brand : brands) {
                SQLiteStatement sqlListStatment = db.compileStatement(sql);
                sqlListStatment.bindAllArgsAsStrings(new String[] {
                        String.valueOf(brand.getBrandId()), brand.getBrandName() });
                sqlListStatment.executeInsert();
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
    }

    public void insertProvinces(ArrayList<Province> provinces) {
        String sql = "INSERT INTO " + CarTablesInfo.CarProvince.TABLE_NAME + " ("
                + CarProvince.PROVINCE_ID + "," + CarProvince.PROVINCE_NAME + ") VALUES (?,?)";
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            for (Province province : provinces) {
                SQLiteStatement sqlListStatment = db.compileStatement(sql);
                sqlListStatment.bindAllArgsAsStrings(new String[] {
                        String.valueOf(province.getProvinceId()), province.getProvinceName() });
                sqlListStatment.executeInsert();
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.endTransaction();
        }
    }
}
