package com.service.web;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;



import java.io.IOException;

/**
 * Created by 95 on 2015/12/5.
 */
public class Selenium {
    public static void main(String[] args) throws InterruptedException, IOException {
        // TODO Auto-generated method stub
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setRedirectEnabled(false);

        //try 20 times to wait .5 second each for filling the page.


        HtmlPage page = webClient.getPage("http://www.lagou.com/jobs/list_Java?px=default&city=%E6%AD%A6%E6%B1%89");

        webClient.waitForBackgroundJavaScript(10000);

//        List<DomElement> list = (List<DomElement>) page.getByXPath("//ul[@class='item_con_list']");
//        String contentStr = list.get(0).asText().trim();
//
//        for (int i = 0; i < 20; i++) {
//
//            if (contentStr.length()!=0) {
//                page.asText();
//                break;
//            }
//            synchronized (page) {
//                page.wait(500);
//            }
//        }

    }
}
