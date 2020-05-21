package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.ResponseServer;
import com.ericarias.quiz.Model.User;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import retrofit2.Call;
import retrofit2.Callback;

public class Start extends AppCompatActivity {

    private TextView errorStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        errorStart = findViewById(R.id.errorStart);
        comprobarSesion();
    }

    /**
     * Comprueba si hay una sesion creada con anterioridad a través del token
     */
    public void comprobarSesion() {
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.session(new User(Utilities.getUserID(this), Utilities.getToken(this))).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, retrofit2.Response<ResponseServer> response) {
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
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                errorStart.setVisibility(View.VISIBLE);
                Log.e(null, "--> Error Start:" + t.getMessage());
            }
        });
    }
}