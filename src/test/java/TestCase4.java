import bean.Customer;
import bean.WorkTimeAgent;
import bean.WorkTimeAgentComparator;
import com.yuntongxun.acd.AcdCenter;
import com.yuntongxun.acd.Config.RedisConfig;
import com.yuntongxun.acd.GenericAcdCenter;
import com.yuntongxun.acd.group.AcdGroupFactory;
import com.yuntongxun.acd.group.RedisAcdGroup;
import com.yuntongxun.acd.proxy.ServiceProxy;
import com.yuntongxun.acd.queue.bean.QueueNotification;
import org.junit.Before;
import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RList;
import org.redisson.config.Config;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class TestCase4 {

    Redisson redisson;

    @Before
    public void before() {

        String host = "redis://192.168.180.204:9889";
        Config config = new Config();
        config.useSingleServer().setAddress(host).setPassword("password");

        redisson = (Redisson) Redisson.create(config);
    }

    @Test
    public void test1() {

        RList<Integer> lineServantRList = redisson.getList("test-list");
        lineServantRList.add(20);
        lineServantRList.add(40);
        lineServantRList.add(30);
        lineServantRList.add(10);

        System.out.println(lineServantRList);
        lineServantRList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });

        System.out.println(lineServantRList);
    }

    @Test
    public void test2() {

        ServiceProxy serviceProxy = new ServiceProxy() {
            @Override
            public void sendQueueNotification(QueueNotification queueNotification) {

                Customer customer = (Customer) queueNotification.getLineElement();
                WorkTimeAgent agent    = (WorkTimeAgent)    queueNotification.getDistributedServant();

                if (queueNotification.getLineStatus() == 0) {
//                    System.out.println("customer :" + customer.index() + "/groupid: " + customer.getGroupId() + "  queue has changed : " + queueNotification.getPreCount());
                } else if (queueNotification.getLineStatus() == 1) {
                    System.out.println("customer :" + customer + " has distributed agent : " + agent);
                }
            }
        };

        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setCluster(false);
        redisConfig.setPassword("password");
        redisConfig.setHosts(Arrays.asList("redis://192.168.180.204:9889"));


        RedisAcdGroup redisAcdGroup = AcdGroupFactory.redisComparateBlockingQueueAcdGroup("group2", serviceProxy, redisConfig, new WorkTimeAgentComparator());

        AcdCenter acdCenter = new GenericAcdCenter();
        acdCenter.putGroup(redisAcdGroup);
        acdCenter.start();

        Random random = new Random(1000);


//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 10; i++) {
//                    WorkTimeAgent agent = new WorkTimeAgent("A" + i);
//                    agent.setDistributeTimes(random.nextInt(100));
//                    redisAcdGroup.addLineServant(agent);
//                }
//            }
//        });
//        thread.start();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Customer customer = new Customer("C" + i);
                    redisAcdGroup.line(customer);
                }
            }
        });
        thread1.start();

        redisAcdGroup.lineServantList();

        System.out.println(redisAcdGroup.lineServantList());

        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
