package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.Model.ResponseServer;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AddQuestion extends AppCompatActivity {

    private EditText textQuestion;
    private EditText textCorrect;
    private EditText textIncorrectOne;
    private EditText textIncorrectTwo;
    private EditText textIncorrectThree;

    private Spinner selectTheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        textQuestion = findViewById(R.id.textQuestion);
        textCorrect = findViewById(R.id.textCorrect);
        textIncorrectOne = findViewById(R.id.textIncorrectOne);
        textIncorrectTwo = findViewById(R.id.textIncorrectTwo);
        textIncorrectThree = findViewById(R.id.textIncorrectThree);

        selectTheme = findViewById(R.id.addTheme);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.themes, R.layout.support_simple_spinner_dropdown_item);
        selectTheme.setAdapter(adapter);
    }

    /**
     * Llamada POST para insertar pregunta
     */
    public void insertQuestion(){

        List<String> answers = Arrays.asList(
                textCorrect.getText().toString(),
                textIncorrectOne.getText().toString(),
                textIncorrectTwo.getText().toString(),
                textIncorrectThree.getText().toString()
        );

        Question question = new Question(
                textQuestion.getText().toString(),
                answers,
                selectTheme.getSelectedItem().toString(),
                Utilities.getUsername(this));

        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.addQuestion(Utilities.getToken(this), question).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, retrofit2.Response<ResponseServer> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(AddQuestion.this, "ERROR: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                if (!response.body().getResp()){
                    Toast.makeText(AddQuestion.this, "ERROR: " + response.body().getDesc(), Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), ViewQuestions.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    /**
     * Evento onClick para llamar al metodo insertQuestion()
     * controlando que los campos no esten vacios
     * @param view
     */
    public void onClickAddQuestion(View view) {
        if (textQuestion.getText().toString().isEmpty() || textCorrect.getText().toString().isEmpty() || textIncorrectOne.getText().toString().isEmpty() || textIncorrectTwo.getText().toString().isEmpty() || textIncorrectThree.getText().toString().isEmpty())
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_LONG).show();
        else
            insertQuestion();
    }
}
