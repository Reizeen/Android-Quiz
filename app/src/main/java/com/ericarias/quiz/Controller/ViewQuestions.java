package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ericarias.quiz.Controller.Adapters.AdapterQuestions;
import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.Model.ResponseServer;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewQuestions extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterQuestions adapterQuestions;
    private ArrayList<Question> questions;

    private int id;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions);

        recyclerView = findViewById(R.id.recyclerQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarCredenciales();
        getQuestions();
    }

    public void cargarCredenciales() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        token = preferences.getString("token", "null");
        id = preferences.getInt("id", 0);
    }

    private void getQuestions() {
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.userQuestions(token, id).enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ViewQuestions.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                questions = new ArrayList<>(response.body());
                adapterQuestions = new AdapterQuestions(questions);
                recyclerView.setAdapter(adapterQuestions);

                adapterQuestions.setOnItemClickListener(new AdapterQuestions.OnItemClickListener() {
                    @Override
                    public void onEditClick(int position) {

                    }

                    @Override
                    public void onDeleteClick(int position) {
                        deleteQuestion(position);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }



    public void deleteQuestion(int position){
        Question question = questions.get(position);
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.deleteQuestion(token, question.getId()).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ViewQuestions.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                questions.remove(position);
                adapterQuestions.notifyItemChanged(position);
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }
}

