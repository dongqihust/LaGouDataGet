package com;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 95 on 2015/12/10.
 */
public class SpringContext {
    Logger logger = Logger.getLogger(SpringContext.class);
   private  ApplicationContext context;
    private static SpringContext ourInstance = new SpringContext();

    public static SpringContext getInstance() {
        return ourInstance;
    }

    private SpringContext() {
        try {
            context = new ClassPathXmlApplicationContext("spring-config.xml");
        }catch (Exception e){
            logger.error(e);
        }


    }
    public Object getBean(String beanname){
        return context.getBean(beanname);
    }
}
