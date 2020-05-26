package com.ericarias.quiz.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

    private TextView alert;
    private RecyclerView recyclerView;
    private AdapterQuestions adapterQuestions;
    private ArrayList<Question> questions;
    private LinearLayout progress;
    private TextView titleQuestions;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_questions);

        progress = findViewById(R.id.questionsProgress);
        titleQuestions = findViewById(R.id.titleViewQuestions);
        alert = findViewById(R.id.alertViewQuestions);
        recyclerView = findViewById(R.id.recyclerQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getQuestions();
    }

    /**
     * Mostrar progress bar y ocultar el contenido de la actividad
     */
    private void showProgress(boolean show) {
        int gone = show ? View.GONE : View.VISIBLE;
        int visibility = show ? View.VISIBLE : View.GONE;

        progress.setVisibility(visibility);
        titleQuestions.setVisibility(gone);
        recyclerView.setVisibility(gone);
    }

    /**
     * Llamada GET para mostrar todas las preguntas del usuario conectado
     */
    private void getQuestions() {
        showProgress(true);
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.userQuestions(Utilities.getToken(this), Utilities.getUserID(this)).enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                showProgress(false);
                if (!response.isSuccessful()) {
                    Toast.makeText(ViewQuestions.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                questions = new ArrayList<>(response.body());
                if (questions.size() == 0) {
                    alert.setVisibility(View.VISIBLE);
                    return;
                }

                adapterQuestions = new AdapterQuestions(questions);
                recyclerView.setAdapter(adapterQuestions);

                adapterQuestions.setOnItemClickListener(new AdapterQuestions.OnItemClickListener() {
                    @Override
                    public void onEditClick(int position) {
                        editQuestion(position);
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

    /**
     * Evento onClick para abrir una nueva actividad y
     * modificar una pregunta especifica
     * @param position
     */
    public void editQuestion(int position){
        this.position = position;
        Question question = questions.get(position);
        Intent intent = new Intent(getApplicationContext(), EditQuestion.class);
        intent.putExtra("question", question);
        startActivityForResult(intent, Utilities.COD_EDIT_QUESTION);
    }

    /**
     * Evento onClick para borrar una pregunta especifica
     * @param position
     */
    public void deleteQuestion(int position){
        Question question = questions.get(position);
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.deleteQuestion(Utilities.getToken(this), question.getId()).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ViewQuestions.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                questions.remove(position);
                adapterQuestions.notifyItemChanged(position);
                adapterQuestions.notifyItemRangeChanged(position, questions.size());
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    /**
     * Actualiza el RecyclerView si se ha modificado una pregunta
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Utilities.COD_EDIT_QUESTION && resultCode == RESULT_OK){
            Question question = (Question) data.getSerializableExtra("question");
            questions.set(position, question);
            adapterQuestions.notifyItemChanged(position);
            Toast.makeText(this, "Pregunta Modificada", Toast.LENGTH_SHORT).show();
        }
    }
}

