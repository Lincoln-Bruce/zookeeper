package com.jpc.zk.configmanage;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author sjp
 * @Description:
 * @Date Create in 17:02 2021/7/4
 */
public class ZookeeperUtil {

    public static ZooKeeper getZookeeper() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper("192.168.31.136:2181,192.168.31.141:2181,192.168.31.138:2181,192.168.31.139:2181/configs",
                1000, event -> System.out.println("Zk创建事件：" + event.toString()));
        return zooKeeper;
    }
}
