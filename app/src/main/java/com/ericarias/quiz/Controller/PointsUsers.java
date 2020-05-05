package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.Points;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointsUsers extends AppCompatActivity {

    private ArrayList<Points> points;
    private AdapterPoints adapterPoints;
    private RecyclerView recyclerPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_users);

        recyclerPoints = findViewById(R.id.recyclerPoints);
        recyclerPoints.setLayoutManager(new LinearLayoutManager(this));
        getPoints();
    }

    public String carggarCredenciales() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getString("token", "null");
    }

    public void getPoints(){
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.allPoints(carggarCredenciales()).enqueue(new Callback<List<Points>>() {
            @Override
            public void onResponse(Call<List<Points>> call, Response<List<Points>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(PointsUsers.this, "ERROR: ", Toast.LENGTH_SHORT).show();
                }

                points = new ArrayList<>(response.body());
                adapterPoints = new AdapterPoints(PointsUsers.this, points);
                recyclerPoints.setAdapter(adapterPoints);
            }

            @Override
            public void onFailure(Call<List<Points>> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }
}
