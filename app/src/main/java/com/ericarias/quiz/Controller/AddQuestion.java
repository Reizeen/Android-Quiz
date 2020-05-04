package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.Model.Response;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;

public class AddQuestion extends AppCompatActivity {


    private EditText textQuestion;
    private EditText textCorrect;
    private EditText textIncorrectOne;
    private EditText textIncorrectTwo;
    private EditText textIncorrectThree;
    private Spinner selectTheme;
    private int id;
    private String token;

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

        carggarCredenciales();
    }

    /**
     * Cargar id del usuario
     */
    public void carggarCredenciales() {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        id = preferences.getInt("id", 0);
        token = preferences.getString("token", "null");
    }

    /**
     * Llamada POST para insertar pregunta
     */
    public void insertQuestion(){

        Question question = new Question(
                textQuestion.getText().toString(),
                textCorrect.getText().toString(),
                textIncorrectOne.getText().toString(),
                textIncorrectTwo.getText().toString(),
                textIncorrectThree.getText().toString(),
                id, // id del usuario
                selectTheme.getSelectedItem().toString().substring(0,3));

        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.addQuestion(token, question).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(AddQuestion.this, "ERROR-S: " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                if (!response.body().getResp()){
                    Toast.makeText(AddQuestion.this, "ERROR: " + response.body().getDesc(), Toast.LENGTH_LONG).show();
                    return;
                }

                Toast.makeText(AddQuestion.this, response.body().getDesc(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    /**
     * Evento onClick del btnAddQuestion
     * @param view
     */
    public void onClickAddQuestion(View view) {

        insertQuestion();

    }


}
