package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ericarias.quiz.Controller.Adapters.AdapterRanking;
import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.Points;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ranking extends AppCompatActivity {

    private ArrayList<Points> pointsList;
    private AdapterRanking adapterRanking;
    private RecyclerView recyclerView;
    private TextView textPosUser;
    private TextView textUsername;
    private TextView textPoints;

    private String token;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        recyclerView = findViewById(R.id.recyclerPoints);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        textPoints = findViewById(R.id.pointsUserRanking);
        textPosUser = findViewById(R.id.posUserRanking);
        textUsername = findViewById(R.id.userNameRanking);

        loadAuth();
        getPoints();
    }

    public void loadAuth() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        token = preferences.getString("token", "null");
        username = preferences.getString("name", "null");
    }

    public void getPoints(){
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.allPoints(token).enqueue(new Callback<List<Points>>() {
            @Override
            public void onResponse(Call<List<Points>> call, Response<List<Points>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Ranking.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                }

                pointsList = new ArrayList<>(response.body());
                adapterRanking = new AdapterRanking(Ranking.this, pointsList);
                recyclerView.setAdapter(adapterRanking);
                getPointsUser();
            }

            @Override
            public void onFailure(Call<List<Points>> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    public void getPointsUser(){
        for (Points p : pointsList) {
            if (p.getName().equals(username)){
                textUsername.setText("Tu posicion " + p.getName());
                textPosUser.setText(String.valueOf(pointsList.indexOf(p) + 1));
                textPoints.setText("Puntos: " + p.getPoints());
            }
        }
    }
}
