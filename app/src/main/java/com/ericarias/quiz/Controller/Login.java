package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.RespHTTP;
import com.ericarias.quiz.Model.Usuario;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {


    private ImageView fondoLogin;
    private ImageView fondoLoginDos;
    private EditText textUser;
    private EditText textPass;
    private TextView passReco;
    private Button btnLogin;
    private Button btnRegister;

    private Retrofit retrofit;
    private HttpLoggingInterceptor logInterceptor;
    private OkHttpClient.Builder httpClientBuilder;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        fondoLogin = findViewById(R.id.fondoLogin);
        fondoLoginDos = findViewById(R.id.fondoLoginDos);
        animacionFondo();

        textUser = findViewById(R.id.userText);
        textPass = findViewById(R.id.passText);
        passReco = findViewById(R.id.passReco);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        mProgressView = findViewById(R.id.login_progress);

    }

    /**
     * Animando el fondo de la actividad.
     */
    public void animacionFondo(){
        // Object Animator (Animacion)
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);

        // Se repita la animacion cada vez que termine.
        animator.setRepeatCount(ValueAnimator.INFINITE);

        // Controla la velocidad y la aceleración de los cambios de la animación.
        animator.setInterpolator(new LinearInterpolator());

        // Tiempo que va ha tardar en ir del 0 al 1;
        animator.setDuration(10000);

        // Actualiza posiciones cada vez que cambie los valores del animator
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = fondoLogin.getWidth();
                final float translationX = width * progress;
                fondoLogin.setTranslationX(translationX);
                fondoLoginDos.setTranslationX(translationX - width);
            }
        });

        animator.start();
    }


    /**
     * Mostrar progress bar y ocultar el formulario de login
     */
    private void showProgress(boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);

        int visibility = show ? View.GONE : View.VISIBLE;
        textUser.setVisibility(visibility);
        textPass.setVisibility(visibility);
        passReco.setVisibility(visibility);
        btnLogin.setVisibility(visibility);
        btnRegister.setVisibility(visibility);
    }

    public void peticionLogin(){
        // Permite ver los datos que se envian y se reciben
        logInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(logInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        showProgress(true);
        WebServiceClient client = retrofit.create(WebServiceClient.class);
        client.loginUser(new Usuario(textUser.getText().toString(), textPass.getText().toString())).enqueue(new Callback<RespHTTP>() {
            @Override
            public void onResponse(Call<RespHTTP> call, Response<RespHTTP> response) {
                showProgress(false);
                if (!response.isSuccessful()) {
                    Toast.makeText(Login.this, "-------- Error: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(Login.this, response.body().getDesc(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RespHTTP> call, Throwable t) {
                showProgress(false);
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    public void onClickLogin(View view){

        peticionLogin();

    }
}
