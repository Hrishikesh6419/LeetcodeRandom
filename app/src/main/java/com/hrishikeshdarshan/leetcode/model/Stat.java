
package com.hrishikeshdarshan.leetcode.model;

import com.google.gson.annotations.SerializedName;


public class Stat {

    @SerializedName("frontend_question_id")
    public Long frontendQuestionId;

    @SerializedName("question__title")
    public String questionTitle;

    @SerializedName("question__title_slug")
    public String questionTitleSlug;


    public Stat(Long frontendQuestionId, String questionTitle, String questionTitleSlug) {
        this.frontendQuestionId = frontendQuestionId;
        this.questionTitle = questionTitle;
        this.questionTitleSlug = questionTitleSlug;
    }
}
