
package com.hrishikeshdarshan.leetcode.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class QuestionsPOJO {

    public QuestionsPOJO(Long numTotal, List<StatStatusPair> statStatusPairs) {
        this.numTotal = numTotal;
        this.statStatusPairs = statStatusPairs;
    }

    @SerializedName("num_total")
    public Long numTotal;
    @SerializedName("stat_status_pairs")
    public List<StatStatusPair> statStatusPairs;


}
