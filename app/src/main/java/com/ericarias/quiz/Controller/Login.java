package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Intent;
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
    private TextView titleLogin;
    private EditText textUser;
    private EditText textPass;
    private TextView passReco;
    private TextView textLogin;
    private TextView textError;
    private Button btnLogin;
    private Button btnGoRegister;

    private Retrofit retrofit;
    private HttpLoggingInterceptor logInterceptor;
    private OkHttpClient.Builder httpClientBuilder;
    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fondoLogin = findViewById(R.id.fondoLogin);
        fondoLoginDos = findViewById(R.id.fondoLoginDos);
        animacionFondo();

        titleLogin = findViewById(R.id.titleLogin);
        textUser = findViewById(R.id.textUserLogin);
        textPass = findViewById(R.id.textPassLogin);
        passReco = findViewById(R.id.passReco);
        textLogin = findViewById(R.id.textLogin);
        textError = findViewById(R.id.textErrorLogin);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * Animando el fondo de la actividad.
     */
    private void animacionFondo(){
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
     * Mostrar progress bar y ocultar el formulario de activity_login
     */
    private void showProgress(boolean show) {
        int gone = show ? View.GONE : View.VISIBLE;
        int visibility = show ? View.VISIBLE : View.GONE;

        mProgressView.setVisibility(visibility);
        textLogin.setVisibility(visibility);
        titleLogin.setVisibility(gone);
        textUser.setVisibility(gone);
        textPass.setVisibility(gone);
        textError.setVisibility(gone);
        passReco.setVisibility(gone);
        btnLogin.setVisibility(gone);
        btnGoRegister.setVisibility(gone);
    }


    /**
     * Llamada POST /signin con Retrofit
     */
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
                    errorAuth(true);
                    return;
                }
                errorAuth(false);
                Toast.makeText(Login.this, response.body().getDesc(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<RespHTTP> call, Throwable t) {
                showProgress(false);
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    /**
     * Mostrar error de autentificacion por parte del usuario
     */
    private void errorAuth(boolean show) {
        textError.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * Evento onClick del btn confirmar.
     * @param view
     */
    public void onClickLogin(View view){
        peticionLogin();
    }

    /**
     * Evento onClick del btnGoRegister.
     * @param view
     */
    public void onClickGoRegister(View view){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }
}
