package com.wdd.mybatch.core.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

public class UuidUtils {
    private static AtomicLong id;

    /**
     * 生成Long 类型的唯一ID
     * 如果需要更长或者更大的冗余空间，只需要time*10^n即可
     * 当前可保证1毫秒 生成10000条不重复
     * @return
     */
    public synchronized static Long getId(){
        Long time =Long.valueOf(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()))*1000;
        if(id == null){
            id =new AtomicLong(time);
            return id.get();
        }

        if(time<=id.get()){
            id.addAndGet(1);
        }else{
            id = new AtomicLong(time);
        }
        return id.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Set<Long> set = new TreeSet<>();
        for(int i=0;i<100;i++){
            new Thread(()->{
                Long id = getId();
                set.add(id);
                System.out.println(id);
            }).start();
        }

        Thread.sleep(50000);
        int size = set.size();
        System.out.println(size);



    }

}
