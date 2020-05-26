package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ericarias.quiz.Controller.Adapters.AdapterResultQuestion;
import com.ericarias.quiz.Controller.Dialogs.ReportDialog;
import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.Points;
import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.Model.Report;
import com.ericarias.quiz.Model.ResponseServer;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EndQuiz extends AppCompatActivity implements ReportDialog.DialogListener {

    private ArrayList<Question> questions;
    private ArrayList<Integer> correctAnswers;
    private ArrayList<String> responseResult;

    private AdapterResultQuestion adapterResultQuestion;
    private RecyclerView recyclerView;
    private TextView textPoints;
    private int countCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_quiz);

        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        correctAnswers = getIntent().getIntegerArrayListExtra("correctAnswers");
        responseResult = getIntent().getStringArrayListExtra("responseResult");

        textPoints = findViewById(R.id.textPoints);
        countCorrect = countAnswers();


        adapterResultQuestion = new AdapterResultQuestion(questions, correctAnswers, responseResult);
        recyclerView = findViewById(R.id.recyclerResultQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterResultQuestion);
        adapterResultQuestion.setOnItemClickListener(position -> onClickReport(position));

        if (countCorrect != 0)
            setPoints();
    }

    /**
     * Evento onClick para Reportar pregunta
     * @param position
     */
    private void onClickReport(int position){
        Question question = questions.get(position);
        ReportDialog reportDialog = new ReportDialog(question.getId());
        reportDialog.show(getSupportFragmentManager(), "Reportar");
    }

    /**
     * Contar cuantas preguntas ha acertado el usuario
     * @return
     */
    private int countAnswers(){
        int count = 0;
        for (int x = 0; x < correctAnswers.size(); x++){
            if (correctAnswers.get(x) == 1)
                count++;
        }
        return count;
    }

    /**
     * Llamada PUT para aumentar los puntos
     */
    private void setPoints(){
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.addPoints(Utilities.getToken(this), Utilities.getUserID(this), countCorrect).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(EndQuiz.this, "ERROR: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                textPoints.setText("PUNTOS GANADOS: " + response.body().getResp());
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }


    /**
     * Llamada POST para enviar reporte
     * @param textReporting
     * @param idQuestion
     */
    @Override
    public void setReport(String textReporting, int idQuestion) {
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.addReport(Utilities.getToken(this), new Report(textReporting, idQuestion)).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(EndQuiz.this, "ERROR: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(EndQuiz.this, "Reportado!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    /**
     * Evento onClick para salir de la actividad
     * @param view
     */
    public void finishQuiz(View view) {
        finish();
    }
}

