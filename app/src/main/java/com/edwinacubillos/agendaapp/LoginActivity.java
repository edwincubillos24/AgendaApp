package com.edwinacubillos.agendaapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText eUsername, ePassword;
    private Button bLogin;
    private String username = "edwinacubillos";
    private String password = "12345678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        eUsername = (EditText) findViewById(R.id.eUsername);
        ePassword = (EditText) findViewById(R.id.ePassword);
        bLogin = (Button) findViewById(R.id.bLogin);

        bLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (eUsername.getText().toString().equals(username)
                && ePassword.getText().toString().equals(password)){

            setSharedPreferences();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences("MisPreferencias",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username",username);
        editor.putString("password",password);
        editor.putBoolean("isLog",true);
        editor.commit();
    }


}