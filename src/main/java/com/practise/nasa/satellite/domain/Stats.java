package com.practise.nasa.satellite.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Stats implements Serializable {

    private Long count;
    private List<Result> results = null;
    private final static long serialVersionUID = 460539461792981326L;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Stats withCount(Long count) {
        this.count = count;
        return this;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Stats withResults(List<Result> results) {
        this.results = results;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("count", count).append("results", results).toString();
    }

}