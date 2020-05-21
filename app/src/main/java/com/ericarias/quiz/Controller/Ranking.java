package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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

    private TextView titleRanking;
    private TextView textPosUser;
    private TextView textUsername;
    private TextView textPoints;

    private CardView cardViewUser;
    private LinearLayout linearLayoutPorgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        recyclerView = findViewById(R.id.recyclerPoints);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        titleRanking = findViewById(R.id.titleRanking);
        textPoints = findViewById(R.id.pointsUserRanking);
        textPosUser = findViewById(R.id.posUserRanking);
        textUsername = findViewById(R.id.userNameRanking);

        cardViewUser = findViewById(R.id.cardViewUserRanking);
        linearLayoutPorgress = findViewById(R.id.rankingProgress);

        getPoints();
    }

    /**
     * Mostrar progress bar y ocultar el contenido de activity_ranking
     */
    private void showProgress(boolean show) {
        int gone = show ? View.GONE : View.VISIBLE;
        int visibility = show ? View.VISIBLE : View.GONE;

        linearLayoutPorgress.setVisibility(visibility);
        titleRanking.setVisibility(gone);
        cardViewUser.setVisibility(gone);
        recyclerView.setVisibility(gone);
    }

    /**
     * Llamada GET para obtener todos los puntos del la BD
     */
    public void getPoints(){
        showProgress(true);
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.allPoints(Utilities.getToken(this)).enqueue(new Callback<List<Points>>() {
            @Override
            public void onResponse(Call<List<Points>> call, Response<List<Points>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(Ranking.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                }

                pointsList = new ArrayList<>(response.body());
                adapterRanking = new AdapterRanking(Ranking.this, pointsList);
                recyclerView.setAdapter(adapterRanking);
                getPointsUser();
                showProgress(false);
            }

            @Override
            public void onFailure(Call<List<Points>> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    /**
     * Metodo para obtener los puntos del usuario conectado
     */
    public void getPointsUser(){
        for (Points p : pointsList) {
            if (p.getName().equals(Utilities.getUsername(this))){
                textUsername.setText("Tu posicion " + p.getName());
                textPosUser.setText(String.valueOf(pointsList.indexOf(p) + 1));
                textPoints.setText("Puntos: " + p.getPoints());
            }
        }
    }
}
