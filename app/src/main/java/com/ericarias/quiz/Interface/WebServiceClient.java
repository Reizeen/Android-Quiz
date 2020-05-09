package com.ericarias.quiz.Interface;

import com.ericarias.quiz.Model.Points;
import com.ericarias.quiz.Model.Question;
import com.ericarias.quiz.Model.ResponseServer;
import com.ericarias.quiz.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceClient {


    @GET("points")
    Call<List<Points>> allPoints(@Header("token") String token);

    @POST("session")
    Call<ResponseServer> session(@Body User user);

    @POST("signin")
    Call<User> loginUser(@Body User user);

    @POST("signup")
    Call<ResponseServer> registerUser(@Body User user);

    @GET("questions/{id}")
    Call<List<Question>> userQuestions(@Header("token") String token,
                                       @Path("id") int idUser);

    @POST("question")
    Call<ResponseServer> addQuestion(@Header("token") String token,
                                     @Body Question question);

    @DELETE("question/{id}")
    Call<ResponseServer> deleteQuestion(@Header("token") String token,
                                        @Path("id") int idQuestion);

    @PUT("question")
    Call<ResponseServer> editQuestion(@Header("token") String token,
                                      @Body Question question);
}
