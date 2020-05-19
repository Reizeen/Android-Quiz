package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.R;
import java.util.ArrayList;
import java.util.Collections;

public class Quiz extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private LinearLayout linearTheme;
    private TextView textTheme;
    private TextView textQuestion;
    private Button optionOne;
    private Button optionTwo;
    private Button optionThree;
    private Button optionFour;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    private ImageView bgResult;
    private TextView resultQuestion;
    private Button btnNext;

    private ArrayList<Question> questions;
    private ArrayList<Integer> correctAnswers;
    private ArrayList<String> responseResult;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        constraintLayout = findViewById(R.id.layoutGame);
        linearTheme = findViewById(R.id.linearTheme);
        textTheme = findViewById(R.id.textTheme);
        textQuestion = findViewById(R.id.textQuestionGame);
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);

        progressBar = findViewById(R.id.progressBarGame);

        bgResult = findViewById(R.id.bgResult);
        resultQuestion = findViewById(R.id.resultQuestion);
        btnNext = findViewById(R.id.btnNext);

        position = 0;
        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        correctAnswers = new ArrayList<>();
        responseResult = new ArrayList<>();

        loadActivity();
        loadQuestion(position);
        progressBarStart();
        onClickOptions();
    }


    /**
     * Temporizador
     * "En contruccion"
     */
    public void progressBarStart(){
        new Thread(() -> {
            while (progressStatus < 100){
                progressStatus++;
                android.os.SystemClock.sleep(50);
                handler.post(() -> progressBar.setProgress(progressStatus));
            }
        });
    }


    /**
     * Cargar el diseño de la actividad según el tema
     */
    private void loadActivity() {
        textTheme.setText(questions.get(position).getTheme().toUpperCase());
        switch (questions.get(position).getTheme()){
            case "Arte":
                constraintLayout.setBackgroundResource(R.drawable.background_art);
                linearTheme.setBackgroundResource(R.color.colorART);
                break;
            case "Ciencia":
                constraintLayout.setBackgroundResource(R.drawable.background_cie);
                linearTheme.setBackgroundResource(R.color.colorCIE);

                break;
            case "Deporte":
                constraintLayout.setBackgroundResource(R.drawable.background_dep);
                linearTheme.setBackgroundResource(R.color.colorDEP);
                break;
            case "Entretenimiento":
                constraintLayout.setBackgroundResource(R.drawable.background_ent);
                linearTheme.setBackgroundResource(R.color.colorENT);
                break;
            case "Geografía":
                constraintLayout.setBackgroundResource(R.drawable.background_geo);
                linearTheme.setBackgroundResource(R.color.colorGEO);
                break;
            case "Historia":
                constraintLayout.setBackgroundResource(R.drawable.background_his);
                linearTheme.setBackgroundResource(R.color.colorHIS);
                break;
            case "Literatura":
                constraintLayout.setBackgroundResource(R.drawable.background_lit);
                linearTheme.setBackgroundResource(R.color.colorLIT);
                break;
            case "Matematicas":
                constraintLayout.setBackgroundResource(R.drawable.background_mat);
                linearTheme.setBackgroundResource(R.color.colorMAT);
                break;
        }
    }

    /**
     * Carga la pregunta y las opciones del juego
     * Crea una nueva lista para establecer las opciones
     * de manera aleatoria.
     */
    private void loadQuestion(int pos) {
        textQuestion.setText(questions.get(pos).getQuestion());

        ArrayList<String> options = new ArrayList<>();
        for (int x = 0; x < 4; x++)
            options.add(questions.get(pos).getAnswers().get(x));
        Collections.shuffle(options);

        optionOne.setText(options.get(0));
        optionTwo.setText(options.get(1));
        optionThree.setText(options.get(2));
        optionFour.setText(options.get(3));
    }


    /**
     * Eventos onClick para cada una de las opciones
     */
    public void onClickOptions(){
        optionOne.setOnClickListener(v -> checkAnswer(optionOne.getText().toString()));
        optionTwo.setOnClickListener(v -> checkAnswer(optionTwo.getText().toString()));
        optionThree.setOnClickListener(v -> checkAnswer(optionThree.getText().toString()));
        optionFour.setOnClickListener(v -> checkAnswer(optionFour.getText().toString()));
    }

    /**
     * Mostrar resultado de la pregunta
     */
    private void showResult(boolean show) {
        int visibility = show ? View.VISIBLE : View.GONE;
        bgResult.setVisibility(visibility);
        resultQuestion.setVisibility(visibility);
        btnNext.setVisibility(visibility);
    }

    /**
     * Almacena la opcion elegida por el usuario.
     * Comprueba la opcion marcada con la respuesta correcta.
     * La respuesta correcta está en la posicion 0
     * del array 'questions'.
     * @param answer
     */
    public void checkAnswer(String answer){
        responseResult.add(answer);

        if (answer.equals(questions.get(position).getAnswers().get(0))){
            resultQuestion.setText("CORRECTO");
            resultQuestion.setTextColor(Color.GREEN);
            showResult(true);
            correctAnswers.add(1);

        } else {
            resultQuestion.setText("INCORRECTO");
            resultQuestion.setTextColor(Color.RED);
            showResult(true);
            correctAnswers.add(0);
        }
    }

    /**
     * Evento onClick de avanzar a la siguiente pegunta
     * o terminar el juego.
     * @param view
     */
    public void nextQuestion(View view){
        if (position == 4){
            Intent intent = new Intent(getApplicationContext(), EndQuiz.class);
            intent.putExtra("questions", questions);
            intent.putExtra("correctAnswers", correctAnswers);
            intent.putExtra("responseResult", responseResult);
            startActivity(intent);
            finish();
        } else {
            position++;
            loadQuestion(position);
            showResult(false);
        }
    }
}