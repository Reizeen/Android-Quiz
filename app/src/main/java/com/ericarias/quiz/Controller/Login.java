package com.ericarias.quiz.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;

import com.ericarias.quiz.R;

public class Login extends AppCompatActivity {


    private ImageView fondoLogin;
    private ImageView fondoLoginDos;
    private EditText textUser;
    private EditText textPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fondoLogin = findViewById(R.id.fondoLogin);
        fondoLoginDos = findViewById(R.id.fondoLoginDos);
        animacionFondo();

        textUser = findViewById(R.id.userText);
        textPass = findViewById(R.id.passText);

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
}
