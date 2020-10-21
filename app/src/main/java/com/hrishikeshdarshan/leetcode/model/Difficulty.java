
package com.hrishikeshdarshan.leetcode.model;

import com.google.gson.annotations.Expose;


public class Difficulty {

    public Difficulty(Long level) {
        this.level = level;
    }

    @Expose
    public Long level;


}
