package com.jpc.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, InterruptedException, KeeperException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        final ZooKeeper zooKeeper = new ZooKeeper("192.168.31.136:2181,192.168.31.137:2181,192.168.31.138:2181,192.168.31.139:2181", 3000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("zk create event: " + watchedEvent.toString());
                String path = watchedEvent.getPath();
                Event.KeeperState state = watchedEvent.getState();
                Event.EventType type = watchedEvent.getType();
                switch (type) {
                    case None:
                        System.out.println("客户端与服务器成功建立连接");
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
                        break;
                    case NodeDataChanged:
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

                switch (state) {
                    case Unknown:
                        break;
                    case Disconnected:
                        System.out.println("------------------连接断开了------------------");
                        break;
                    case NoSyncConnected:
                        break;
                    case SyncConnected:
                        System.out.println("------------------连接上了---------------------");
                        break;
                    case AuthFailed:
                        break;
                    case ConnectedReadOnly:
                        break;
                    case SaslAuthenticated:
                        break;
                    case Expired:
                        System.out.println("------------------连接超时了------------------");
                        break;
                    case Closed:
                        System.out.println("------------------连接关闭了------------------");
                        break;
                }
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        final ZooKeeper.States state = zooKeeper.getState();
        switch (state) {
            case CONNECTING:
                System.out.println("ing...");
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("ed...");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }


        zooKeeper.create("/ooxx", "国旗手".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        final Stat stat = new Stat();
        zooKeeper.getData("/ooxx", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println("回调函数读取数据："+new String(data));
                System.out.println("ctx："+ctx);
            }
        }, "opel");

        byte[] data = zooKeeper.getData("/ooxx", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("getData event: "+event.toString());
                try {
                    byte[] data = zooKeeper.getData("/ooxx", this, stat);
                    System.out.println("2-----"+new String(data));
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, stat);
        System.out.println("1-----"+new String(data));
        Stat stat1 = zooKeeper.setData("/ooxx", "三八红旗手".getBytes(), stat.getVersion());
        zooKeeper.setData("/ooxx", "红旗手".getBytes(), stat1.getVersion());

        Thread.sleep(1000000);

    }
}
