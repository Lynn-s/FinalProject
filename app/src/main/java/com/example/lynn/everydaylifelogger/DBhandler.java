package com.example.lynn.everydaylifelogger;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhandler extends SQLiteOpenHelper{


    public DBhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // 테이블 생성코드
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table ScheduleList( _id INTEGER PRIMARY KEY AUTOINCREMENT, list text, schedule text);";
        db.execSQL(sql);
    }

    // 테이블 삭제 코드
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists ScheduleList";
        db.execSQL(sql);
        onCreate(db);
    }

    //입력
    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    //수정
    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    //삭제
    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    //출력
    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";

        Cursor cursor = db.rawQuery("select * from ScheduleList", null);
        while(cursor.moveToNext()) {
            str += cursor.getInt(0)
                    + " "
                    + cursor.getString(1)
                    + ": "
                    + cursor.getString(2)
                    + "\n";
        }

        return str;
    }

    //이하 비율 계산 함수들
    public double result_eat(){
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        double num = 0;
        double num_eat = 0;
        double result;

        Cursor cursor = db.rawQuery("select * from ScheduleList", null);
        while(cursor.moveToNext()) {
            num += 1;
            str = cursor.getString(1);
            if(str.equals("식사")){num_eat+=1;}
        }

        result = (num_eat/num)*100;
        result = Double.parseDouble(String.format("%.2f",result));
        return result;
    }

    public double result_exercise(){
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        double num = 0;
        double num_exercise = 0;
        double result = 0;

        Cursor cursor = db.rawQuery("select * from ScheduleList", null);

        while(cursor.moveToNext()) {
            num += 1;
            str = cursor.getString(1);
            if(str.equals("운동")){num_exercise+=1;}
        }

        result = (num_exercise/num)*100;
        result = Double.parseDouble(String.format("%.2f",result));
        return result;
    }

    public double result_learn(){
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        double num = 0;
        double num_learn = 0;
        double result = 0;

        Cursor cursor = db.rawQuery("select * from ScheduleList", null);
        while(cursor.moveToNext()) {
            num += 1;
            str = cursor.getString(1);
            if(str.equals("학습")){num_learn+=1;}
        }

        result = (num_learn/num)*100;
        result = Double.parseDouble(String.format("%.2f",result));
        return result;
    }

    public double result_culture(){
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        double num = 0;
        double num_culture = 0;
        double result = 0;

        Cursor cursor = db.rawQuery("select * from ScheduleList", null);
        while(cursor.moveToNext()) {
            num += 1;
            str = cursor.getString(1);
            if(str.equals("문화")){num_culture+=1;}
        }

        result = (num_culture/num)*100;
        result = Double.parseDouble(String.format("%.2f",result));
        return result;
    }

    public double result_etc(){
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        double num = 0;
        double num_etc = 0;
        double result = 0;

        Cursor cursor = db.rawQuery("select * from ScheduleList", null);
        while(cursor.moveToNext()) {
            num += 1;
            str = cursor.getString(1);
            if(str.equals("기타")){num_etc+=1;}
        }

        result = (num_etc/num)*100;
        result = Double.parseDouble(String.format("%.2f",result));
        return result;
    }
}
