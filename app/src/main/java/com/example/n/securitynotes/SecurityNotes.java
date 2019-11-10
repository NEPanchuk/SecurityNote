package com.example.n.securitynotes;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
public class SecurityNotes extends AppCompatActivity implements View.OnClickListener {
    String id_user = "";
    String login = "";
    String password = "";
    String key_user = "";
    LinearLayout llNotes;
    List<List<String>> listNotes = new ArrayList<List<String>>();
    List<Button> lv = new ArrayList<Button>();
    String currentTheme = "";
    String currentText = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_notes);
        llNotes = (LinearLayout) findViewById(R.id.llNotes);
        Intent intent = getIntent();
        id_user = intent.getStringExtra("id_user");
        login = intent.getStringExtra("login");
        password = intent.getStringExtra("password");
        key_user = intent.getStringExtra("key_user");

    }
    String TAG = "----------123----------";
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity: onResume()");
        //Log.d(TAG, id_user);
        Intent intent = getIntent();
        id_user = intent.getStringExtra("id_user");
        Log.d(TAG, id_user);
        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
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
                    String[] spliter = str.split(";");
                    List<String> l = new ArrayList<String>();
                    l.add(spliter[2]);
                    l.add(spliter[3]);
                    if (spliter[1].equals(id_user)){
                        listNotes.add(l);
                    }
                } while (c.moveToNext());
            }
        }
        LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        for(int i = 0; i < listNotes.size(); i++){
            Button newNotes = new Button(this);
            newNotes.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            newNotes.setEms(10);
            newNotes.setText("Тема: " + listNotes.get(i).get(0));
            newNotes.setOnClickListener(this);
            newNotes.setId(i);
            newNotes.setBackground(this.getResources().getDrawable(R.drawable.border_top_bottom));
            lv.add(newNotes);
            llNotes.addView(newNotes, lParams);}
    }
    public void onClick(View v){

        currentTheme = "";
        currentText = "";
        for(int i = 0; i < lv.size(); i++){
            lv.get(i).setText("Тема: " + listNotes.get(i).get(0));
            lv.get(i).setBackground(this.getResources().getDrawable(R.drawable.border_top_bottom));

        }
        Button myButton= (Button)v;
        String myButtonText = myButton.getText().toString();
        for(int i = 0; i < listNotes.size(); i++){
            if (myButtonText.equals("Тема: " + listNotes.get(i).get(0))){
                myButton.setText("Тема: " + listNotes.get(i).get(0)+"\n\n" + "Текст: " + listNotes.get(i).get(1));
                currentTheme = listNotes.get(i).get(0);
                currentText = listNotes.get(i).get(1);
                myButton.setBackground(this.getResources().getDrawable(R.color.colorBtn));
            }
        }
    }
    public void onCreateNote(View v){
        Intent intent = new Intent(this, CreateNote.class);
        intent.putExtra("id_user", id_user);
        startActivity(intent);
    }
    public void onChangeNote(View v){
        if(currentTheme.equals("") && currentText.equals("")){showDialog(1);}
        else {
        Intent intent = new Intent(this, ChangeNote.class);
        intent.putExtra("id_user", id_user);
        intent.putExtra("theme", currentTheme);
        intent.putExtra("text_note", currentText);
        startActivity(intent);}
    }
    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Ошибка");
        adb.setIcon(android.R.drawable.ic_dialog_info);
        adb.setPositiveButton("Продолжить",
                // устанавливаем слушатель
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        switch (which) {
                            case Dialog.BUTTON_POSITIVE:
                                break;
                        }
                    }
                });
        if (id == 1) {
            adb.setMessage("Заметка не выбрана");
            return adb.create();
        }
        if (id == 2) {
            adb.setTitle("Примечание");
            adb.setMessage("Заметка удалена");
            return adb.create();
        }
        return super.onCreateDialog(id);}
    public void onDeleteNote(View v){
        if(currentTheme.equals("") && currentText.equals("")){showDialog(1);}
        else {
            DBHelper dbh = new DBHelper(this);
            SQLiteDatabase db = dbh.getWritableDatabase();
            int delCount = db.delete("note", "id_user = ? and theme = ? and text_note = ?", new String[]{id_user, currentTheme, currentText});
            Cursor c;
            c = db.query("note", null, null, null, null, null, null);
            listNotes.clear();
            if (c != null) {
                if (c.moveToFirst()) {
                    String str;
                    do {
                        str = "";
                        for (String cn : c.getColumnNames()) {
                            str = str.concat(c.getString(c.getColumnIndex(cn)) + ";");
                        }
                        String[] spliter = str.split(";");
                        List<String> l = new ArrayList<String>();
                        l.add(spliter[2]);
                        l.add(spliter[3]);
                        if (spliter[1].equals(id_user)) {
                            listNotes.add(l);
                        }

                    } while (c.moveToNext());
                }
            }
            for (int i = 0; i < listNotes.size(); i++) {
                Log.d(TAG, listNotes.get(i).get(0));
            }
            lv.clear();
            llNotes.removeAllViews();
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            for (int i = 0; i < listNotes.size(); i++) {
                Button newNotes = new Button(this);
                newNotes.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                newNotes.setEms(10);
                newNotes.setText("Тема: " + listNotes.get(i).get(0));
                newNotes.setOnClickListener(this);
                newNotes.setId(i);
                newNotes.setBackground(this.getResources().getDrawable(R.drawable.border_top_bottom));
                lv.add(newNotes);
                llNotes.addView(newNotes, lParams);
            }
            showDialog(2);
        }
    }
}
