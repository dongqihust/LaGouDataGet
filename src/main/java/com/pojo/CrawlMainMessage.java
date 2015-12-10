package com.pojo;

import com.alibaba.fastjson.JSONArray;

import java.io.Serializable;

/**
 * Created by 95 on 2015/12/8.
 */
public class CrawlMainMessage implements Serializable{
    private LagouCity lagouCity;
    private LagouJobStyle lagouJobStyle;
    private JSONArray results;


    public CrawlMainMessage(LagouCity lagouCity, LagouJobStyle lagouJobStyle, JSONArray results) {

        this.lagouCity = lagouCity;
        this.lagouJobStyle = lagouJobStyle;
        this.results = results;
    }

    public LagouCity getLagouCity() {
        return lagouCity;
    }

    public void setLagouCity(LagouCity lagouCity) {
        this.lagouCity = lagouCity;
    }

    public LagouJobStyle getLagouJobStyle() {
        return lagouJobStyle;
    }

    public void setLagouJobStyle(LagouJobStyle lagouJobStyle) {
        this.lagouJobStyle = lagouJobStyle;
    }

    public JSONArray getResults() {
        return results;
    }

    public void setResults(JSONArray results) {
        this.results = results;
    }
}
