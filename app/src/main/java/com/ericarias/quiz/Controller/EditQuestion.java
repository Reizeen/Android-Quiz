package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditQuestion extends AppCompatActivity {

    private Question question;
    private EditText textQuestion;
    private EditText textCorrect;
    private EditText textIncorrectOne;
    private EditText textIncorrectTwo;
    private EditText textIncorrectThree;
    private Spinner selectTheme;
    private ArrayAdapter<CharSequence> adapter;

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

    public String getToken() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getString("token", "null");
    }

    private void loadQuestion() {
        textQuestion.setText(question.getQuestion());
        textCorrect.setText(question.getRespcorrect());
        textIncorrectOne.setText(question.getRespaltone());
        textIncorrectTwo.setText(question.getRespalttwo());
        textIncorrectThree.setText(question.getRespaltthree());

        int pos = adapter.getPosition(question.getTheme_name());
        selectTheme.setSelection(pos);
    }

    private void saveQuestion() {
        question.setQuestion(textQuestion.getText().toString());
        question.setRespcorrect(textCorrect.getText().toString());
        question.setRespaltone(textIncorrectOne.getText().toString());
        question.setRespalttwo(textIncorrectTwo.getText().toString());
        question.setRespaltthree(textIncorrectThree.getText().toString());
        question.setTheme_name(selectTheme.getSelectedItem().toString());

    }

    private void editQuestion(){
        saveQuestion();
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.editQuestion(getToken(), question).enqueue(new Callback<ResponseServer>() {
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


    public void onClickEditQuestion(View view) {
        if (textQuestion.getText().toString().isEmpty() || textCorrect.getText().toString().isEmpty() || textIncorrectOne.getText().toString().isEmpty() || textIncorrectTwo.getText().toString().isEmpty() || textIncorrectThree.getText().toString().isEmpty())
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_LONG).show();
        else
            editQuestion();
    }
}
