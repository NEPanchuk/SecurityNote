package com.example.n.securitynotes;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
public class ChangeNote extends AppCompatActivity {
    EditText etTheme;
    EditText etText;
    String id_user;
    String theme;
    String text_note;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_note);
        Intent intent = getIntent();
        id_user = intent.getStringExtra("id_user");
        theme = intent.getStringExtra("theme");
        text_note = intent.getStringExtra("text_note");
        etTheme = (EditText) findViewById(R.id.etTheme);
        etText = (EditText) findViewById(R.id.etText);
        etTheme.setText(theme);
        etText.setText(text_note);
    }
    public void onChangeNote(View v){
        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("theme", etTheme.getText().toString());
        cv.put("text_note", etText.getText().toString());
        int updCount = db.update("note", cv, "id_user = ? and theme = ? and text_note = ?",
                new String[] { id_user, theme, text_note });
        Cursor c;
        c = db.query("note", null, null, null, null, null, null);
        if(c!=null){
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(c.getString(c.getColumnIndex(cn)) + ";");
                    }
                    Log.d("sddsdd",str);

                } while (c.moveToNext());
            }
        }
        Intent intent = new Intent(this, SecurityNotes.class);
        intent.putExtra("id_user",id_user);
        startActivity(intent);
    }
}
