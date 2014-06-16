package cir.lab.csn.client;


import org.apache.activemq.broker.jmx.TopicViewMBean;

import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public interface BrokerManager {

    public int setBrokerConfiguration(String settings);

    public int startBroker();

    public int stopBroker();

    public long getTotalEnqueueCount();

    public long getTotalDequeueCount();

    public long getTotalConsumerCount();

    public long getTotalProducerCount();

    public Set<String> getTotalTopicSubscribers();

    public int getStoreUsagePercentage();

    public int getMemoryUsagePercentage();

    public void doGarbageCollect();

    public Map<String, Long> getTopicEnqueueCount();

    public Map<String, Long> getTopicDequeueCount();

    public Map<String, Long> getTopicConsumerCount();

    public Map<String, Set<String>> getTopicSubscribers();

    public String getCurrentHealthStatus();
}
