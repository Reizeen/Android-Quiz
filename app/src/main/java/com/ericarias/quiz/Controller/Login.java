package com.ericarias.quiz.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.ericarias.quiz.Model.User;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;

public class Login extends AppCompatActivity {

    private ImageView fondoLogin;
    private ImageView fondoLoginDos;
    private TextView titleLogin;
    private EditText textUser;
    private TextInputLayout textUserLayout;
    private EditText textPass;
    private TextInputLayout textPassLayout;
    private TextView passReco;
    private TextView textLogin;
    private TextView textError;
    private Button btnLogin;
    private Button btnGoRegister;
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
        textUserLayout = findViewById(R.id.textUserLoginLayout);
        textPass = findViewById(R.id.textPassLogin);
        textPassLayout = findViewById(R.id.textPassLoginLayout);
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
        textUserLayout.setVisibility(gone);
        textPass.setVisibility(gone);
        textPassLayout.setVisibility(gone);
        textError.setVisibility(gone);
        passReco.setVisibility(gone);
        btnLogin.setVisibility(gone);
        btnGoRegister.setVisibility(gone);
    }


    /**
     * Llamada POST /signin con Retrofit
     */
    public void peticionLogin(){
        showProgress(true);
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.loginUser(new User(textUser.getText().toString(), textPass.getText().toString())).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if (!response.isSuccessful()){
                    showProgress(false);
                    errorAuth(true);
                    return;
                }

                saveAuth(response.body().getId(), response.body().getName(), response.body().getToken());
                errorAuth(false);
                Intent intent = new Intent(getApplicationContext(), Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showProgress(false);
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }


    /**
     * Guardar id y token en SharedPreferences
     * @param id
     * @param token
     */
    private void saveAuth(int id, String username, String token) {
        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("id", id);
        editor.putString("name", username);
        editor.putString("token", token);
        editor.commit();
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
        Utilities.hideKeyboard(getApplicationContext(), this.getCurrentFocus());
        peticionLogin();
    }


    /**
     * Evento onClick del btnGoRegister.
     * @param view
     */
    public void onClickGoRegister(View view){
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivityForResult(intent, Utilities.COD_REGISTER);
        errorAuth(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Utilities.COD_REGISTER && resultCode == RESULT_OK) {
            if (data.getBooleanExtra("register", false))
                Toast.makeText(this, data.getStringExtra("desc"), Toast.LENGTH_SHORT).show();
        }
    }
}
