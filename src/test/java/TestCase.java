import bean.Agent;
import bean.Customer;
import com.yuntongxun.acd.AcdCenter;
import com.yuntongxun.acd.GenericAcdCenter;
import com.yuntongxun.acd.group.AcdGroup;
import com.yuntongxun.acd.group.AcdGroupFactory;
import com.yuntongxun.acd.group.FIFOBlockingQueueAcdGroup;
import com.yuntongxun.acd.proxy.ServiceProxy;
import com.yuntongxun.acd.queue.bean.QueueNotification;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestCase {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        List<AcdGroup> acdGroups = new ArrayList<>();
        ServiceProxy serviceProxy = new ServiceProxy() {
            @Override
            public void sendQueueNotification(QueueNotification queueNotification) {

                Customer customer = (Customer) queueNotification.getLineElement();
                Agent    agent    = (Agent)    queueNotification.getDistributedServant();

                if (queueNotification.getLineStatus() == 0) {
                    System.out.println("customer :" + customer.index() + "/groupid: " + customer.getGroupId() + "  queue has changed : " + queueNotification.getPreCount());
                } else if (queueNotification.getLineStatus() == 1) {
                    System.out.println("customer :" + customer + " has distributed agent : " + agent);
                }
            }
        };

        AcdGroup group1 = AcdGroupFactory.fifoBlockingQueueAcdGroup("group1", serviceProxy);
        acdGroups.add(group1);

        AcdGroup group2 = AcdGroupFactory.fifoBlockingQueueAcdGroup("group2", serviceProxy);
        acdGroups.add(group2);

        AcdGroup group3 = AcdGroupFactory.fifoBlockingQueueAcdGroup("group3", serviceProxy);
        acdGroups.add(group3);

        AcdCenter acdCenter = new GenericAcdCenter();
//        acdCenter.putGroup(acdGroups);
        acdCenter.putGroup(group1);
        acdCenter.start();

        for (int j = 0; j < 5; j++) {
            Agent agent = new Agent("A" + j);
            group1.addLineServant(agent);
        }

        for (int i = 0; i < 20; i++) {
            Customer customer = new Customer("C" + i, group1.getGroupName());
//            Thread.sleep(i * 10000);
            group1.line(customer);
        }


        for (int i = 0; i < 30; i++) {
            Customer customer = new Customer("C" + i, group2.getGroupName());
            group2.line(customer);
        }


        for (int i = 0; i < 20; i++) {
            Customer customer = new Customer("C" + i, group3.getGroupName());
            group3.line(customer);
        }

        for (int j = 0; j < 5; j++) {
            Thread.sleep(1000);
            Agent agent = new Agent("A" + j);
            group2.addLineServant(agent);
        }

        for (int j = 0; j < 5; j++) {
//            Thread.sleep(1000);
            Agent agent = new Agent("A" + j);
            group3.addLineServant(agent);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
