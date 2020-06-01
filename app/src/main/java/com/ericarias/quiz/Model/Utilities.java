package com.ericarias.quiz.Model;

import android.content.Context;
import android.content.SharedPreferences;

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
    public static final int COD_EDIT_QUESTION = 103;

    /**
     * Metodo para la obtencion de Retrofit
     * @return
     */
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

    /**
     * Obtiene el identificador del usuario
     * @param context
     * @return
     */
    public static int getUserID(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getInt("id", 0);
    }

    /**
     * Obtiene el token del usuario
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getString("token", "null");
    }

    /**
     * Obtiene el nombre del usuario.
     * @param context
     * @return
     */
    public static String getUsername(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        return preferences.getString("name", "null");
    }
}
