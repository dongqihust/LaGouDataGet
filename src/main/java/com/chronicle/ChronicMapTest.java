package com.chronicle;


import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;

import java.io.File;
import java.io.IOException;


/**
 * Created by 95 on 2015/12/6.
 */
public class ChronicMapTest {

    public  static  void main(String[] args) throws IOException {


        String tmp = System.getProperty("java.io.tmpdir");
        String pathname = tmp + "myfile.dat";

        File file = new File(pathname);

        ChronicleMap<Long, Boolean> postionIdMap = null;
        try {
           postionIdMap =
            ChronicleMapBuilder.of(Long.class,Boolean.class)
                    .entries(50000).constantValueSizeBySample(true)
           .createPersistedTo(file);

        } catch (IOException e) {
            e.printStackTrace();
        }

     //   postionIdMap.put(12344556632423L,true);
      //  postionIdMap.put(12345678910L, true);
        Boolean value = new Boolean(false);
        value = postionIdMap.getUsing(1234455663212321423L,value);
        System.out.print(value);
    }


}
