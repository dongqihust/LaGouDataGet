package com.service.web;

import com.pojo.CrawlMainMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.io.Serializable;

/**
 * Created by 95 on 2015/9/15.
 */
public class ProducerServiceImpl {
    private JmsTemplate jmsTemplate;
    /**
     * 向指定队列发送消息
     */
    public void sendMessage(Destination destination, final CrawlMainMessage msg) {

        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(msg);
            }
        });
    }

    /**
     * 向默认队列发送消息
     */
    public void sendMessage(final CrawlMainMessage msg) {
        String destination =  jmsTemplate.getDefaultDestination().toString();

        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage message = null;
                try {
                     message = session.createObjectMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }

                return message;
            }
        });

    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

}