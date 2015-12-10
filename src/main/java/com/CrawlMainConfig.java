package com;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95 on 2015/12/9.
 */
public class CrawlMainConfig {

    static  ArrayList<String> retArrays = new ArrayList<String>(20);
    static {
        retArrays.add("formatCreateTime");
        retArrays.add("createTime");
        retArrays.add("positionId");
        retArrays.add("deliverCount");
        retArrays.add("positionName");
        retArrays.add("positionType");
        retArrays.add("workYear");
        retArrays.add("education");
        retArrays.add("jobNature");
        retArrays.add("companyLogo");
        retArrays.add("industryField");
        retArrays.add("positionAdvantage");
        retArrays.add("salary");
        retArrays.add("positionFirstType");
        retArrays.add("leaderName");
        retArrays.add("companySize");
        retArrays.add("companyName");
        retArrays.add("companyId");
        retArrays.add("city");
        retArrays.add("financeStage");
        retArrays.add("companyLabelList");
        retArrays.add("companyShortName");
        retArrays.add("showOrder");
        retArrays.add("adWord");
        retArrays.add("imstate");
        retArrays.add("createTimeSort");
        retArrays.add("positonTypesMap");
        retArrays.add("hrScore");
        retArrays.add("flowScore");
        retArrays.add("showCount");
        retArrays.add("pvScore");
        retArrays.add("totalCount");
        retArrays.add("searchScore");
        retArrays.add("cityid");
        retArrays.add("jobsubstyle3");
    }



    public static List<String> getCrawlMainPropertis(){
        return  retArrays;
    }

    //待改成单例模式
    public  static String  getSql(){
        StringBuilder keyFragmentBuffer = new StringBuilder();
        StringBuilder variableFragmentBuffer = new StringBuilder();
        final List<String> crawlProperties = CrawlMainConfig.getCrawlMainPropertis();

        for(String property: crawlProperties){
            keyFragmentBuffer.append(property).append(",");
            variableFragmentBuffer.append("?").append(",");
        }
        String keyFragmentTemp = keyFragmentBuffer.toString();
        String keyFragment = keyFragmentTemp.substring(0, keyFragmentTemp.length() - 1);

        String variableFragmentTemp = variableFragmentBuffer.toString();
        String variableFragment = variableFragmentTemp.substring(0, variableFragmentTemp.length() - 1);

         String sql = "INSERT INTO crawl_main(" +keyFragment+")"
                +   " VALUES ("+variableFragment+")";
        return  sql;
    }
}
