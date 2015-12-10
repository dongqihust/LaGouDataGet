package com.crawl.biz;

import com.dao.CityAndJobConfig;
import com.dao.CrawlDao;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.pojo.LagouCity;
import com.pojo.LagouJobStyle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;

/**
 * Created by 95 on 2015/12/6.
 * 无耻地从拉勾网获取互联网招聘的城市信息，技术类别分类。
 * 图方便采用jsoup搞定
 */
public class GetBizConfig {

    public static  void main(String[] args) throws IOException {
        GetBizConfig config = new GetBizConfig();

        //得到城市的url
        //String url =  "http://www.lagou.com/gongsi/";

        //
        String url =  "http://www.lagou.com/";
        Document doc = Jsoup.connect(url).get();
        config.insertJobStyle(doc);



    }

    public String  getHtmlEnableJS(String url) throws IOException {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
        webClient.getOptions().setRedirectEnabled(false);
        //try 20 times to wait .5 second each for filling the page.
        HtmlPage page = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(10000);
        return page.asXml();
    }

    public void insertCitys(Document doc){
        //data-lg-tj-id="8q00" 节点下的就是城市，记得去重复哦
        //what fuck 城市信息是动态加载的。但是职位不是
        HashSet<String> citys = new HashSet<String>(40);
        Elements  otherElements= doc.select("a[data-lg-tj-id=7B00]");
        Elements  mainElements= doc.select("a[data-lg-tj-id=7z00]");

        for(Element e: otherElements){
            citys.add(e.text().trim());

        }
        for(Element e: mainElements){
            citys.add(e.text().trim());
        }

        ArrayList<LagouCity> cityList = new ArrayList<LagouCity>(40);
        for(String city : citys){
            if("全国".equals(city)){
                continue;
            }
            LagouCity lagouCity = new LagouCity();
            lagouCity.setCity(city);
            cityList.add(lagouCity);
        }
        System.out.print(citys);
        CityAndJobConfig.getInstance().insertLaGouCitys(cityList);
    }

    public void insertJobStyle(Document doc){
        ArrayList<LagouJobStyle> lagouJobStyles = new ArrayList<LagouJobStyle>(200);

        Elements menuBoxs = doc.getElementsByClass("menu_box");
        for(Element menuBox :menuBoxs){
            Element sub1 = menuBox.getElementsByTag("h2").get(0);
                System.out.println(sub1.text());

            Elements sub2 = menuBox.getElementsByTag("dt");
            for(Element e: sub2){
                System.out.println("  " + e.text());
                Elements sub3 = e.nextElementSibling().children();
                for(Element element3 : sub3){
                    LagouJobStyle lagouJobStyle = new LagouJobStyle();
                    lagouJobStyle.setSubstyle1(sub1.text().trim());
                    lagouJobStyle.setSubstyle2(e.text().trim());
                    lagouJobStyle.setSubstyle3(element3.text().trim());
                    lagouJobStyles.add(lagouJobStyle);

                    System.out.println("     "+element3.text());
                }

            }

        }
        CityAndJobConfig.getInstance().insertLaGouJobStyles(lagouJobStyles);


    }
}
