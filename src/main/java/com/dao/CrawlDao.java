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
 * Created by 95 on 2015/12/5.
 */
public class CrawlDao {


    ChonicleService chonicleService;
    private static CrawlDao ourInstance = new CrawlDao();

    public static CrawlDao getInstance() {
        return ourInstance;
    }

    private CrawlDao() {
        chonicleService = ChonicleService.getInstance();

    }


    /**
     * ( ^_^ ) 判断该positionid是否已经存在于数据。
     * @param positionid
     * @return
     */
    public int getPositionId(int positionid){
        return chonicleService.get(positionid);
    }

    /**
     * ( ^_^ ) 添加一个positionid，实际上是put 方法
     * @param positionid
     * @return
     */
    public void addPositionId(int positionid,int value){
        chonicleService.add(positionid, value);
    }

}
