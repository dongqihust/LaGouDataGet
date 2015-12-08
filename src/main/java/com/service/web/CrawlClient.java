package com.service.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dao.CrawlDao;
import com.pojo.CrawlMain;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by 95 on 2015/12/5.
 */
public class CrawlClient {

    private static CrawlClient ourInstance = new CrawlClient();

    public static CrawlClient getInstance() {
        return ourInstance;
    }

    private CrawlClient() {

    }


    public static void main(String[] args) {
        CrawlClient crawlClient = new CrawlClient();
        BasicCookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore).build();


        for (int i = 1; i <= 10000; i++) {

            HttpUriRequest getOneData = RequestBuilder.post()
                    .setUri("http://www.lagou.com/jobs/positionAjax.json?px=default&city=%E6%AD%A6%E6%B1%89")
                    .addParameter("first", "true")
                    .addParameter("pn", i + "")
                    .addParameter("kd", "java").build();
            String responseContent = null;
            try {
                responseContent = crawlClient.getStringFromRequest(httpClient, getOneData);
            } catch (Exception e) {
                break;
            }
            JSONArray results = crawlClient.getResult(responseContent);
            if(results==null||results.size()==0){
                System.out.println("================================= 当前获取了   " + i + "    条数据=============");
                break;
            }
         //   crawlClient.insertResponseToDb(results);
        }


    }


    public String getStringFromRequest(CloseableHttpClient httpClient, HttpUriRequest httpUriRequest) {
        StringBuilder sb = new StringBuilder();
        try {
            CloseableHttpResponse response = httpClient.execute(httpUriRequest);
            InputStream is = response.getEntity().getContent();
            Scanner sc = new Scanner(is);

            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }



    public void insertResponseToDb(JSONArray results,int cityid, int jobstyleid) {
        Object[] os = results.toArray();

        List<CrawlMain> crawlMainList = new ArrayList<CrawlMain>(15);
        for (Object result : os) {
            JSONObject resultObject = (JSONObject) result;
            String companyLabelListString = resultObject.remove("companyLabelList").toString();
            String companyLabelListTemp = companyLabelListString.replace("\"", "").replace("]", "").replace("[", "");
            resultObject.put("companyLabelList", new String(companyLabelListTemp).intern());
            resultObject.put("cityid",cityid);
            resultObject.put("jobsubstyle3",jobstyleid);
            CrawlMain cm = JSON.parseObject(resultObject.toString(), CrawlMain.class);
            crawlMainList.add(cm);
        }

        CrawlDao.getInstance().insertCrawlJob(crawlMainList);


    }


    public JSONArray getResult(String responseContent) {
        JSONObject json = JSON.parseObject(responseContent);
        JSONObject content = JSON.parseObject(json.get("content").toString());
        JSONArray results = JSON.parseArray(content.get("result").toString());
      return  results;
    }



}
