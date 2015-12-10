package com.dao;

import com.alibaba.fastjson.JSONObject;
import com.chronicle.ChonicleService;
import com.pojo.LagouCity;
import com.pojo.LagouJobStyle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 95 on 2015/12/10.
 */
public class CityAndJobConfig {
    SessionFactory sessionFactory;
    private static CityAndJobConfig ourInstance = new CityAndJobConfig();

    public static CityAndJobConfig getInstance() {
        return ourInstance;
    }

    private CityAndJobConfig() {
        Configuration config = new Configuration().configure();
        sessionFactory = config.buildSessionFactory();
    }

    /**
     * 批量插入，提供了性能优化
     * @param crawlMains
     */
    public void  insertCrawlJob(List<JSONObject> crawlMains){

    }


    /**
     * 批量插入，提供了性能优化
     * @param agouCitys
     */
    public  void  insertLaGouCitys(List<LagouCity> agouCitys){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(LagouCity lagouCity : agouCitys){
            session.save(lagouCity);
        }
        session.flush();
        session.clear();
        tx.commit();
        session.close();
    }

    /**
     * 单独插入
     * @param lagouCity
     */
    public  void  insertLaGouCity(LagouCity lagouCity){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(lagouCity);
        session.flush();
        tx.commit();
        session.close();
    }

    public List getAllLaGouCitys(){
        ArrayList<LagouCity> citys = new ArrayList<LagouCity>(40);
        Session session = sessionFactory.openSession();
        citys = (ArrayList<LagouCity>) session.createSQLQuery("select * from lagou_city").addEntity(LagouCity.class).list();
        return citys;
    }


    /**
     * 批量插入，提供了性能优化
     * @param lagouJobStyles
     */
    public  void  insertLaGouJobStyles(List<LagouJobStyle> lagouJobStyles){
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        for(LagouJobStyle lagouJobStyle : lagouJobStyles){
            session.save(lagouJobStyle);
        }
        session.flush();
        session.clear();
        tx.commit();
        session.close();
    }

    public List getAllLaGouJobStyles(){
        ArrayList<LagouJobStyle> LagouJobStyles = new ArrayList<LagouJobStyle>(40);
        Session session = sessionFactory.openSession();
        LagouJobStyles = (ArrayList<LagouJobStyle>) session.createSQLQuery("select * from lagou_job_style").addEntity(LagouJobStyle.class).list();
        return LagouJobStyles;
    }
}
