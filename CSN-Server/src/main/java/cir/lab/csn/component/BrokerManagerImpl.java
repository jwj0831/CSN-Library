package cir.lab.csn.component;

import cir.lab.csn.client.BrokerManager;
import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.HealthViewMBean;
import org.apache.activemq.broker.jmx.TopicViewMBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BrokerManagerImpl implements BrokerManager {
    private BrokerService service;

    Logger logger = LoggerFactory.getLogger(BrokerManagerImpl.class);
    private JMXServiceURL jmxURL;
    private JMXConnector jmxConnector;

    private static final String JMX_SERVICE_URL = "service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi";
    private MBeanServerConnection mBeanConn;
    private ObjectName objectName;
    private static final String BROKER_OBJECT_NAME = "org.apache.activemq:brokerName=localhost,type=Broker";
    private static final String HEALTH_OBJECT_NAME = "org.apache.activemq:type=Broker,brokerName=localhost,service=Health";


    private BrokerViewMBean brokerMBean;
    private HealthViewMBean healthMBean;

    @Override
    public int setBrokerConfiguration(String settings) {
        try {
            service = BrokerFactory.createBroker(settings);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int startBroker() {
        try {
            service.start();
            logger.info("Finish to start Broker");
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int stopBroker() {
        try {
            service.stop();
            logger.info("finish to stop broker");
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private void connectDefaultJMX(String objName) {
        try {
            jmxURL = new JMXServiceURL(JMX_SERVICE_URL);
            jmxConnector = JMXConnectorFactory.connect(jmxURL);
            mBeanConn = jmxConnector.getMBeanServerConnection();
            objectName = new ObjectName(objName);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
    }

    private void getBrokerMBean() {
        connectDefaultJMX(BROKER_OBJECT_NAME);
        brokerMBean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanConn, objectName, BrokerViewMBean.class, true);
    }

    private void getHealthMBean() {
        connectDefaultJMX(HEALTH_OBJECT_NAME);
        healthMBean = (HealthViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanConn, objectName, HealthViewMBean.class, true);
    }


    @Override
    public long getTotalEnqueueCount() {
        getBrokerMBean();
        return brokerMBean.getTotalEnqueueCount();
    }

    @Override
    public long getTotalDequeueCount() {
        getBrokerMBean();
        return brokerMBean.getTotalDequeueCount();
    }

    @Override
    public long getTotalConsumerCount() {
        getBrokerMBean();
        return brokerMBean.getTotalConsumerCount();
    }

    @Override
    public long getTotalProducerCount() {
        getBrokerMBean();
        return brokerMBean.getTotalProducerCount();
    }

    @Override
    public Set<String> getTotalTopicSubscribers() {
        getBrokerMBean();
        ObjectName[] names = brokerMBean.getTopicSubscribers();
        Set<String> subscribers = new HashSet<String>();
        for(ObjectName name : names) {
           subscribers.add(name.toString());
        }
        return subscribers;
    }

    @Override
    public int getStoreUsagePercentage() {
        getBrokerMBean();
        return brokerMBean.getStorePercentUsage();
    }

    @Override
    public int getMemoryUsagePercentage() {
        getBrokerMBean();
        return brokerMBean.getMemoryPercentUsage();
    }

    @Override
    public void doGarbageCollect() {
        getBrokerMBean();
        try {
            brokerMBean.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getCurrentHealthStatus() {
        getHealthMBean();
        return healthMBean.getCurrentStatus();
    }

    @Override
    public Set<Map<String, Object>> getTopicEnqueueCount() {
        getBrokerMBean();
        ObjectName[] names = brokerMBean.getTopics();
        Set<Map<String, Object>> topicSet = new HashSet<Map<String, Object>>();
        for(ObjectName name : names) {
            TopicViewMBean topicMBean = (TopicViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanConn, name, TopicViewMBean.class, true);
            if(topicMBean.getName().substring(0, 8).compareTo("ActiveMQ") != 0) {
                Map<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.put("topicName", topicMBean.getName());
                tempMap.put("num", topicMBean.getEnqueueCount());
                topicSet.add(tempMap);
            }
        }
        return topicSet;
    }

    @Override
    public Set<Map<String, Object>> getTopicDequeueCount() {
        getBrokerMBean();
        ObjectName[] names = brokerMBean.getTopics();
        Set<Map<String, Object>>  topicSet = new HashSet<Map<String, Object>>();
        for(ObjectName name : names) {
            TopicViewMBean topicMBean = (TopicViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanConn, name, TopicViewMBean.class, true);
            if(topicMBean.getName().substring(0, 8).compareTo("ActiveMQ") != 0) {
                Map<String, Object> tempMap = new HashMap<String, Object>();
                tempMap.put("topicName", topicMBean.getName());
                tempMap.put("num", topicMBean.getDequeueCount());
                topicSet.add(tempMap);
            }
        }
        return topicSet;
    }

    @Override
    public Map<String, Long> getTopicConsumerCount() {
        getBrokerMBean();
        ObjectName[] names = brokerMBean.getTopics();
        Map<String, Long> map = new HashMap<String, Long>();
        for(ObjectName name : names) {
            TopicViewMBean topicMBean = (TopicViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanConn, name, TopicViewMBean.class, true);
            if(topicMBean.getName().substring(0, 8).compareTo("ActiveMQ") != 0)
                map.put(topicMBean.getName(), topicMBean.getConsumerCount());
        }
        return map;
    }

    @Override
    public Map<String, Set<String>> getTopicSubscribers() {
        getBrokerMBean();
        ObjectName[] names = brokerMBean.getTopics();
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        for(ObjectName name : names) {
            TopicViewMBean topicMBean = (TopicViewMBean) MBeanServerInvocationHandler.newProxyInstance(mBeanConn, name, TopicViewMBean.class, true);
            Set<String> subscribers = new HashSet<String>();
            try {
                ObjectName[] subNames = topicMBean.getSubscriptions();
                for(ObjectName subName : subNames) {
                    subscribers.add(subName.getCanonicalName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            }
            map.put(topicMBean.getName(), subscribers);
        }
        return map;
    }

}
