package com.hrishikeshdarshan.leetcode.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

//Base URL = https://leetcode.com/

public interface QuestionsApi {

    @GET("api/problems/algorithms")
    Single<QuestionsPOJO> getQuestions();

}
