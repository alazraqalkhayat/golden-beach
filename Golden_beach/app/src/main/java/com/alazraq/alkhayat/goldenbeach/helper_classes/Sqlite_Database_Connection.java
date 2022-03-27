package com.alazraq.alkhayat.goldenbeach.helper_classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_watching_list;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Sqlite_Database_Connection extends SQLiteOpenHelper {

    //public static final String DbName="alazraq.db";

    //public Database_Connection(Context context,String DBNAME){
        //super(context,DBNAME,null,1);
    //}
    static final String DBNAME="golden_beach_database.db";

    public Sqlite_Database_Connection(Context context){
        super(context,DBNAME,null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table If Not EXISTS name_of_all(name TEXT ,session INTEGER,PRIMARY KEY(name,session))");
        db.execSQL("create table If Not EXISTS watching_list(name TEXT,session INTEGER,PRIMARY KEY(name,session))");
        //db.execSQL("CREATE TABLE IF NOT EXISTS watching_list(name TEXT PRIMARY KEY,session TEXT)");
        //db.execSQL("create table  If Not EXISTS watching_list(name_of TEXT PRIMARY KEY,session TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table If EXISTS name_of_all");
        db.execSQL("DROP TABLE IF EXISTS watching_list");
        onCreate(db);


    }


    //start employees tables

    public boolean insertNameAndSessionOfAll(String name,int session){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("session",session);

        //contentValues.put("session",session);

        long result= db.insert("name_of_all",null,contentValues);


        if(result==-1)
            return false;
        else
            return true;




    }

    public String getNameAndSessionOfAll(String name1,int session1){

        String get_name="";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select name from  name_of_all where name='"+name1+"' AND session="+session1+"",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){

            get_name=res.getString(res.getColumnIndex("name"));

            res.moveToNext();
        }

        return get_name;
    }

    public boolean insertNewWatchingList(String name,int session){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("session",session);


        long result= db.insert("watching_list",null,contentValues);


        if(result==-1)
            return false;
        else
            return true;



    }

    public ArrayList getAllWatchingLists(){

        ArrayList<Items_of_watching_list> arr=new ArrayList<Items_of_watching_list>();

        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("select * from  watching_list ORDER BY name DESC ",null);
        res.moveToFirst();
        while (res.isAfterLast()==false){

            String name=res.getString(res.getColumnIndex("name"));
            String session=res.getString(res.getColumnIndex("session"));


            arr.add(new Items_of_watching_list(name,Integer.valueOf(session)));




            res.moveToNext();
        }

        return arr;
    }

    public void deleteLimitedWatchingListItems(String name, int session){

        SQLiteDatabase db=this.getWritableDatabase();

        String delete_watching_list_items_query="delete from watching_list WHERE name='"+name+"' AND session="+session+"";
        db.execSQL(delete_watching_list_items_query);

    }




}
