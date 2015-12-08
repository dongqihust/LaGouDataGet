package com.chronicle;

import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Created by 95 on 2015/12/6.
 */
public class ChonicleService {

    ChronicleMap<Integer, Boolean> postionIdMap;
    private static ChonicleService ourInstance = new ChonicleService();

    public static ChonicleService getInstance() {
        return ourInstance;
    }

    private ChonicleService() {
        initChronicleMap();
    }

    private void initChronicleMap() {
        String tmp = System.getProperty("java.io.tmpdir");
        String pathname = tmp + "lagou_postionid3.dat";

        File file = new File(pathname);

        try {
            postionIdMap =
                    ChronicleMapBuilder.of(Integer.class, Boolean.class)
                            .entries(5000000).constantValueSizeBySample(true)
                            .createPersistedTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 线程安全方法，判断当positionid是否存在
     * @param positionid
     * @return
     */
    public boolean exist(int positionid){
        return postionIdMap.getOrDefault(positionid,false);
    }

    /**
     * 线程安全方法，添加一个positionid
     * @param positionid
     */
    public  void add(int positionid){
        postionIdMap.put(positionid,true);
    }
}
