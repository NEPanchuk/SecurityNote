package com.example.n.securitynotes;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
public class CreateNote extends AppCompatActivity {
    EditText etTheme;
    EditText etText;
    int id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);
        etTheme = (EditText) findViewById(R.id.etTheme);
        etText = (EditText) findViewById(R.id.etText);
        Intent intent = getIntent();
        id_user = Integer.parseInt(intent.getStringExtra("id_user"));
    }
    public void onCreateNote(View v){
        String theme = etTheme.getText().toString();
        String text = etText.getText().toString();
        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_user", id_user);
        cv.put("theme", theme);
        cv.put("text_note", text);
        db.insert("note", null, cv);
        Intent intent = new Intent(this, SecurityNotes.class);
        String str_id = String.valueOf(id_user);
        intent.putExtra("id_user", str_id);
        startActivity(intent);
    }
}
