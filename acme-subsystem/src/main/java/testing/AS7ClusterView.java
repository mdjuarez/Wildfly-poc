package testing;

import java.util.HashMap;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public class AS7ClusterView {
	public static void main(String[] args) throws Exception {
		 String host = "localhost";
	        int port = 9990;  // management-web port
	        String urlString =
	            System.getProperty("jmx.service.url","service:jmx:http-remoting-jmx://" + host + ":" + port);
	        JMXServiceURL serviceURL = new JMXServiceURL(urlString);
	        String username = "admin";
	        String password = "admin123";
	        HashMap env = new HashMap();
	        String[] creds = new String[2];
	        creds[0] = username;
	        creds[1] = password;
	        env.put(JMXConnector.CREDENTIALS, creds);
	        JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL, env);
	       
	        
	        MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
	        
	        //Invoke on the WildFly 8 MBean server
	        int count = connection.getMBeanCount();
	        String mb =connection.getDefaultDomain();
	        ObjectName objectName1 = new ObjectName("jboss.msc:type=container,name=jboss-as");
	        MBeanInfo info = connection.getMBeanInfo(objectName1);
	        
	        System.out.println(info.getDescriptor());
	        System.out.println(info.getAttributes().length);
	        System.out.println(mb);
	        System.out.println(info);
//	        String webClusterObjectName="jgroups:type=channel";
//			ObjectName objectName = new ObjectName(webClusterObjectName);
//			String clusterView = (String) connection.getAttribute(objectName,"View");
//			Long receivedMessages = (Long) connection.getAttribute(objectName,"ReceivedMessages");
//			String name = (String) connection.getAttribute(objectName, "Name");
//			String clusterName = (String) connection.getAttribute(objectName,"ClusterName");
	        
	        
	        
	        
	        
	        
	       
	        jmxConnector.close();

//		String host = "localhost"; // Your JBoss Native Interface Bin Address default is localhost
//		int port = 9990;             // management port     // In Domain Mode you should use  4447 port of individual server
//		String urlString = "service:jmx:remoting-jmx://" + host + ":" + port;
//		System.out.println(" \n\n\t**** urlString: " + urlString);
//                String webClusterObjectName="jgroups:type=channel,cluster=\"web\"";
//                //String ejbClusterObjectName="jgroups:type=channel,cluster=\"ejb\"";
//
//		JMXServiceURL serviceURL = new JMXServiceURL(urlString);
//		 String username = "admin";
//	        String password = "admin123";
//	        HashMap env = new HashMap();
//	        String[] creds = new String[2];
//	        creds[0] = username;
//	        creds[1] = password;
//	        env.put(JMXConnector.CREDENTIALS, creds);
//
////	        JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL, env);
////		Hashtable h = new Hashtable();
////		String[] credentials = new String[] { "admin", "admin123" };
////		h.put("jmx.remote.credentials", credentials);
//
//		JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceURL,null);
//		MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
//		ObjectName objectName = new ObjectName(webClusterObjectName);
//		String clusterView = (String) connection.getAttribute(objectName,"View");
//		Long receivedMessages = (Long) connection.getAttribute(objectName,"ReceivedMessages");
//		String name = (String) connection.getAttribute(objectName, "Name");
//		String clusterName = (String) connection.getAttribute(objectName,"ClusterName");
//
//		System.out.println(" clusterView = " + clusterView);
//		System.out.println(" receivedMessages = " + receivedMessages);
//		System.out.println(" name = " + name);
//		System.out.println(" clusterName = " + clusterName);
//		jmxConnector.close();
	}

}
