package com.ibm.dev;

import java.text.DateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import com.ibm.broker.config.proxy.ApplicationProxy;
import com.ibm.broker.config.proxy.BrokerProxy;
import com.ibm.broker.config.proxy.ConfigManagerProxyException;
import com.ibm.broker.config.proxy.ConfigManagerProxyLoggedException;
import com.ibm.broker.config.proxy.ConfigManagerProxyPropertyNotInitializedException;
import com.ibm.broker.config.proxy.ExecutionGroupProxy;
import com.ibm.broker.config.proxy.IntegrationNodeConnectionParameters;
import com.ibm.broker.config.proxy.MessageFlowProxy;

public class ExampleIIBV10AdminProgram {

    private final static String NODE_INDENT = "";    
    private final static String SERVER_INDENT = "  ";
    private final static String APP_INDENT = "    ";    
    private final static String MF_INDENT = "      ";

    public static void main(String[] args) {
        
    	log(NODE_INDENT+"==========================================================================================");
    	log(NODE_INDENT+"This v11 admin program shows the use of com.ibm.broker.config.proxy.* classes (DEPRECATED)");
    	log(NODE_INDENT+"==========================================================================================");
 	   
    	int nodePortNumber = 4414;
    	String nodeHostName = "localhost";    	    	
    	BrokerProxy connectedInstance = connectNode(nodeHostName, nodePortNumber);
    	
        try {
			displayNodeInfo(connectedInstance);
		} catch (ConfigManagerProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    private static BrokerProxy connectNode(String nodeHostName, int nodePortNumber) {

    	BrokerProxy connectedInstance = null;
    	IntegrationNodeConnectionParameters cp = new IntegrationNodeConnectionParameters(nodeHostName, nodePortNumber);
    	
    	try {
    		connectedInstance = BrokerProxy.getInstance(cp);
		} catch (ConfigManagerProxyLoggedException e) {
			e.printStackTrace();
		}
        log(NODE_INDENT+"Connecting to host "+nodeHostName+" and port "+nodePortNumber);
        return connectedInstance;    	
    }
    
    private static void displayNodeInfo(BrokerProxy b)
    		throws ConfigManagerProxyException {

    	displayNodeRunstate(b);    
    	// Get an unfiltered enumeration of all the integration servers in this integration node
    	Enumeration<ExecutionGroupProxy> allServersInThisNode = b.getExecutionGroups(null);
    	if (allServersInThisNode != null) {
    		while (allServersInThisNode.hasMoreElements()) {
    			ExecutionGroupProxy thisServer = allServersInThisNode.nextElement();        
    			displayServerRunstate(thisServer);
    			// Get an unfiltered enumeration of all applications in this integration server
    			Enumeration<ApplicationProxy> allAppsInThisServer = thisServer.getApplications(null);
    			if (allAppsInThisServer != null) {
    				while (allAppsInThisServer.hasMoreElements()) {
    					ApplicationProxy thisApp = allAppsInThisServer.nextElement();
    					displayApplicationRunstate(thisApp);    	
    					// Get an unfiltered enumeration of all message flows in this integration server
    					Enumeration<MessageFlowProxy> allFlowsInThisApp = thisApp.getMessageFlows(null);
    					if (allFlowsInThisApp != null) {
    						while (allFlowsInThisApp.hasMoreElements()) {
    							MessageFlowProxy thisFlow = allFlowsInThisApp.nextElement();
    							displayMessageFlowRunstate(thisFlow);
    						}
    					} else {
    	    	    		log(MF_INDENT+"There are no message flows in this application!");
    	    	    	}
    				}
    			} else {
    	    		log(APP_INDENT+"There are no applications in this server!");
    	    	}
    		} 
    	} else {
    		log(SERVER_INDENT+"There are no servers in this node!");
    	}
    }

    private static void displayNodeRunstate(BrokerProxy thisNode)
    throws ConfigManagerProxyPropertyNotInitializedException {
        
        boolean isRunning = thisNode.isRunning();
        String nodeName = thisNode.getName();
        if (isRunning) {
    		log(NODE_INDENT+nodeName+" is running");
        } else {
    		log(NODE_INDENT+new String[] {nodeName}+" is stopped");
        }
    }
    
    private static void displayServerRunstate(ExecutionGroupProxy thisServer)
    		throws ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException {
    	boolean isRunning = thisServer.isRunning();
    	String serverName = thisServer.getName();
    	String nodeName = thisServer.getParent().getName();

    	if (isRunning) {
    		log(SERVER_INDENT+nodeName+","+serverName+" is running");
    	} else {
    		log(SERVER_INDENT+nodeName+","+serverName+" is stopped");
    	}
    }

    private static void displayApplicationRunstate(ApplicationProxy thisApp)
    	    throws ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException {
    	boolean isRunning = thisApp.isRunning();
    	String applicationName = thisApp.getName();
    	String serverName = thisApp.getParent().getName();
    	String nodeName = thisApp.getParent().getParent().getName();
    	     
    	if (isRunning) {
    		log(APP_INDENT+nodeName+","+serverName+","+applicationName+" is running");    		
    	} else {
    		log(APP_INDENT+nodeName+","+serverName+","+applicationName+" is stopped");    	    
    	}
    }
    
    private static void displayMessageFlowRunstate(MessageFlowProxy thisFlow)
    throws ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException {
        boolean isRunning = thisFlow.isRunning();
        String messageFlowName = thisFlow.getName();
        String applicationName = thisFlow.getParent().getName();
        String serverName = thisFlow.getParent().getParent().getName();
        String nodeName = thisFlow.getParent().getParent().getParent().getName();
        
        if (isRunning) {
        	log(MF_INDENT+nodeName+","+serverName+","+applicationName+","+messageFlowName+" is running");        	
        } else {
        	log(MF_INDENT+nodeName+","+serverName+","+applicationName+","+messageFlowName+" is stopped");      
    	}
	}
        
    private static void log(String string) {
    	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.getDefault());
    	String formattedDate = df.format(new Date());
    	System.out.println("("+formattedDate+") "+string);
    }

}
