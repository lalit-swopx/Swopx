package com.swopx.db_package;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.swopx.utils.Constant;
import com.swopx.utils.CustomPreference;

import java.util.ArrayList;
import java.util.HashMap;

import static com.swopx.utils.CustomPreference.rowid;
import static com.swopx.utils.CustomPreference.short_name;


public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "app_swopx_database";


    public static final String KEY_ROWID = "_id";
    public static final String TABLE_LEGAL_ENTITY = "legal_entity";
    public static final String TABLE_CATEGORY = "category";
//    public static final String TABLE_NAME_CURRENT_CITY = "current_city_data_storage";
//    public static final String TABLE_NAME_COUNTRY = "country_data_storage";
//    public static final String TABLE_NAME_STATE = "state_data_storage";
//    public static final String TABLE_NAME_CURRENT_STATE = "current_state_data_storage";
//    public static final String TABLE_ALL_STATE = "table_all_state";
//    public static final String TABLE_SUBJECT = "table_subject";
//    public static final String TABLE_QUALIFICATION = "table_qualification";
//    public static final String TABLE_INSITUTION = "table_insitution";
//    public static final String TABLE_EDUCATIONAL_DETAIL = "educational_detail";
//    public static final String TABLE_PROFESIONAL_DETAIL = "professional_detail";
//    public static final String TABLE_SKILL_DETAIL = "skill_detail";
//    public static final String TABLE_LANGUAGE_DETAIL = "language_detail";
//    public static final String TABLE_JOB_PROFILE = "job_profile";
//    public static final String TABLE_DESIGNATION = "designation";
//    public static final String TABLE_EMPLOYER = "employer";
//    public static final String TABLE_ALL_SKILL_Suggesstion= "skill_suggestion";
//    public static final String TABLE_Courses= "courses";
//    public static final String TABLE_Domain= "domain";
//    public static final String TABLE_GET_COURSES= "get_courses";


    Context context;


    CustomPreference global_variables;

    private static DBHelper appCacheDBHelper = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
         global_variables=new CustomPreference();
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_LEGAL_TABLE = "CREATE TABLE " + TABLE_LEGAL_ENTITY + "("
                + KEY_ROWID + " integer PRIMARY KEY autoincrement, "
                + global_variables.id + " TEXT, " + global_variables.name + " TEXT "+")";
        db.execSQL(CREATE_LEGAL_TABLE);

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_ROWID + " integer PRIMARY KEY autoincrement, "
                + global_variables.id + " TEXT, " + global_variables.name + " TEXT "+")";
        db.execSQL(CREATE_CATEGORY_TABLE);

    }


    public static DBHelper getInstance(Context paramContext) {
        if (appCacheDBHelper == null)
            appCacheDBHelper = new DBHelper(paramContext);
        return appCacheDBHelper;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEGAL_ENTITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        onCreate(db);
    }





    public long insert(HashMap<String, String> hashMaps, String table_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (String key : hashMaps.keySet()) {
//            Log.d("====>insert", key);
//            Log.d("====>inserthash", hashMaps.get(key));
            values.put(key, hashMaps.get(key));
        }
        return db.insert(table_name, null, values);
    }

    public boolean updateData(HashMap<String, String> columnValue, String whereColumn, String whereValue, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (String key : columnValue.keySet()) {
            contentValues.put(key, columnValue.get(key));
        }
        db.update(tableName, contentValues, whereColumn + "=?", new String[]{whereValue});
        return true;
    }

    public ArrayList<HashMap<String, String>> getData(ArrayList<String> arrayList, String tableName) {
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from " + tableName + "", null);
        Log.d("====>DAta IN TABLE ", cursor.getColumnCount() + "");
        if (cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                HashMap<String, String> hashMap = new HashMap<>();
                for (int i = 0; i < arrayList.size(); i++) {
                    hashMap.put(arrayList.get(i), cursor.getString(cursor.getColumnIndex(arrayList.get(i))));
                    Log.e("====> cursor", cursor.getColumnName(i) + "");
                }
                hashMaps.add(hashMap);
            }
        }
        cursor.close();
        return hashMaps;
    }

    public ArrayList<HashMap<String, String>> getDataLast(ArrayList<String> arrayList, String tableName)
    {
        ArrayList<HashMap<String, String>> last_item = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + tableName;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(arrayList.get(0), cursor.getString(cursor.getColumnIndex(arrayList.get(0))));
            last_item.add(hashMap);
            Log.e("====> cursor", ""+last_item.size());
//            while (cursor.moveToLast()) {
//                HashMap<String, String> hashMap = new HashMap<>();
//                for (int i = 0; i < arrayList.size(); i++) {
//                    hashMap.put(arrayList.get(i), cursor.getString(cursor.getColumnIndex(arrayList.get(i))));
////                    Log.e("====> cursor", cursor.getColumnName(i) + "");
//                }
//                last_item.add(hashMap);
//            }
        }

//        cursor.moveToLast();
        cursor.close();
        return last_item;
    }

    public boolean truncateData(String table_name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + table_name);
        if (numberOfRows(table_name) == -1) {
            Log.d("===>", "truncateData Successfully");
            return true;
        }
        return false;
    }


    public int getDataWhere(ArrayList<String> arrayList, String tableName, String whereColumn, String value) {
        ArrayList<HashMap<String, String>> hashMaps = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("<><>query", "select * from " + tableName + " where " + whereColumn + "='" + value + "'");
        Cursor cursor = db.rawQuery("select * from " + tableName + " where " + whereColumn + "='" + value + "'", null);

        Log.d("<><>cursor", cursor.getCount() + "");
        Log.d("====>?", String.valueOf(cursor.getCount()));
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            int l = 0;
            while (l < cursor.getCount()) {
                HashMap<String, String> hashMap = new HashMap<>();
                for (int i = 0; i < arrayList.size(); i++) {
                    Log.d("=====>", cursor.getString(cursor.getColumnIndex(arrayList.get(i))) + "");
                    hashMap.put(arrayList.get(i), cursor.getString(cursor.getColumnIndex(arrayList.get(i))));
                }
                cursor.moveToNext();
                hashMaps.add(hashMap);
                Log.d("<><>loop", hashMaps.size() + "");
                l++;
            }
        }
        Log.d("<><>", hashMaps.size() + "");
        cursor.close();
        return hashMaps.size();
    }

    public int numberOfRows(String table_name) {
        SQLiteDatabase db = this.getReadableDatabase();
//        Log.e("====>", "numberOfRows");
        int numRows = (int) DatabaseUtils.queryNumEntries(db, table_name);
//        Log.e("===>numofRows", String.valueOf(numRows));
        return numRows;
    }

    public Cursor fetchItemsByDescSqliteExternal(String inputText, String tableNameCountry) throws SQLException {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor mCursor=null;
        Constant.Log("Table Name","Table Name===="+tableNameCountry);
        if(tableNameCountry.equalsIgnoreCase(TABLE_LEGAL_ENTITY))
        {
            Constant.Log("Table Name","Table Name====1"+tableNameCountry+numberOfRows(tableNameCountry));
             mCursor = db.query(true, tableNameCountry, new String[]{rowid + " _id", global_variables.id, global_variables.name}, global_variables.name + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        else
        {
            Constant.Log("Table Name","Table Name====6"+tableNameCountry+numberOfRows(tableNameCountry));
             mCursor = db.query(true, tableNameCountry, new String[]{rowid + " _id", global_variables.id, global_variables.name}, global_variables.name + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public void deleteData(String value, String table_name, String column) {
        SQLiteDatabase db = this.getWritableDatabase();
        //return db.delete(table_name, column + " = ? ", new String[]{value});
        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        String Query = "DELETE FROM " + table_name + " WHERE " + column + "= '" + value + "'";
        db.execSQL(Query);

        //Close the database
        db.close();
        Constant.Log("Number of row"," Table rows"+numberOfRows(table_name)+Query);
    }



}

