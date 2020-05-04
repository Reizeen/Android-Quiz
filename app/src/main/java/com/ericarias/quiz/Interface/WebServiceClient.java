package com.ericarias.quiz.Interface;

import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.Model.Response;
import com.ericarias.quiz.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface WebServiceClient {


    @POST("session")
    Call<Response> session(@Body User user);

    @POST("signin")
    Call<User> loginUser(@Body User user);

    @POST("signup")
    Call<Response> registerUser(@Body User user);

    @POST("question")
    Call<Response> addQuestion(@Header("token") String token,
                               @Body Question question);
}
