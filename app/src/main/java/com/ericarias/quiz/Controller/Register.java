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
import com.ericarias.quiz.Model.ResponseServer;
import com.ericarias.quiz.Model.User;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;
import com.google.android.material.textfield.TextInputLayout;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import retrofit2.Call;
import retrofit2.Callback;

public class Register extends AppCompatActivity {

    private ImageView fondoRegister;
    private ImageView fondoRegisterDos;
    private TextView titleRegister;
    private EditText textUser;
    private TextInputLayout textUserLayout;
    private EditText textEmail;
    private TextInputLayout textEmailLayout;
    private EditText textPass;
    private TextInputLayout textPassLayout;
    private TextView textError;
    private Button btnRegister;
    private Button btnGoLogin;
    private View mProgressView;
    private TextView textRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fondoRegister = findViewById(R.id.fondoRegister);
        fondoRegisterDos = findViewById(R.id.fondoRegisterDos);
        animacionFondo();

        titleRegister = findViewById(R.id.titleRegister);
        textUser = findViewById(R.id.textUserRegister);
        textUserLayout = findViewById(R.id.textUserRegisterLayout);
        textEmail = findViewById(R.id.textEmailRegister);
        textEmailLayout = findViewById(R.id.textEmailRegisterLayout);
        textPass = findViewById(R.id.textPassRegister);
        textPassLayout = findViewById(R.id.textPassRegisterLayout);
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
        animator.addUpdateListener(animation -> {
            final float progress = (float) animation.getAnimatedValue();
            final float width = fondoRegister.getWidth();
            final float translationX = width * progress;
            fondoRegister.setTranslationX(translationX);
            fondoRegisterDos.setTranslationX(translationX - width);
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
        textUserLayout.setVisibility(gone);
        textUser.setVisibility(gone);
        textEmailLayout.setVisibility(gone);
        textEmail.setVisibility(gone);
        textPassLayout.setVisibility(gone);
        textPass.setVisibility(gone);
        textError.setVisibility(gone);
        btnRegister.setVisibility(gone);
        btnGoLogin.setVisibility(gone);
    }


    /**
     * Llamada POST /si con Retrofit
     */
    public void peticionRegister(){
        showProgress(true);
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.registerUser(new User(textUser.getText().toString(), textEmail.getText().toString(), textPass.getText().toString())).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, retrofit2.Response<ResponseServer> response) {
                if (!response.isSuccessful()){
                    showProgress(false);
                    Toast.makeText(Register.this, "ERROR " + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                if (!response.body().getResp()){
                    showProgress(false);
                    errorAuth(true, response.body().getDesc());
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), Login.class);
                intent.putExtra("register", true);
                intent.putExtra("desc", response.body().getDesc());
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
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
        UIUtil.hideKeyboard(this);
        if (textUser.getText().toString().isEmpty() || textPass.getText().toString().isEmpty() || textEmail.getText().toString().isEmpty())
            errorAuth(true, "Tienes campos vacios");
        else
            peticionRegister();
    }

    /**
     * Mostrar error de registro por parte del usuario
     */
    private void errorAuth(boolean show, String mensajeError) {
        textError.setVisibility(show ? View.VISIBLE : View.GONE);
        textError.setText(mensajeError);
    }

    /**
     * Click Login
     * @param view
     */
    public void onClickGoLogin(View view){
        finish();
    }

}
