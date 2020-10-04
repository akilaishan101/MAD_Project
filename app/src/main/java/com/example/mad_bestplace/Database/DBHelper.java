package com.example.mad_bestplace.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.example.mad_bestplace.foods;
import com.example.mad_bestplace.shops;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BestPlace.db";
    String type;

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, 7);

    }


    public void queryData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + UserMaster.SHOPE_TABLE + " (" +
                UserMaster.COLUMN_NAME_ID + " TEXT PRIMARY KEY," +
                UserMaster.COLUMN_NAME_SHOP + " TEXT," +
                UserMaster.COLUMN_NAME_COMPANY + " TEXT," +
                UserMaster.COLUMN_NAME_ADDRESS + " TEXT," +
                UserMaster.COLUMN_NAME_DISCRIPTION + " TEXT," +
                UserMaster.COLUMN_NAME_IMAGE + "BLOB)");

        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + UserMaster.USER_INFORMAION + " (" +
                UserMaster.COLUMN_NAME1_USERNAME + " TEXT PRIMARY KEY," +
                UserMaster.COLUMN_NAME3_EMAIL + " TEXT," +
                UserMaster.COLUMN_NAME4_PHONE + " TEXT," +
                UserMaster.COLUMN_NAME5_ADDRESS + " TEXT," +
                UserMaster.COLUMN_NAME7_TYPE + " TEXT," +
                UserMaster.COLUMN_NAME6_PASSWORD + " TEXT)");


        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + UserMaster.FOOD_TABLE + " (" +
                UserMaster.COLUMN_FOOD_ID + " TEXT PRIMARY KEY," +
                UserMaster.COLUMN_SFOOD_ID + " TEXT," +
                UserMaster.COLUMN_FOOD_NAME + " TEXT," +
                UserMaster.COLUMN_FOOD_PRICE + " TEXT," +
                UserMaster.COLUMN_NAME_IMAGE + "BLOB)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserMaster.SHOPE_TABLE);
        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UserMaster.USER_INFORMAION);
        onCreate(sqLiteDatabase);
    }

    //add shop

    public void insertData(String id, String name, String company, String address, String discription, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + UserMaster.SHOPE_TABLE + " VALUES ( ?, ?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, id);
        statement.bindString(2, name);
        statement.bindString(3, company);
        statement.bindString(4, address);
        statement.bindString(5, discription);
        statement.bindBlob(6, image);

        statement.executeInsert();
    }

    //add users
    public boolean Register_user(String UserName, String email, String phone, String address, String type, String Passwrod) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserMaster.COLUMN_NAME1_USERNAME, UserName);
        contentValues.put(UserMaster.COLUMN_NAME3_EMAIL, email);
        contentValues.put(UserMaster.COLUMN_NAME4_PHONE, phone);
        contentValues.put(UserMaster.COLUMN_NAME5_ADDRESS, address);
        contentValues.put(UserMaster.COLUMN_NAME7_TYPE, type);
        contentValues.put(UserMaster.COLUMN_NAME6_PASSWORD, Passwrod);

        long insert = db.insert(UserMaster.USER_INFORMAION, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;

        }


    }
    //feedback
    public boolean feedback(String UserName, String email, String phone, String message) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserMaster.COLUMN_NAME1_USERNAME1, UserName);
        contentValues.put(UserMaster.COLUMN_NAME3_EMAIL1, email);
        contentValues.put(UserMaster.COLUMN_NAME4_PHONE1, phone);
        contentValues.put(UserMaster.COLUMN_NAME5_MESSEGE, message);

        long insert = db.insert(UserMaster.FEED_INFORMAION, null, contentValues);
        if (insert == -1) {
            return false;
        } else {
            return true;

        }


    }

    public String login_Check(String username) {

        SQLiteDatabase db = this.getReadableDatabase();
        String quary = "SELECT " + UserMaster.COLUMN_NAME1_USERNAME + "," + UserMaster.COLUMN_NAME6_PASSWORD +
                "," + UserMaster.COLUMN_NAME7_TYPE + " FROM " + UserMaster.USER_INFORMAION;
        Cursor cursor = db.rawQuery(quary, null);

        String user, pass;
        pass = "User not found!";

        if (cursor.moveToFirst()) {

            do {
                user = cursor.getString(0);

                if (user.equals(username)) {
                    pass = cursor.getString(1);
                    type = cursor.getString(2);
                    break;
                }
            } while (cursor.moveToNext());

        }

        return pass;

    }

    public String passType() {
        return type;
    }



    public Cursor get_shop_values() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UserMaster.SHOPE_TABLE, null);
        return cursor;
    }

    public ArrayList<shops> getAllData() {

        ArrayList<shops> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UserMaster.SHOPE_TABLE, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String company = cursor.getString(2);
            String address = cursor.getString(3);
            String dis = cursor.getString(4);
            byte[] img = cursor.getBlob(5);

            shops sh = new shops(id, name, company, address, dis, img);

            arrayList.add(sh);
        }
        return arrayList;
    }


    public boolean update_shop(String id, String name, String company, String address, String discription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserMaster.COLUMN_NAME_ID, id);
        contentValues.put(UserMaster.COLUMN_NAME_SHOP, name);
        contentValues.put(UserMaster.COLUMN_NAME_COMPANY, company);
        contentValues.put(UserMaster.COLUMN_NAME_ADDRESS, address);
        contentValues.put(UserMaster.COLUMN_NAME_DISCRIPTION, discription);
        db.update(UserMaster.SHOPE_TABLE, contentValues, "ID = ?", new String[]{id});
        return true;

    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(UserMaster.SHOPE_TABLE, "ID = ?", new String[]{id});
    }


    public void addfoodData(String fid, String id, String name, String price, byte[] image) {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO " + UserMaster.FOOD_TABLE + " VALUES (?, ?, ?, ?, ?)";

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, fid);
        statement.bindString(2, id);
        statement.bindString(3, name);
        statement.bindString(4, price);
        statement.bindBlob(5, image);

        statement.executeInsert();
    }


    public ArrayList<foods> getAllfoodData(String sid) {

        ArrayList<foods> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //Cursor cursor = db.rawQuery(" SELECT *  FROM "+ UserMaster.FOOD_TABLE + "WHERE" + UserMaster.COLUMN_SFOOD_ID + " = " + sid,null);
        Cursor cursor = db.query(UserMaster.FOOD_TABLE, null, UserMaster.COLUMN_SFOOD_ID + " = " + sid, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String fid = cursor.getString(0);
            String fsid = cursor.getString(1);
            String fname = cursor.getString(2);
            String fprice = cursor.getString(3);
            byte[] img = cursor.getBlob(4);

            foods fd = new foods(fid, fsid, fname, fprice, img);

            arrayList.add(fd);
        }
        return arrayList;
    }

    public Cursor get_food_values() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + UserMaster.FOOD_TABLE, null);
        return cursor;
    }

    public boolean update_food(String fid, String fname, String fprice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserMaster.COLUMN_FOOD_ID, fid);
        contentValues.put(UserMaster.COLUMN_FOOD_NAME, fname);
        contentValues.put(UserMaster.COLUMN_FOOD_PRICE, fprice);
        db.update(UserMaster.FOOD_TABLE, contentValues, "ID = ?", new String[]{fid});
        return true;


    }

    public Integer deletefood(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(UserMaster.FOOD_TABLE, "ID = ?", new String[]{id});
    }


    public ArrayList<foods> foodData(String newText) {

        ArrayList<foods> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();



        Cursor cursor = db.query(UserMaster.FOOD_TABLE, null, UserMaster.COLUMN_FOOD_NAME + " LIKE '%" + newText + "%'",null ,null, null, null, null);
            while (cursor.moveToNext()) {
                String fid = cursor.getString(0);
                String fsid = cursor.getString(1);
                String fname = cursor.getString(2);
                String fprice = cursor.getString(3);
                byte[] img = cursor.getBlob(4);

                foods fd = new foods(fid, fsid, fname, fprice, img);

                arrayList.add(fd);
            }


        return arrayList;
    }

    public ArrayList<shops> getsearchshopData(String newText) {

        ArrayList<shops> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(UserMaster.SHOPE_TABLE, null, UserMaster.COLUMN_NAME_SHOP + " LIKE '%" + newText + "%'",null ,null, null, null, null);

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String company = cursor.getString(2);
            String address = cursor.getString(3);
            String dis = cursor.getString(4);
            byte[] img = cursor.getBlob(5);

            shops sh = new shops(id, name, company, address, dis, img);

            arrayList.add(sh);
        }
        return arrayList;
    }

    public ArrayList<foods> getAllfData() {

        ArrayList<foods> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(" SELECT *  FROM "+ UserMaster.FOOD_TABLE,null);


        while (cursor.moveToNext()) {
            String fid = cursor.getString(0);
            String fsid = cursor.getString(1);
            String fname = cursor.getString(2);
            String fprice = cursor.getString(3);
            byte[] img = cursor.getBlob(4);

            foods fd = new foods(fid, fsid, fname, fprice, img);

            arrayList.add(fd);
        }
        return arrayList;

    }


}


