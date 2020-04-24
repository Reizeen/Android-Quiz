package com.ericarias.quiz.Interface;

import com.ericarias.quiz.Model.Response;
import com.ericarias.quiz.Model.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebServiceClient {

    @POST("signin")
    Call<Users> loginUser(@Body Users usuario);

    @POST("signup")
    Call<Response> registerUser(@Body Users usuario);
}
