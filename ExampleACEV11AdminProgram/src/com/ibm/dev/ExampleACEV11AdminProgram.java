package com.ibm.dev;

import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import com.ibm.integration.admin.proxy.IntegrationAdminException;
import com.ibm.integration.admin.proxy.IntegrationNodeProxy;
import com.ibm.integration.admin.proxy.IntegrationServerProxy;
import com.ibm.integration.admin.proxy.ApplicationProxy;
import com.ibm.integration.admin.proxy.MessageFlowProxy;

public class ExampleACEV11AdminProgram {

	private final static String NODE_INDENT = "";    
    private final static String SERVER_INDENT = "  ";
    private final static String APP_INDENT = "    ";    
    private final static String MF_INDENT = "      ";
    
   public static void main(String[] args) {
	   log(NODE_INDENT+"=============================================================================================");
	   log(NODE_INDENT+"This v11 admin program shows the use of com.ibm.integration.admin.proxy.* classes (STRATEGIC)");
	   log(NODE_INDENT+"=============================================================================================");
	   
	   int nodePortNumber = 4414;
	   String nodeHostName = "localhost";    	    	
	   IntegrationNodeProxy connectedInstance = connectNode(nodeHostName, nodePortNumber);    	
	   try {
		   displayNodeInfo(connectedInstance);
	   } catch (IntegrationAdminException e) {
		   e.printStackTrace();
	   }		
    }
    
   private static void displayNodeInfo(IntegrationNodeProxy b) throws IntegrationAdminException {

	   displayNodeRunstate(b);    
	   List<String> allServersInThisNode = b.getIntegrationServerNames();
	   if (allServersInThisNode.size() > 0) {
		   Iterator<String> serverIterator = allServersInThisNode.iterator();
		   while(serverIterator.hasNext()) {
			   IntegrationServerProxy thisServer = b.getIntegrationServerByName(serverIterator.next());
			   displayServerRunstate(thisServer);
			   List<ApplicationProxy> allAppsInThisServer = thisServer.getAllApplications(true);
			   if (allAppsInThisServer.size() > 0) {
				   Iterator<ApplicationProxy> appIterator = allAppsInThisServer.iterator();
				   while(appIterator.hasNext()) {
					   ApplicationProxy thisApp = appIterator.next();
					   displayApplicationRunstate(thisApp);
					   List<MessageFlowProxy> allFlowsInThisApp = thisApp.getAllMessageFlows(true);
					   if (allFlowsInThisApp.size() > 0) {
						   Iterator<MessageFlowProxy> flowIterator = allFlowsInThisApp.iterator();
						   while(flowIterator.hasNext()) {
							   MessageFlowProxy thisFlow = flowIterator.next();
							   displayMessageFlowRunstate(thisFlow);
						   }
					   } else {
						   log(MF_INDENT+"There are no message flows in this application!");
					   }
				   }
			   } else {
				   log(SERVER_INDENT+"There are no applications in this server!");				   
			   }
		   }
	   } else {
		   log(SERVER_INDENT+"There are no servers in this node!");
	   }
   }

   private static void displayNodeRunstate(IntegrationNodeProxy thisNode) throws IntegrationAdminException {       
       String nodeName = thisNode.getName();
       log(NODE_INDENT+nodeName+" is running");       
   }

   private static void displayServerRunstate(IntegrationServerProxy thisServer) throws IntegrationAdminException {
	   boolean isRunning = thisServer.getIntegrationServerModel(true).getActive().isRunning();
	   String serverName = thisServer.getName();
	   String nodeName = thisServer.getIntegrationNodeProxy().getName();
	   if (isRunning) {
		   log(SERVER_INDENT+nodeName+","+serverName+" is running");
	   } else {
		   log(SERVER_INDENT+nodeName+","+serverName+" is stopped");		   
	   }	   
   }
   
   private static void displayApplicationRunstate(ApplicationProxy thisApp) throws IntegrationAdminException {   	
	   boolean isRunning = thisApp.getApplicationModel(true).getActive().isRunning();	   
	   String applicationName = thisApp.getName();
	   String serverName = thisApp.getIntegrationServerProxy().getName();
	   String nodeName = thisApp.getIntegrationServerProxy().getIntegrationNodeProxy().getName();
	   if (isRunning) {
		   log(APP_INDENT+nodeName+","+serverName+","+applicationName+" is running");
	   } else {
		   log(APP_INDENT+nodeName+","+serverName+","+applicationName+" is stopped");
	   }
   }

   private static void displayMessageFlowRunstate(MessageFlowProxy thisFlow) throws IntegrationAdminException {	   
	   boolean isRunning = thisFlow.getMessageFlowModel(true).getActive().isRunning();
	   String messageFlowName = thisFlow.getName();
	   String applicationName = thisFlow.getApplicationProxy().getName();
	   String serverName = thisFlow.getApplicationProxy().getIntegrationServerProxy().getName();
	   String nodeName = thisFlow.getApplicationProxy().getIntegrationServerProxy().getIntegrationNodeProxy().getName();	   
	   // In ACEv11.0.0.5, there was a defect whereby the method getApplicationProxy()
	   // was incorrectly named getApplicationServer(). The following 3 lines are left commented out to 
	   // and can be swapped for the 3 lines above this comment if you are using v11.0.0.5
	   //
	   // String applicationName = thisFlow.getApplicationServer().getName();
	   // String serverName = thisFlow.getApplicationServer().getIntegrationServerProxy().getName();
	   // String nodeName = thisFlow.getApplicationServer().getIntegrationServerProxy().getIntegrationNodeProxy().getName();
	   if (isRunning) {
       	log(MF_INDENT+nodeName+","+serverName+","+applicationName+","+messageFlowName+" is running");        	
       } else {
       	log(MF_INDENT+nodeName+","+serverName+","+applicationName+","+messageFlowName+" is stopped");      
       }	   
   }
   
    private static IntegrationNodeProxy connectNode(String nodeHostName, int nodePortNumber) {
    	// This example does not use userid, password, SSL (as indicated by the last 3 properties).
    	IntegrationNodeProxy connectedInstance = new IntegrationNodeProxy(nodeHostName, nodePortNumber, "", "", false);    	
        log(NODE_INDENT+"Connecting to host "+nodeHostName+" and port "+nodePortNumber);
        return connectedInstance;    	
    }
    
    private static void log(String string) {
    	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.getDefault());
    	String formattedDate = df.format(new Date());
    	System.out.println("("+formattedDate+") "+string);
    }
    
}
