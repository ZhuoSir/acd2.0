import bean.Agent;
import bean.Customer;
import com.yuntongxun.acd.AcdCenter;
import com.yuntongxun.acd.GenericAcdCenter;
import com.yuntongxun.acd.common.LineElement;
import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.context.listener.AfterLineAcdContextListener;
import com.yuntongxun.acd.context.listener.ExceptionAcdContextListener;
import com.yuntongxun.acd.context.listener.PreLineAcdContextListener;
import com.yuntongxun.acd.distribute.AbstractServantDistributor;
import com.yuntongxun.acd.distribute.ComparaSortBlockingListServantDistributor;
import com.yuntongxun.acd.distribute.comparator.LineServantComparator;
import com.yuntongxun.acd.group.AcdGroup;
import com.yuntongxun.acd.group.AcdGroupFactory;
import com.yuntongxun.acd.proxy.ServiceProxy;
import com.yuntongxun.acd.queue.bean.QueueNotification;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCase2 {


    @Test
    public void test2() {

        LineServantComparator comparator = new LineServantComparator() {
            @Override
            public int compare(LineServant o1, LineServant o2) {
                SortAgent sortAgent1 = (SortAgent) o1;
                SortAgent sortAgent2 = (SortAgent) o2;

                if (sortAgent1.getSort() > sortAgent2.getSort()) {
                    return -1;
                } else if (sortAgent1.getSort() < sortAgent2.getSort()){
                    return 1;
                }
                return 0;
            }
        };

        AbstractServantDistributor servantDistributor = new ComparaSortBlockingListServantDistributor(comparator);

        final Collection<LineServant> sortAgents = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Random random = new Random(1000);
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int sort = random.nextInt(10000);
                    LineServant lineServant = new SortAgent("A" + sort, sort);
                    sortAgents.add(lineServant);
                    servantDistributor.add(lineServant);
                }
            });
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("排序前:" + sortAgents);
        Collection<LineServant> sortAgent2 = servantDistributor.lineServantList();
        System.out.println("排序后:" + sortAgent2);

//        SortAgent sortAgent = new SortAgent("A" + 11, 69);
//        servantDistributor.add(sortAgent);
//        sortAgents = servantDistributor.lineServantList();
//        System.out.println("排序后:" + sortAgents);
    }


    @Test
    public void test3() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);
        List<AcdGroup> acdGroups = new ArrayList<>();
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

        AcdGroup group1 = AcdGroupFactory.fifoBlockingOfelementAndSortBlockingOfServantAcdGroup("group1", serviceProxy, new LineServantComparator() {
            @Override
            public int compare(LineServant o1, LineServant o2) {

                Agent agent1 = (Agent) o1;
                Agent agent2 = (Agent) o2;

                if (agent2.getSort() < agent1.getSort()) {
                    return -1;
                } else if (agent2.getSort() > agent1.getSort()){
                    return 1;
                }

                return 0;
            }
        });


        group1.addAcdContextListener(new PreLineAcdContextListener() {
            @Override
            public void preLine(LineElement lineElement) {
                System.out.println("PreLine1 : " + lineElement);
            }
        });

        group1.addAcdContextListener(new AfterLineAcdContextListener() {
            @Override
            public void afterLine(LineElement lineElement, LineServant lineServant) {
                System.out.println("After1: " + lineElement + ", " + lineServant);
                System.out.println("");

            }
        });

        group1.addAcdContextListener(new ExceptionAcdContextListener() {
            @Override
            public void exception(Exception e) {
                e.printStackTrace();
            }
        });


        acdGroups.add(group1);
        AcdCenter acdCenter = new GenericAcdCenter();
        acdCenter.putGroup(group1);
        acdCenter.start();


        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int c = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Customer customer = new Customer("C" + c, group1.getGroupName());
                    group1.line(customer);
                }
            });
        }

        Collection<LineServant> agents = new ArrayList<>();
        Random random = new Random(1000);
        for (int j = 0; j < 5; j++) {
            final int c = j;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int sort = random.nextInt(100);
                    Agent agent = new Agent("A" + c, sort);
                    agents.add(agent);
                    group1.addLineServant(agent);
                }
            });
        }

//        System.out.println(agents);
//        group1.addLineServants(agents);
//        System.out.println(group1.lineServantList());

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void test4() {

        CountDownLatch latch = new CountDownLatch(1);
        List<AcdGroup> acdGroups = new ArrayList<>();
        ServiceProxy serviceProxy = new ServiceProxy() {
            @Override
            public void sendQueueNotification(QueueNotification queueNotification) {

                Customer customer = (Customer) queueNotification.getLineElement();
                Agent agent    = (Agent)    queueNotification.getDistributedServant();

                if (queueNotification.getLineStatus() == 0) {
                    System.out.println("customer :" + customer.index() + "/groupid: " + customer.getGroupId() + "  queue has changed : " + queueNotification.getPreCount());
                } else if (queueNotification.getLineStatus() == 1) {
                    System.out.println("customer :" + customer + " has distributed agent : " + agent);
                }
            }
        };

        AcdGroup group1 = AcdGroupFactory.fifoBlockingOfelementAndSortBlockingOfServantAcdGroup("group1", serviceProxy, new LineServantComparator() {
            @Override
            public int compare(LineServant o1, LineServant o2) {
                if (o1.getDistributeTimes() < o2.getDistributeTimes()) {
                    return -1;
                } else if (o2.getDistributeTimes() < o1.getDistributeTimes()) {
                    return 1;
                }
                return 0;
            }
        });
        acdGroups.add(group1);
        AcdCenter acdCenter = new GenericAcdCenter();
        acdCenter.putGroup(group1);
        acdCenter.start();


        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int c = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Customer customer = new Customer("C" + c, group1.getGroupName());
                    group1.line(customer);
                }
            });

        }

        Collection<LineServant> agents = new ArrayList<>();
        Random random = new Random(100);
        for (int j = 0; j < 5; j++) {
            final int c = j;
            int worktime = random.nextInt(10);
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Agent agent = new Agent("A" + c);
                    agent.setDistributeTimes(worktime);
                    agents.add(agent);
//                    group1.addLineServant(agent);
                }
            });
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(agents);
        group1.addLineServants(agents);
        System.out.println(group1.lineServantList());

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    class SortAgent extends LineServant {

        private int sort;

        public SortAgent(String servantId, int sort) {
            super(servantId);
            this.sort = sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getSort() {
            return sort;
        }

        @Override
        public String toString() {
            return "SortAgent{" +
                    "sort=" + sort +
                    '}';
        }
    }
}
