import bean.Agent;
import bean.Customer;
import com.yuntongxun.acd.AcdCenter;
import com.yuntongxun.acd.Config.RedisConfig;
import com.yuntongxun.acd.GenericAcdCenter;
import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.group.AcdGroupFactory;
import com.yuntongxun.acd.group.RedisAcdGroup;
import com.yuntongxun.acd.proxy.ServiceProxy;
import com.yuntongxun.acd.queue.bean.QueueNotification;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCase3 {

    @Test
    public void test1() {

        ServiceProxy serviceProxy = new ServiceProxy() {
            @Override
            public void sendQueueNotification(QueueNotification queueNotification) {

                Customer customer = (Customer) queueNotification.getLineElement();
                Agent agent    = (Agent)    queueNotification.getDistributedServant();

                if (queueNotification.getLineStatus() == 0) {
//                    System.out.println("customer :" + customer.index() + "/groupid: " + customer.getGroupId() + "  queue has changed : " + queueNotification.getPreCount());
                } else if (queueNotification.getLineStatus() == 1) {
                    System.out.println("customer :" + customer + " has distributed agent : " + agent);
                }
            }
        };

        RedisConfig redisConfig = new RedisConfig();
        redisConfig.setCluster(false);
        redisConfig.setHosts(Arrays.asList("redis://127.0.0.1:6379"));

        RedisAcdGroup group1 = AcdGroupFactory.redisFifoBlockingQueueAcdGroup("group1", serviceProxy, redisConfig);

        AcdCenter acdCenter = new GenericAcdCenter();
        acdCenter.putGroup(group1);
        acdCenter.start();

        ExecutorService executorService = Executors.newCachedThreadPool();
//        for (int i = 0; i < 10; i++) {
//            final int c = i;
////            executorService.submit(new Runnable() {
////                @Override
////                public void run() {
////                    Customer customer = new Customer("C" + c, group1.getGroupName());
////                    group1.line(customer);
////                }
////            });
//
//            Customer customer = new Customer("C" + c, group1.getGroupName());
//            group1.line(customer);
//        }

        System.out.println(group1.elements());

        Collection<LineServant> agents = new ArrayList<>();
        Random random = new Random(1000);
        for (int j = 0; j < 3; j++) {
            final int c = j;
//            executorService.submit(new Runnable() {
//                @Override
//                public void run() {
//                    int sort = random.nextInt(100);
//                    Agent agent = new Agent("A" + c, sort);
//                    agents.add(agent);
//                    group1.addLineServant(agent);
//                }
//            });

            int sort = random.nextInt(100);
            Agent agent = new Agent("A" + c, sort);
            agents.add(agent);
            group1.addLineServant(agent);
        }

        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
