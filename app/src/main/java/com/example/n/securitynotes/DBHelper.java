package com.example.n.securitynotes;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user ("
                + "id integer primary key autoincrement,"
                + "login text," + "password text,"
                + "key_phrase text"+");");
        db.execSQL("create table note ("
                + "id integer primary key autoincrement,"
                + "id_user integer,"
                + "theme text," + "text_note text"
                + ");");
        ContentValues cv = new ContentValues();
        cv.put("id", 1);
        cv.put("login", "login");
        cv.put("password", "password");
        cv.put("key_phrase", "key_key_phrase");
        db.insert("user", null, cv);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}
}