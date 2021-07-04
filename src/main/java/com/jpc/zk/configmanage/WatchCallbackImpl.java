package com.jpc.zk.configmanage;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @author sjp
 * @Description:
 * @Date Create in 17:08 2021/7/4
 */
public class WatchCallbackImpl implements AsyncCallback.DataCallback, Watcher, AsyncCallback.StatCallback {

    private ConfigData configData;
    private ZooKeeper zooKeeper;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void await() throws InterruptedException {
        zooKeeper.exists("/config1", this, this, "爱我中华");
        countDownLatch.await();
    }


    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
        if(Objects.nonNull(data)){
            configData.setText(new String(data));
            countDownLatch.countDown();
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if(Objects.nonNull(stat)){
            zooKeeper.getData("/config1", this, this, "ABC");
        }
    }

    @Override
    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                zooKeeper.getData("/config1", this, this, "D");
                break;
            case NodeDeleted:
                System.out.println("节点数据被删除......");
                configData.setText("");
                countDownLatch = new CountDownLatch(1);
                break;
            case NodeDataChanged:
                zooKeeper.getData("/config1", this, this, "D");
                break;
            case NodeChildrenChanged:
                break;
            case DataWatchRemoved:
                break;
            case ChildWatchRemoved:
                break;
            case PersistentWatchRemoved:
                break;
        }
        switch (event.getState()) {
            case Unknown:
                break;
            case Disconnected:
                System.out.println("客户端与服务端断开连接......");
                break;
            case NoSyncConnected:
                break;
            case SyncConnected:
                System.out.println("节点数据正在同步......");
                break;
            case AuthFailed:
                break;
            case ConnectedReadOnly:
                break;
            case SaslAuthenticated:
                break;
            case Expired:
                System.out.println("客户端与服务端连接已超时......");
                break;
            case Closed:
                System.out.println("客户端与服务端连接已关闭......");
                break;
        }
    }


    public ConfigData getConfigData() {
        return configData;
    }

    public void setConfigData(ConfigData configData) {
        this.configData = configData;
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }
}
