package com.jpc.zk.configmanage;

import io.netty.util.internal.StringUtil;
import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author sjp
 * @Description:
 * @Date Create in 16:59 2021/7/4
 */

public class TestConfig {

    ZooKeeper zooKeeper;

    @Before
    public void initZooKeeper() throws IOException {
        zooKeeper = ZookeeperUtil.getZookeeper();
    }

    @After
    public void close() throws InterruptedException {
        zooKeeper.close();
    }

    @Test
    public void testZkConfig() throws InterruptedException {
        WatchCallbackImpl watchCallback = new WatchCallbackImpl();
        watchCallback.setZooKeeper(zooKeeper);
        //得用对象，用String获取不到WatchCallbackImpl getData读到的数据
        ConfigData configData = new ConfigData();
        watchCallback.setConfigData(configData);

        watchCallback.await();

        while (true){
            if(StringUtil.isNullOrEmpty(configData.getText())){
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"：config1下没有数据");
            }else{
                System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)+"：读取到配置文件：" + configData.getText());
            }
            Thread.sleep(1000);
        }

    }
}
