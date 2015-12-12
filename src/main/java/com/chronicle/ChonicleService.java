package com.chronicle;

import com.sun.jmx.snmp.agent.SnmpMibOid;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * Created by 95 on 2015/12/6.
 */
public class ChonicleService {
    public  final  static int   NOT_INSERT = 0;
    public  final  static int   NOT_CRAWL= 1;
    public  final  static int   HAS_CRAWL =2;

    Logger logger = Logger.getLogger(ChonicleService.class);

    ChronicleMap<Integer, Integer> postionIdMap;
    private static ChonicleService ourInstance = new ChonicleService();

    public static ChonicleService getInstance() {
        return ourInstance;
    }

    private ChonicleService() {
        initChronicleMap();
    }

    private void initChronicleMap() {
        String tmp = System.getProperty("java.io.tmpdir");
        String pathname = tmp + "lagou_postionid_v11.dat";

        File file = new File(pathname);

        try {
            postionIdMap =
                    ChronicleMapBuilder.of(Integer.class, Integer.class)
                            .entries(5000000).constantValueSizeBySample(new Integer(1))
                            .createPersistedTo(file);

        } catch (IOException e) {
            logger.error(e);
        }
    }


    /**
     * 线程安全方法，判断当positionid是否存在
     * @param positionid
     * @return
     */
    public int get(int positionid){
        return postionIdMap.getOrDefault(positionid,0);
    }
    /**
     * 线程安全方法，添加一个positionid
     * @param positionid
     */
    public  void add(int positionid, int value){
        postionIdMap.put(positionid,value);
    }
}
