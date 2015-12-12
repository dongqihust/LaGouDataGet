package com.service.web;

import com.pojo.CrawlMainMessage;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class QueueMessageListener implements MessageListener {
        //当收到消息时，自动调用该方法。
      private  AtomicInteger count = new AtomicInteger(0);

    org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(QueueMessageListener.class);

    public void onMessage(Message message) {

        System.out.println("==================="+count.addAndGet(1));
        if(message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            CrawlMainMessage tm = null;
            try {
                tm = (CrawlMainMessage)objectMessage.getObject();
            } catch (JMSException e) {
                logger.error("队列得到的对象无法转换");
            }
            CrawlClient.getInstance().insertResponseToDb(tm.getResults(), tm.getLagouCity().getId(), tm.getLagouJobStyle().getId());
    }else{
        logger.warn("当前消费者并未获取数据");
    }

    }


 
}