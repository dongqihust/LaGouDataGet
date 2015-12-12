package com.service.web;

import com.pojo.CrawlMainMessage;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 * Created by 95 on 2015/9/15.
 */
public class ConsumerServiceImpl  {
    Logger logger = Logger.getLogger(ConsumerServiceImpl.class);
    private JmsTemplate jmsTemplate;

    /**
     * 接受消息
     */
    public void receive(Destination destination) throws JMSException {
     ObjectMessage message = (ObjectMessage) jmsTemplate.receive(destination);;
     if(message!=null){
    CrawlMainMessage tm = (CrawlMainMessage)message.getObject();
         logger.error("=================");
    CrawlClient.getInstance().insertResponseToDb(tm.getResults(), tm.getLagouCity().getId(), tm.getLagouJobStyle().getId());
    }else{
         logger.warn("当前消费者并未获取数据");
     }


    }
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }
}