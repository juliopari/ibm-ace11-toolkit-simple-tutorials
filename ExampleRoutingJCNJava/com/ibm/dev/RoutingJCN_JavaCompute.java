package com.ibm.dev;

import com.ibm.broker.javacompute.MbJavaComputeNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbUserException;

public class RoutingJCN_JavaCompute extends MbJavaComputeNode {

	private static final Exception MbUserException = null;

	public void evaluate(MbMessageAssembly assembly) throws MbException {
		MbOutputTerminal out = getOutputTerminal("out");
		MbOutputTerminal alt = getOutputTerminal("alternate");

		try {
			MbMessage message = assembly.getMessage();
			// ----------------------------------------------------------
			// Add user code below
			MbElement dataElement = message.getRootElement().getLastChild().getFirstChild();								
			if (dataElement.getFirstElementByPath("Country").getValueAsString().equals("GB")) {
				out.propagate(assembly);	
			} else if (dataElement.getFirstElementByPath("Country").getValueAsString().equals("US")) {
				alt.propagate(assembly);	
			} else {
				MbUserException mbue = new MbUserException(this, "evaluate()", "","", "The country was not GB nor US", null);
			    throw mbue;
			}
			// Note that JavaCompute nodes only provide two standard output terminals - named Output (out) and Alternate (alt)
			// JavaCompute nodes also don't provide the facility to "Add output terminal" to create further terminals
			// For these reasons, if you wanted to route down more than two alternate paths in your flow you would be better
			// advised to either:
			//   1. Use a Route node (which does allow you to add terminals) or
			//   2. Use a Compute node (which provides 4 static terminals) and also provides the ESQL PROPAGATE to LABEL function
			// If you would like to use a JavaCompute node to route to more than two outputs, you could also achieve this with a 
			// downstream RouteToLabel node, having set up the LocalEnvironment tree to control the distribution using code such 
			// as the example commented lines below:
			//
			// MbMessage localEnv = assembly.getLocalEnvironment();
			// MbMessage outputLocalEnv = new MbMessage(localEnv);
			// MbElement Destination = outputLocalEnv.getRootElement().createElementAsFirstChild(MbElement.TYPE_NAME_VALUE,"Destination", null);
			// MbElement RouterList = Destination.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE,"RouterList", null);
			// MbElement DestinationData = RouterList.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE,"DestinationData", null);			
			// DestinationData.createElementAsFirstChild(MbElement.TYPE_NAME_VALUE,"labelName", dataElement.getFirstElementByPath("Country").getValueAsString());
			
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

	}

}
