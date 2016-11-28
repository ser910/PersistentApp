package com.degtyar.sergey.persistentapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String PREF_FILENAME = "com.degtyar.sergey.persistentapp_id_prefs.filename";
    public static final String PREF_NAME = "com.degtyar.sergey.persistentapp_id_prefs.name";
    public static final String PREF_SURNAME = "com.degtyar.sergey.persistentapp_id_prefs.surname";
    public static final String PREF_SEX = "com.degtyar.sergey.persistentapp_id_prefs.sex";
    public static final String PREF_AGE = "com.degtyar.sergey.persistentapp_id_prefs.age";
    EditText name;
    EditText surname;
    EditText age;
    CheckBox cb;
    Button save;
    Button load;
    Button clear;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(
                        prefs.getString(PREF_NAME, ""),
                        prefs.getString(PREF_SURNAME, ""),
                        prefs.getBoolean(PREF_SEX, true),
                        prefs.getInt(PREF_AGE, 0) + " "
                );
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefs.edit().clear().apply();
                load("", "", false, "");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        save();
    }

    @Override
    protected void onStart() {
        super.onStart();
        load(
                prefs.getString(PREF_NAME, ""),
                prefs.getString(PREF_SURNAME, ""),
                prefs.getBoolean(PREF_SEX, true),
                prefs.getInt(PREF_AGE, 0) + " "
        );
        if(TextUtils.isEmpty(name.toString())||TextUtils.isEmpty(surname.toString())||age.toString()=="0")
            save.setVisibility(View.VISIBLE);
    }

    private void load(String string, String string2, boolean aBoolean, String text) {
        name.setText(string);
        surname.setText(string2);
        cb.setChecked(aBoolean);
        age.setText(text);
    }

    private void save() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PREF_NAME, String.valueOf(name.getText()));
        editor.putString(PREF_SURNAME, String.valueOf(surname.getText()));
        editor.putBoolean(PREF_SEX,cb.isChecked());
        editor.putInt(PREF_AGE,Integer.parseInt(String.valueOf(age.getText())));
        editor.apply();
    }

    private void initUI() {
        prefs = getSharedPreferences(PREF_FILENAME,MODE_PRIVATE);
        name = (EditText) findViewById(R.id.name);
        surname =(EditText) findViewById(R.id.surname);
        age = (EditText) findViewById(R.id.age);
        save = (Button) findViewById(R.id.b_save);
        load = (Button) findViewById(R.id.b_load);
        clear= (Button) findViewById(R.id.b_clear);
        cb = (CheckBox) findViewById(R.id.sex);
    }
}
