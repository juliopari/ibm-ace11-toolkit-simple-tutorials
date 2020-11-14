package com.ibm.dev;

import java.util.Map;
import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;
import com.ibm.broker.plugin.MbXMLNSC;
import com.ibm.integration.admin.proxy.ApplicationProxy;
import com.ibm.integration.admin.proxy.IntegrationNodeProxy;
import com.ibm.integration.admin.proxy.IntegrationServerProxy;
import com.ibm.integration.admin.proxy.MessageFlowProxy;

public class Keywords_JavaCompute extends MbJavaComputeNode {

	public void evaluate(MbMessageAssembly inAssembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");		
		MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = new MbMessage();
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,outMessage);
		
		try {					
			// ----------------------------------------------------------
			// Add user code below
			copyMessageHeaders(inMessage, outMessage);			
			String appName = getMessageFlow().getApplicationName();
			String flowName = getMessageFlow().getName();
			IntegrationServerProxy myServer = new IntegrationServerProxy();
			IntegrationNodeProxy myNode = myServer.getIntegrationNodeProxy();						
			ApplicationProxy myApp = myServer.getApplicationByName(appName, true);
			MessageFlowProxy myFlow = myApp.getMessageFlowByName(flowName, null, true);
			Map<String,String> myKeywords = myFlow.getMessageFlowModel(true).getDescriptiveProperties().getKeywords();
			MbElement outElement = outMessage.getRootElement().createElementAsLastChild(MbXMLNSC.PARSER_NAME);
			MbElement messageElement = outElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Message", null);
			if (myNode != null) { 
				messageElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "NodeName", myNode.getName());
			}
			messageElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ServerName", myServer.getName());
			messageElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "ApplicationName", appName);
		    messageElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "FlowName", flowName);
		    MbElement keywords = messageElement.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "Keywords", null);
			for (Map.Entry<String, String> entry : myKeywords.entrySet()) {
			    keywords.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, entry.getKey(), entry.getValue());
			}			
			// End of user code
			// ----------------------------------------------------------
		} catch (MbException e) {
			// Re-throw to allow Broker handling of MbException
			throw e;
		} catch (RuntimeException e) {
			// Re-throw to allow Broker handling of RuntimeException
			throw e;
		} catch (Exception e) {
			// Consider replacing Exception with type(s) thrown by user code
			// Example handling ensures all exceptions are re-thrown to be handled in the flow
			throw new MbUserException(this, "evaluate()", "", "", e.toString(), null);
		}
		// The following should only be changed
		// if not propagating message to the 'out' terminal
		out.propagate(outAssembly);
	}
	
	public void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage)
			throws MbException {
		MbElement outRoot = outMessage.getRootElement();

		// iterate though the headers starting with the first child of the root
		// element
		MbElement header = inMessage.getRootElement().getFirstChild();
		while (header != null && header.getNextSibling() != null) // stop before
																	// the last
																	// child
																	// (body)
		{
			// copy the header and add it to the out message
			outRoot.addAsLastChild(header.copy());
			// move along to next header
			header = header.getNextSibling();
		}
	}
}
