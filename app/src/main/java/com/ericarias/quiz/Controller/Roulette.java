package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Roulette extends AppCompatActivity implements Animation.AnimationListener {

    private int degree;
    private ImageView ruleta;
    private final int MATEMATICAS = 45;
    private final int HISTORIA = 90;
    private final int ARTE = 135;
    private final int DEPORTE = 180;
    private final int LITERATURA = 225;
    private final int GEOGRAFIA = 270;
    private final int ENTRETENIMIENTO = 315;
    private final int CIENCIA = 360;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);
        ruleta = findViewById(R.id.ruleta);
        degree = 0;
    }

    public String getToken() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getString("token", "null");
    }

    public void spinRoulette(View view) {
        degree = new Random().nextInt(3600) + 72;
        RotateAnimation rotateAnimation = new RotateAnimation(0, degree, RotateAnimation.RELATIVE_TO_SELF,
                0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(3600);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(this);
        ruleta.startAnimation(rotateAnimation);
    }

    @Override
    public void onAnimationStart(Animation animation) { }

    @Override
    public void onAnimationEnd(Animation animation) {
        int num = 360 - (degree % 360);
        getQuestions(resultado(num));
    }

    @Override
    public void onAnimationRepeat(Animation animation) { }

    private String resultado(int num) {
        String result = null;
        Log.e(null, "resultado: " + num);

        if (num <= MATEMATICAS){
            result = "MAT";
        } else if (num <= HISTORIA){
            result = "HIS";
        } else if (num <= ARTE){
            result = "ART";
        } else if (num <= DEPORTE){
            result = "DEP";
        } else if (num <= LITERATURA){
            result = "LIT";
        } else if (num <= GEOGRAFIA){
            result = "GEO";
        } else if (num <= ENTRETENIMIENTO){
            result = "ENT";
        } else if (num <= CIENCIA){
            result = "CIE";
        }

        return result;
    }


    public void getQuestions(String idTheme){
        Log.e(null, "getQuestions: " + idTheme );
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.gameQuestions(getToken(), idTheme).enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Roulette.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<Question> questions = new ArrayList<>(response.body());
                Toast.makeText(Roulette.this, questions.get(0).getTheme(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Quiz.class);
                intent.putExtra("questions", questions);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }


}
