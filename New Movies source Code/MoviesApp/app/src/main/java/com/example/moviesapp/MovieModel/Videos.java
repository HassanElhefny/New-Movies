
package com.example.moviesapp.MovieModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Videos implements Serializable {

    @SerializedName("results")
    @Expose
    private List<ResultDescription> results = null;
    private final static long serialVersionUID = 1558477426268569509L;

    public List<ResultDescription> getResults() {
        return results;
    }

    public void setResults(List<ResultDescription> results) {
        this.results = results;
    }

}
