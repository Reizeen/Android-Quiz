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
import retrofit2.Response;

public class EditQuestion extends AppCompatActivity {

    private Question question;
    private Spinner selectTheme;
    private ArrayAdapter<CharSequence> adapter;

    private EditText textQuestion;
    private EditText textCorrect;
    private EditText textIncorrectOne;
    private EditText textIncorrectTwo;
    private EditText textIncorrectThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);

        question = (Question) getIntent().getSerializableExtra("question");
        textQuestion = findViewById(R.id.textQuestionEdit);
        textCorrect = findViewById(R.id.textCorrectEdit);
        textIncorrectOne = findViewById(R.id.textIncorrectOneEdit);
        textIncorrectTwo = findViewById(R.id.textIncorrectTwoEdit);
        textIncorrectThree = findViewById(R.id.textIncorrectThreeEdit);

        selectTheme = findViewById(R.id.editTheme);
        adapter = ArrayAdapter.createFromResource(this, R.array.themes, R.layout.support_simple_spinner_dropdown_item);
        selectTheme.setAdapter(adapter);

        loadQuestion();
    }

    /**
     * Cargar la informacion de la pregunta en la actividad
     */
    private void loadQuestion() {
        textQuestion.setText(question.getQuestion());
        textCorrect.setText(question.getAnswers().get(0));
        textIncorrectOne.setText(question.getAnswers().get(1));
        textIncorrectTwo.setText(question.getAnswers().get(2));
        textIncorrectThree.setText(question.getAnswers().get(3));

        int pos = adapter.getPosition(question.getTheme());
        selectTheme.setSelection(pos);
    }

    /**
     * Guardar la informacion de la pregunta
     */
    private void saveQuestion() {
        List<String> answers = Arrays.asList(
                textCorrect.getText().toString(),
                textIncorrectOne.getText().toString(),
                textIncorrectTwo.getText().toString(),
                textIncorrectThree.getText().toString()
        );

        question.setQuestion(textQuestion.getText().toString());
        question.setAnswers(answers);
        question.setTheme(selectTheme.getSelectedItem().toString());
    }

    /**
     * Llamada PUT para modificar la pregunta
     */
    private void editQuestion(){
        saveQuestion();
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.editQuestion(Utilities.getToken(this), question).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(EditQuestion.this, "ERROR: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), ViewQuestions.class);
                intent.putExtra("question", question);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }


    /**
     * Evento onClick para llamar al metodo editQuestion()
     * controlando que los campos no esten vacios
     * @param view
     */
    public void onClickEditQuestion(View view) {
        if (textQuestion.getText().toString().isEmpty() || textCorrect.getText().toString().isEmpty() || textIncorrectOne.getText().toString().isEmpty() || textIncorrectTwo.getText().toString().isEmpty() || textIncorrectThree.getText().toString().isEmpty())
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_LONG).show();
        else
            editQuestion();
    }
}
