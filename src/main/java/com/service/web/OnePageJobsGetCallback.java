package com.service.web;

import com.alibaba.fastjson.JSONArray;
import com.pojo.LagouCity;
import com.pojo.LagouJobStyle;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by 95 on 2015/12/6.
 */
public class OnePageJobsGetCallback implements FutureCallback<HttpResponse> {

    private LagouCity lagouCity;
    private LagouJobStyle lagouJobStyle;

    public OnePageJobsGetCallback(LagouCity lagouCity, LagouJobStyle lagouJobStyle) {
        this.lagouCity = lagouCity;
        this.lagouJobStyle = lagouJobStyle;
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

    public void completed(HttpResponse httpResponse) {
        StringBuilder sb = new StringBuilder();
        CrawlClient crawlClient =  CrawlClient.getInstance();
        InputStream is = null;
        try {
            is = httpResponse.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(is);
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }

        JSONArray results = crawlClient.getResult(sb.toString());

        if(results!=null&&results.size()!=0){
            crawlClient.insertResponseToDb(results,lagouCity.getId(),lagouJobStyle.getId());

        }



    }

    public void failed(Exception e) {

    }

    public void cancelled() {

    }
}