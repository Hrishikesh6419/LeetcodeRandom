package com.hrishikeshdarshan.leetcode.model;


import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuestionsService {

    public String BASE_URL = "https://leetcode.com/";
    QuestionsApi api;


    public QuestionsService(){
            api = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(QuestionsApi.class);
    }

    public Single<QuestionsPOJO> getAllQuestion(){
        return api.getQuestions();
    }


}
