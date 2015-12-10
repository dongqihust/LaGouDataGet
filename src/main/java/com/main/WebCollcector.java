package com.main;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dao.CityAndJobConfig;
import com.pojo.LagouCity;
import com.pojo.LagouJobStyle;
import com.service.web.CrawlClient;
import com.service.web.OnePageJobsGetCallback;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;


/**
 * Created by 95 on 2015/12/10.
 */
public class WebCollcector {

    static Logger logger = Logger.getLogger(WebCollcector.class);
    public static void main(String[] args) throws IOException {
        WebCollcector webCollectService = new WebCollcector();

        //初次抓取，模拟得到cookie
        CrawlClient crawlClient = CrawlClient.getInstance();
        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore).build();

        //开启异步抓取客户端
        CloseableHttpAsyncClient httpAsyncClient = HttpAsyncClients.custom().setDefaultCookieStore(cookieStore).build();
        httpAsyncClient.start();
        //获取城市和职位列表，通过这两项来搜索数据
        List<LagouCity> lagouCityList = CityAndJobConfig.getInstance().getAllLaGouCitys();
        List<LagouJobStyle> lagouJobStyleList = CityAndJobConfig.getInstance().getAllLaGouJobStyles();

        int i=1;
        for(LagouCity lagouCity : lagouCityList){
            for(LagouJobStyle lagouJobStyle :lagouJobStyleList){
                OnePageJobsGetCallback onePageJobsGetCallback = new OnePageJobsGetCallback(lagouCity,lagouJobStyle);
                logger.error(lagouCity.getCity() + "   --" + lagouJobStyle.getSubstyle3() + "   -----" + i++);
                webCollectService.extractJobInfosAsyn(onePageJobsGetCallback, crawlClient, httpAsyncClient, httpClient);
            }
        }


    }
    /**
     * 异步获取一个城市某一个职位下面的所有的CrawlMain
     */
    public void extractJobInfosAsyn(OnePageJobsGetCallback onePageJobsGetCallback,CrawlClient crawlClient, CloseableHttpAsyncClient closeableHttpAsyncClient, CloseableHttpClient closeableHttpClient){

        //首先构造所有的HttpUriRequest
        //1、首先得到第一页的数据，从而得到当前的总页数

        HttpUriRequest getOneData = RequestBuilder.post()
                .setUri("http://www.lagou.com/jobs/positionAjax.json?px=default&city="+onePageJobsGetCallback.getLagouCity().getCity())
                .addParameter("first", "true")
                .addParameter("pn", 1 + "")
                .addParameter("kd", onePageJobsGetCallback.getLagouJobStyle().getSubstyle3()).build();
        String responseContent = null;
        try {
            responseContent = crawlClient.getStringFromRequest(closeableHttpClient, getOneData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(responseContent);
        JSONObject jsonContent = jsonObject.getJSONObject("content");
        int pageCount = jsonContent.getInteger("totalPageCount");


        //然后通过对每个页数构造一个HttpUriRequest
        for(int i=1;i<=pageCount;i++){
            HttpUriRequest getOneDataTemp = RequestBuilder.post()
                    .setUri("http://www.lagou.com/jobs/positionAjax.json?px=default&city=" + onePageJobsGetCallback.getLagouCity().getCity())
                    .addParameter("first", "true")
                    .addParameter("pn", i + "")
                    .addParameter("kd", onePageJobsGetCallback.getLagouJobStyle().getSubstyle3()).build();
            closeableHttpAsyncClient.execute(getOneDataTemp, onePageJobsGetCallback);
            logger.error("page:  " + i + "正在执行请求数据");
        }
    }


}
