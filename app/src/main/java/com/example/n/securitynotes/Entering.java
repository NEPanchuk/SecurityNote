package com.example.n.securitynotes;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.*;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.*;
import android.view.*;
public class Entering extends AppCompatActivity {
    final int DIALOG_EXIT = 1;
    EditText etLogin;
    EditText etPassword;
    EditText etKey;
    Button btnEnter;
    Button btnRegistration;
    Button btnNonReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entering);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etKey = (EditText) findViewById(R.id.etKey);
        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnRegistration = (Button) findViewById(R.id.btnRegistration);
        btnRegistration.setPaintFlags(btnRegistration.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnNonReg = (Button) findViewById(R.id.btnNonReg);
        btnNonReg.setPaintFlags(btnNonReg.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }
    public void onEntering(View v){
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String key = etKey.getText().toString();
        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor c;
        Boolean flag = false;
        String[] users_info = {};
        c = db.query("user", null, null, null, null, null, null);
        if(c!=null){
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(c.getString(c.getColumnIndex(cn)) + ";");
                    }
                    Log.d("dfdf",str);
                    users_info = str.split(";");
                    if (login.equals(users_info[1]) && password.equals(users_info[2]) && key.equals(users_info[3])){

                        flag = true;
                        break;
                    }
                } while (c.moveToNext());
            }
        }
        if(flag == true){
            Intent intent = new Intent(this, SecurityNotes.class);
            intent.putExtra("id_user", users_info[0]);
            intent.putExtra("login", users_info[1]);
            intent.putExtra("password", users_info[2]);
            intent.putExtra("key_user", users_info[3]);

            startActivity(intent);
        }
        else {
            showDialog(DIALOG_EXIT);
        }
        c.close();

    }
    public void onRegistration(View v){
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
    }
    public void onNoReg(View v){
        Intent intent = new Intent(this, SecurityNotes.class);
        intent.putExtra("id_user", 1);
        intent.putExtra("login", "login");
        intent.putExtra("password", "password");
        intent.putExtra("key_user", "key_key_phrase");
        startActivity(intent);
    }
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_EXIT) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            adb.setTitle("Ошибка");
            adb.setMessage("Неправильный логин или пароль");
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
            return adb.create();
        }
        return super.onCreateDialog(id);
    }
}

