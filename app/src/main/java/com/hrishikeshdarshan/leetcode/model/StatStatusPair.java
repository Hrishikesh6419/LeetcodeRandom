
package com.hrishikeshdarshan.leetcode.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class StatStatusPair {

    @Expose
    public Difficulty difficulty;

    @Expose
    public Stat stat;

    public StatStatusPair(Difficulty difficulty, Stat stat) {
        this.difficulty = difficulty;
        this.stat = stat;
    }

}
