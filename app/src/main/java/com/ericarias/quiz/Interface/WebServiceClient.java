package com.ericarias.quiz.Interface;

import com.ericarias.quiz.Model.Response;
import com.ericarias.quiz.Model.Usuario;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebServiceClient {

    @POST("signin")
    Call<Usuario> loginUser(@Body Usuario usuario);

    @POST("signup")
    Call<Response> registerUser(@Body Usuario usuario);
}
