package com.ericarias.quiz.Interface;

import com.ericarias.quiz.Model.RespHTTP;
import com.ericarias.quiz.Model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebServiceClient {

    @POST("signin")
    Call<RespHTTP> loginUser(@Body Usuario usuario);

    @POST("signup")
    Call<RespHTTP> registerUser(@Body Usuario usuario);
}
