package com.example.n.securitynotes;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Registration extends AppCompatActivity {
    EditText etLogin;
    EditText etPassword;
    EditText etKey;
    Button btnRegistration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etKey = (EditText) findViewById(R.id.etKey);
        btnRegistration = (Button) findViewById(R.id.btnRegistration);
    }
    public void onRegistration(View v){
        String login = etLogin.getText().toString();
        String password = etPassword.getText().toString();
        String key = etKey.getText().toString();
        String regex = "^[а-яА-ЯёЁa-zA-Z0-9]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(login);
        if (login.equals("")){showDialog(1);}
        if (password.equals("")){showDialog(2);}
        if (password.equals("")){showDialog(3);}
        if (!matcher.matches()) {showDialog(4);}
        if (password.length() < 8) {showDialog(5);}
        if (key.length() < 16) {showDialog(6);}
        else {
        DBHelper dbh = new DBHelper(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("login", login);
        cv.put("password", password);
        cv.put("key_phrase", key);
        db.insert("user", null, cv);
        Intent intent = new Intent(this, Entering.class);
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
            adb.setMessage("Пустое поле логина");
            return adb.create();
        }
        if (id == 2) {
            adb.setMessage("Пустое поле пароля");
            return adb.create();
        }
        if (id == 3) {
            adb.setMessage("Пустое поле ключа");
            return adb.create();
        }
        if (id == 4) {
            adb.setMessage("Для поля логин возможен ввод только букв и цифр");
            return adb.create();
        }
        if (id == 5) {
            adb.setMessage("Для поля пароль длина должна быть больше 8 символов");
            return adb.create();
        }
        if (id == 6) {
            adb.setMessage("Для поля ключевая фраза длина должна быть больше 16 символов");
            return adb.create();
        }
        return super.onCreateDialog(id);}
}
