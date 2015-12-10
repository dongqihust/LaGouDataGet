package com.service.web;


import com.CrawlMainConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dao.CrawlDao;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by 95 on 2015/12/9.
 */
public class CrawlMainService {
    private static Logger logger = Logger.getLogger(CrawlMainService.class);
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertCrawlMains(final List<JSONObject> CrawlMains) {
        BatchPreparedStatementSetter batchPreparedStatementSetter = new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                JSONObject jsonObject = CrawlMains.get(i);
                    for(int j=1;j<=CrawlMainConfig.getCrawlMainPropertis().size();j++){
                        String key = CrawlMainConfig.getCrawlMainPropertis().get(j - 1);
                        Object value = jsonObject.get(key);
                        if(value==null){
                            value="null";
                        }
                        preparedStatement.setString(j,value.toString());
                    }
            }
            public int getBatchSize() {
                return CrawlMains.size();
            }
        };
        jdbcTemplate.batchUpdate(CrawlMainConfig.getSql(), batchPreparedStatementSetter);
    }

    public void insertCrawlMains(JSONArray jsonArray, int cityid, int subtype3){

        List<JSONObject> CrawlMains = new ArrayList<JSONObject>(15);
        CrawlDao crawlDao = CrawlDao.getInstance();
       for(Object object : jsonArray){
           JSONObject jsonObject = (JSONObject) object;
           if(crawlDao.getPositionId(jsonObject.getInteger("positionId"))==0){
               jsonObject.put("cityid",cityid+"");
               jsonObject.put("jobsubstyle3",subtype3+"");
               CrawlMains.add(jsonObject);
               crawlDao.addPositionId(jsonObject.getInteger("positionId"),1);
           }

       }
        if(CrawlMains.size()>0){
            insertCrawlMains(CrawlMains);
        }else{
            logger.error("当前城市和职位下，并未收到数据,cityid="+cityid+"  ,subtype3="+subtype3);
        }
    }






}
