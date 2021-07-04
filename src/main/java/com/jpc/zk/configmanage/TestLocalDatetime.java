package com.jpc.zk.configmanage;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sjp
 * @Description:
 * @Date Create in 17:44 2021/7/4
 */
public class TestLocalDatetime {

    @Test
    public void outputDateTime() throws InterruptedException {
        while(true){
            System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            Thread.sleep(1000);
        }
    }
}
