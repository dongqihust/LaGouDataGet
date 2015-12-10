package com;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by 95 on 2015/12/10.
 */
public class SpringContext {
   private  ApplicationContext context;
    private static SpringContext ourInstance = new SpringContext();

    public static SpringContext getInstance() {
        return ourInstance;
    }

    private SpringContext() {
         context = new ClassPathXmlApplicationContext("spring-config.xml");

    }
    public Object getBean(String beanname){
        return context.getBean(beanname);
    }
}
