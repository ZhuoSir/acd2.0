import bean.Agent;
import bean.Customer;
import bean.WorkTimeAgent;
import com.yuntongxun.acd.common.LineServant;
import com.yuntongxun.acd.distribute.AbstractServantDistributor;
import com.yuntongxun.acd.distribute.ComparaSortBlockingQueueServantDistributor;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCase2 {

    @Test
    public void test1() {

        List<WorkTimeAgent> agents = new LinkedList<>();

        for (int i = 0; i < 10; i++) {
            WorkTimeAgent agent = new WorkTimeAgent("A" + i);
            agent.setLastWorkTime(new Date());
            agents.add(agent);
        }

        System.out.println("排序前:" + agents);

        Collections.sort(agents, new Comparator<WorkTimeAgent>() {
            @Override
            public int compare(WorkTimeAgent a1, WorkTimeAgent a2) {

                if (a1.getLastWorkTime().before(a2.getLastWorkTime())) {
                    return 1;
                } else {
                    return -1;
                }
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });

        System.out.println("排序后:" + agents);
    }


    @Test
    public void test2() {

        Comparator<LineServant> comparator = new Comparator<LineServant>() {
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

        AbstractServantDistributor servantDistributor = new ComparaSortBlockingQueueServantDistributor(comparator);

        final Collection<LineServant> sortAgents = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();
        Random random = new Random(1000);
        for (int i = 0; i < 100; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    int sort = random.nextInt(1000);
                    LineServant lineServant = new SortAgent("A" + sort, sort);
                    sortAgents.add(lineServant);
                    servantDistributor.add(lineServant);
                }
            });
        }

        System.out.println("排序前:" + sortAgents);
        Collection<LineServant> sortAgent2 = servantDistributor.lineServantList();
        System.out.println("排序后:" + sortAgent2);

//        SortAgent sortAgent = new SortAgent("A" + 11, 69);
//        servantDistributor.add(sortAgent);
//        sortAgents = servantDistributor.lineServantList();
//        System.out.println("排序后:" + sortAgents);

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
