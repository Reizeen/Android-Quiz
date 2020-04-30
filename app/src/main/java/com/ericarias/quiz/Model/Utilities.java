package com.ericarias.quiz.Model;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utilities {

    private static HttpLoggingInterceptor logInterceptor;
    private static OkHttpClient.Builder httpClientBuilder;
    private static Retrofit retrofit;

    /**
     * URL de la API
     */
    public static final String URL_API = "https://dam2.ieslamarisma.net/2019/eric/public/";

    /**
     * Codigos de respuesta
     */
    public static final int COD_REGISTER = 101;
    public static final int COD_ADD_QUESTION = 102;

    /**
     * Oculta el teclado
     * @param context
     * @param view
     */
    public static void hideKeyboard(Context context, View view) {
        if (view != null){
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static Retrofit myRetrofit(){
        // Permite ver los datos que se envian y se reciben
        logInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(logInterceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        return retrofit;
    }
}
