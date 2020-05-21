package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.ResponseServer;
import com.ericarias.quiz.Model.User;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    private TextView textUserName;
    private TextView textEmail;
    private TextView textResponse;
    private EditText textPassCurrent;
    private EditText textNewPass;
    private EditText textRepNewPass;

    private RelativeLayout relativeLayoutOne;
    private RelativeLayout relativeLayoutTwo;
    private RelativeLayout relativeLayoutThree;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textUserName = findViewById(R.id.textUserNameProfile);
        textEmail = findViewById(R.id.textEmailProfile);
        textResponse = findViewById(R.id.textResponseProfile);
        textPassCurrent = findViewById(R.id.textPassCurrent);
        textNewPass = findViewById(R.id.textNewPass);
        textRepNewPass = findViewById(R.id.textRepNewPass);

        relativeLayoutOne = findViewById(R.id.relativeLayoutProfile1);
        relativeLayoutTwo = findViewById(R.id.relativeLayoutProfile2);
        relativeLayoutThree = findViewById(R.id.relativeLayoutProfile3);

        progressBar = findViewById(R.id.progressBarProfile);

        getUser();
    }

    /**
     * Mostrar progress bar y ocultar el contenido de activity_view_questions
     */
    private void showProgress(boolean show) {
        int gone = show ? View.GONE : View.VISIBLE;
        int visibility = show ? View.VISIBLE : View.GONE;

        progressBar.setVisibility(visibility);
        relativeLayoutOne.setVisibility(gone);
        relativeLayoutTwo.setVisibility(gone);
        relativeLayoutThree.setVisibility(gone);
        textResponse.setVisibility(gone);
    }

    private void getUser() {
        showProgress(true);
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.getUser(Utilities.getToken(this), Utilities.getUserID(this)).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Profile.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgress(false);
                textUserName.setText(response.body().getName());
                textEmail.setText(response.body().getEmail());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    private void modPass(){
        showProgress(true);
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.modPass(Utilities.getToken(this),
                textUserName.getText().toString(),
                textPassCurrent.getText().toString(),
                textNewPass.getText().toString()).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(Profile.this, "ERROR " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (response.body().getResp())
                    textResponse.setTextColor(Color.GREEN);
                else
                    textResponse.setTextColor(Color.RED);

                textResponse.setText(response.body().getDesc());
                showProgress(false);
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    public void onClickPass(View view) {
        UIUtil.hideKeyboard(this);

        if (textPassCurrent.getText().toString().isEmpty() || textNewPass.getText().toString().isEmpty() || textRepNewPass.getText().toString().isEmpty()) {
            Toast.makeText(this, "Rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!textNewPass.getText().toString().equals(textRepNewPass.getText().toString())) {
            Toast.makeText(this, "Contraseña reptida incorrecta", Toast.LENGTH_SHORT).show();
            return;
        }
        modPass();
    }
}
