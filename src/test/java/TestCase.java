import bean.Customer;
import com.yuntongxun.acd.AcdServer;
import com.yuntongxun.acd.GenericAcdServer;
import com.yuntongxun.acd.group.AcdGroup;
import com.yuntongxun.acd.group.AcdGroupFactory;
import com.yuntongxun.acd.group.BlockingQueueAcdGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TestCase {

    public static void main(String[] args) {

        CountDownLatch latch = new CountDownLatch(1);

        List<AcdGroup> acdGroups = new ArrayList<>();

        BlockingQueueAcdGroup group1 = AcdGroupFactory.newBlockingQueueAcdGroup("group1");
        acdGroups.add(group1);

//        BlockingQueueAcdGroup group2 = AcdGroupFactory.newBlockingQueueAcdGroup("group2");
//        acdGroups.add(group2);
//
//        BlockingQueueAcdGroup group3 = AcdGroupFactory.newBlockingQueueAcdGroup("group3");
//        acdGroups.add(group3);

        AcdServer acdServer = new GenericAcdServer();
        acdServer.putGroup(acdGroups);
        acdServer.start();

        for (int i = 0; i < 5; i++) {
            Customer customer = new Customer("C" + i);
            group1.line(customer);
        }




        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
