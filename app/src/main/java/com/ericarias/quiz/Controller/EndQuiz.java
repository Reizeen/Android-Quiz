package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ericarias.quiz.Controller.Adapters.AdapterResultQuestion;
import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.TreeMap;

public class EndQuiz extends AppCompatActivity {

    private ArrayList<Question> questions;
    private ArrayList<Integer> correctAnswers;
    private ArrayList<String> responseResult;

    private AdapterResultQuestion adapterResultQuestion;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_quiz);

        questions = (ArrayList<Question>) getIntent().getSerializableExtra("questions");
        correctAnswers = getIntent().getIntegerArrayListExtra("correctAnswers");
        responseResult = getIntent().getStringArrayListExtra("responseResult");

        adapterResultQuestion = new AdapterResultQuestion(questions, correctAnswers, responseResult);
        recyclerView = findViewById(R.id.recyclerResultQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterResultQuestion);

        adapterResultQuestion.setOnItemClickListener(position -> onClickReport(position));

    }

    private void onClickReport(int position){
        Question question = questions.get(position);
        /* En Construccion */
    }
}

