package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.Response;
import com.ericarias.quiz.Model.Users;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;

public class Start extends AppCompatActivity {

    private int id;
    private String token;
    private TextView errorStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        errorStart = findViewById(R.id.errorStart);

        carggarCredenciales();
        comprobarSesion();
    }

    public void carggarCredenciales() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        id = preferences.getInt("id", 0);
        token = preferences.getString("token", "null");
    }

    public void comprobarSesion() {
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.session(new Users(id, token)).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (!response.isSuccessful())
                    errorStart.setVisibility(View.VISIBLE);

                Intent intent;

                if(response.body().getResp())
                    intent = new Intent(getApplicationContext(), Main.class);
                else
                    intent = new Intent(getApplicationContext(), Login.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                errorStart.setVisibility(View.VISIBLE);
                Log.e(null, "--> Error Start:" + t.getMessage());
            }
        });
    }
}