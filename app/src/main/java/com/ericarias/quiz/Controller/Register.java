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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.RespHTTP;
import com.ericarias.quiz.Model.Usuario;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import org.w3c.dom.Text;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    private ImageView fondoRegister;
    private ImageView fondoRegisterDos;
    private TextView titleRegister;
    private EditText textUser;
    private EditText textEmail;
    private EditText textPass;
    private TextView textError;
    private Button btnRegister;
    private Button btnGoLogin;
    private View mProgressView;
    private TextView textRegister;
    private Retrofit retrofit;
    private HttpLoggingInterceptor logInterceptor;
    private OkHttpClient.Builder httpClientBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fondoRegister = findViewById(R.id.fondoRegister);
        fondoRegisterDos = findViewById(R.id.fondoRegisterDos);
        animacionFondo();

        titleRegister = findViewById(R.id.titleRegister);
        textUser = findViewById(R.id.textUserRegister);
        textEmail = findViewById(R.id.textEmailRegister);
        textPass = findViewById(R.id.textPassRegister);
        textError = findViewById(R.id.textErrorRegister);
        btnRegister = findViewById(R.id.btnRegister);
        btnGoLogin = findViewById(R.id.btnGoLogin);
        mProgressView = findViewById(R.id.register_progress);
        textRegister = findViewById(R.id.textRegister);
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
                final float width = fondoRegister.getWidth();
                final float translationX = width * progress;
                fondoRegister.setTranslationX(translationX);
                fondoRegisterDos.setTranslationX(translationX - width);
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
        textRegister.setVisibility(visibility);
        titleRegister.setVisibility(gone);
        textUser.setVisibility(gone);
        textEmail.setVisibility(gone);
        textPass.setVisibility(gone);
        textError.setVisibility(gone);
        btnRegister.setVisibility(gone);
        btnGoLogin.setVisibility(gone);
    }

    /**
     * Llamada POST /si con Retrofit
     */
    private void peticionRegister() {
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
        client.registerUser(new Usuario(textUser.getText().toString(), textEmail.getText().toString(), textPass.getText().toString())).enqueue(new Callback<RespHTTP>() {
            @Override
            public void onResponse(Call<RespHTTP> call, Response<RespHTTP> response) {
                showProgress(false);

                // Se cumple si el error no es un 2XX
                if (!response.isSuccessful()) {
                    Toast.makeText(Register.this, response.code(), Toast.LENGTH_SHORT).show();
                    errorAuth(true);
                    return;
                }

                // Se cumple si la respuesta es False. Para controlar el username y email.
                Log.i(null, "---->: " + response.body().getResp());
                if(!response.body().getResp()){
                    textError.setText(response.body().getDesc());
                    errorAuth(true);
                    return;
                }

                errorAuth(false);
                Toast.makeText(Register.this, response.body().getDesc(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RespHTTP> call, Throwable t) {
                showProgress(false);
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    /**
     * Evento onClick del btnRegister
     * @param view
     */
    public void onClickRegister(View view){
        peticionRegister();
    }

    /**
     * Mostrar error de registro por parte del usuario
     */
    private void errorAuth(boolean show) {
        textError.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void onClickGoLogin(View view){
        finish();
    }


}
