package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Game extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private LinearLayout linearTheme;
    private TextView textTema;
    private ProgressBar progressBar;
    private TextView textQuestion;
    private Button optionOne;
    private Button optionTwo;
    private Button optionThree;
    private Button optionFour;

    private ArrayList<Question> questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        constraintLayout = findViewById(R.id.layoutGame);
        linearTheme = findViewById(R.id.linearTheme);
        textTema = findViewById(R.id.textTheme);
        progressBar = findViewById(R.id.progressBarGame);
        textQuestion = findViewById(R.id.textQuestionGame);
        optionOne = findViewById(R.id.optionOne);
        optionTwo = findViewById(R.id.optionTwo);
        optionThree = findViewById(R.id.optionThree);
        optionFour = findViewById(R.id.optionFour);

        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        loadActivity();
        loadQuestion();
    }


    private void loadActivity() {
        textTema.setText(questions.get(0).getTheme().toUpperCase());
        switch (questions.get(0).getTheme()){
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

    private void loadQuestion() {
        textQuestion.setText(questions.get(0).getQuestion());
        List<String> answers = questions.get(0).getAnswers();
        Collections.shuffle(answers);
        optionOne.setText(answers.get(0));
        optionTwo.setText(answers.get(1));
        optionThree.setText(answers.get(2));
        optionFour.setText(answers.get(3));
    }
}
