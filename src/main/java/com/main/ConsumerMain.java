package com.main;

import com.SpringContext;
import com.service.web.ConsumerServiceImpl;
import com.service.web.CrawlMainService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.util.Random;


/**
 * Created by 95 on 2015/12/8.
 */
public class ConsumerMain {

    static Logger logger = Logger.getLogger(ConsumerMain.class);
    public static void main(String[] args) throws JMSException {
        CrawlMainService crawlMainService = (CrawlMainService) SpringContext.getInstance().getBean("crawlMainService");
        logger.error(crawlMainService);
        final Destination queueDestination = (Destination)  SpringContext.getInstance().getBean("queueDestination");

        for (int i=1;i<10;i++){
            new Thread(new Runnable() {
                public void run() {
                    ConsumerServiceImpl consumerService = (ConsumerServiceImpl)  SpringContext.getInstance().getBean("consumerService");
                    while (true){
                        try {

                            Thread.sleep(new Random().nextInt(1000));
                            consumerService.receive(queueDestination);
                        } catch (JMSException e) {
                            logger.error(e);
                        } catch (InterruptedException e) {
                            logger.error(e);
                        }
                    }
                }
            }).start();
        }



    }





}
