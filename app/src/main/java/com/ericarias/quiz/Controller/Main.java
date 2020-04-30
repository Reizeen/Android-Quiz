package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorNotificLogin));*/

    }


    public void cerrrarSesion(View view) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", 0);
        editor.putString("token", "null");
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void addQuestion(View view) {
        Intent intent = new Intent(getApplicationContext(), AddQuestion.class);
        startActivityForResult(intent, Utilities.COD_ADD_QUESTION);
    }
}
