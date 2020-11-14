package com.ibm.dev;

import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputTerminal;
import com.ibm.broker.plugin.MbMessage;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbNode;
import com.ibm.broker.plugin.MbNodeInterface;
import com.ibm.broker.plugin.MbOutputTerminal;
import com.ibm.broker.plugin.MbPolicy;
import com.ibm.broker.plugin.MbXMLNSC;

public class PolicyQueryNode extends MbNode implements MbNodeInterface {

	String attrUserDefinedPolicyProject = "myPolicyProject";
	String attrUserDefinedPolicy = "myPolicy";
	String attrUserDefinedPolicyProperty = "myProperty";
	
	public static String getNodeName() {
		return "com_ibm_dev_PolicyQueryNode";
	}
	
	public String getUserDefinedPolicyProject() {
		return attrUserDefinedPolicyProject;
	}
	public void setUserDefinedPolicyProject(String value)
	{
		attrUserDefinedPolicyProject = value;
	}

	public String getUserDefinedPolicy() {
		return attrUserDefinedPolicy;
	}
	public void setUserDefinedPolicy(String value)
	{
		attrUserDefinedPolicy = value;
	}
	
	public String getUserDefinedPolicyProperty() {
		return attrUserDefinedPolicyProperty;
	}
	public void setUserDefinedPolicyProperty(String value)
	{
		attrUserDefinedPolicyProperty = value;
	}
	
	public PolicyQueryNode() throws MbException
	{
		createInputTerminal("in");
		createOutputTerminal("out");
	}
	
	@Override
	public void evaluate(MbMessageAssembly inAssembly, MbInputTerminal in) throws MbException {
		MbPolicy myPol = MbPolicy.getPolicy("UserDefined","{"+attrUserDefinedPolicyProject+"}:"+attrUserDefinedPolicy);
		String myMsg = myPol.getPropertyValueAsString(attrUserDefinedPolicyProperty);
		MbMessage inMessage = inAssembly.getMessage();
		MbMessage outMessage = new MbMessage();
		MbMessageAssembly outAssembly = new MbMessageAssembly(inAssembly,  outMessage);
		copyMessageHeaders(inMessage, outMessage);
		MbElement outRoot = outAssembly.getMessage().getRootElement();
		MbElement ResponseMessage =
				outRoot.createElementAsLastChild(MbXMLNSC.PARSER_NAME).createElementAsFirstChild(MbElement.TYPE_NAME,  "ResponseMessage",  null);
		ResponseMessage.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "UserDefinedPolicyProject", attrUserDefinedPolicyProject);
		ResponseMessage.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "UserDefinedPolicy", attrUserDefinedPolicy);
		ResponseMessage.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "UserDefinedPolicyProperty", attrUserDefinedPolicyProperty);
		ResponseMessage.createElementAsLastChild(MbElement.TYPE_NAME_VALUE, "UserDefinedPolicyPropertyValue", myMsg);
		MbOutputTerminal out = getOutputTerminal("out");
		out.propagate(outAssembly);
	}

	public void copyMessageHeaders(MbMessage inMessage, MbMessage outMessage) throws MbException {
		MbElement outRoot = outMessage.getRootElement();
		// iterate through the headers starting with the first child of the root element
		MbElement header = inMessage.getRootElement().getFirstChild();
		while (header != null && header.getNextSibling() != null)
		{
			// copy the header and add it to the out message
			outRoot.addAsLastChild(header.copy());
			// move along to next header
			header = header.getNextSibling();
		}
	}
}
