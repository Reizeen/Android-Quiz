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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ericarias.quiz.Interface.WebServiceClient;
import com.ericarias.quiz.Model.ResponseServer;
import com.ericarias.quiz.Model.User;
import com.ericarias.quiz.Model.Utilities;
import com.ericarias.quiz.R;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecoverPass extends AppCompatActivity {

    private ImageView fondoRecover;
    private ImageView fondoRecoverDos;

    private TextView title;
    private LinearLayout linearLayout;
    private EditText editTextEmail;
    private TextView textInfo;

    private Button btnSend;
    private Button btnReturn;

    private ProgressBar progressBar;
    private TextView textSendEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pass);

        fondoRecover = findViewById(R.id.fondoRecoverPass);
        fondoRecoverDos = findViewById(R.id.fondoRecoverPassDos);
        animacionFondo();

        title = findViewById(R.id.titleRecoverPass);
        linearLayout = findViewById(R.id.textEmailRecoverLayout);
        editTextEmail = findViewById(R.id.editTextEmailRecover);
        textInfo = findViewById(R.id.textInfoRecover);

        btnSend = findViewById(R.id.btnSend);
        btnReturn = findViewById(R.id.btnReturn);

        progressBar = findViewById(R.id.recover_progress);
        textSendEmail = findViewById(R.id.textSendEmail);
    }

    /**
     * Mostrar progress bar y ocultar el formulario de la actividad
     */
    private void showProgress(boolean show) {
        int gone = show ? View.GONE : View.VISIBLE;
        int visibility = show ? View.VISIBLE : View.GONE;

        progressBar.setVisibility(visibility);
        textSendEmail.setVisibility(visibility);
        title.setVisibility(gone);
        linearLayout.setVisibility(gone);
        editTextEmail.setVisibility(gone);
        textInfo.setVisibility(gone);
        btnSend.setVisibility(gone);
        btnReturn.setVisibility(gone);
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
            final float width = fondoRecover.getWidth();
            final float translationX = width * progress;
            fondoRecover.setTranslationX(translationX);
            fondoRecoverDos.setTranslationX(translationX - width);
        });

        animator.start();
    }

    /**
     * Llamada POST para enviar email al usuario
     */
    private void sendEmail(){
        showProgress(true);
        WebServiceClient client = Utilities.myRetrofit().create(WebServiceClient.class);
        client.sendEmail(new User(editTextEmail.getText().toString())).enqueue(new Callback<ResponseServer>() {
            @Override
            public void onResponse(Call<ResponseServer> call, Response<ResponseServer> response) {
                if (!response.isSuccessful()){
                    showProgress(false);
                    return;
                }

                if (response.body().getResp())
                    textInfo.setBackgroundResource(R.color.colorGreenDark);
                else
                    textInfo.setBackgroundResource(R.color.colorRed);

                textInfo.setText(response.body().getDesc());
                showProgress(false);
            }

            @Override
            public void onFailure(Call<ResponseServer> call, Throwable t) {
                showProgress(false);
                Log.e(null, "--> Error onFailure:" + t.getMessage());
            }
        });
    }

    /**
     * Evento onClick para llamar al metodo sendEmail()
     * y comprobar campo vacio
     * @param view
     */
    public void onClickSendEmail(View view) {
        UIUtil.hideKeyboard(this);
        if (editTextEmail.getText().toString().isEmpty()) {
            textInfo.setVisibility(View.VISIBLE);
            textInfo.setText("Escribe un Email");
        } else
            sendEmail();
    }

    /**
     * Cerrar actividad
     * @param view
     */
    public void onClickReturn(View view) {
        finish();
    }
}
