package com.ibm.dev;

import com.ibm.broker.config.appdev.InputTerminal;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.NodeProperty;
import com.ibm.broker.config.appdev.OutputTerminal;

/*** 
 * <p>  <I>PolicyQueryNodeUDN</I> instance</p>
 * <p></p>
 */
public class PolicyQueryNodeUDN extends Node {

	private static final long serialVersionUID = 1L;

	// Node constants
	protected final static String NODE_TYPE_NAME = "com/ibm/dev/PolicyQueryNode";
	protected final static String NODE_GRAPHIC_16 = "platform:/plugin/PolicyQuery/icons/full/obj16/com/ibm/dev/PolicyQuery.gif";
	protected final static String NODE_GRAPHIC_32 = "platform:/plugin/PolicyQuery/icons/full/obj30/com/ibm/dev/PolicyQuery.gif";

	protected final static String PROPERTY_USERDEFINEDPOLICYPROJECT = "userDefinedPolicyProject";
	protected final static String PROPERTY_USERDEFINEDPOLICY = "userDefinedPolicy";
	protected final static String PROPERTY_USERDEFINEDPOLICYPROPERTY = "userDefinedPolicyProperty";

	protected NodeProperty[] getNodeProperties() {
		return new NodeProperty[] {
			new NodeProperty(PolicyQueryNodeUDN.PROPERTY_USERDEFINEDPOLICYPROJECT,		NodeProperty.Usage.MANDATORY,	false,	NodeProperty.Type.STRING, "myPolicyProject","","",	"com/ibm/dev/PolicyQuery",	"PolicyQuery"),
			new NodeProperty(PolicyQueryNodeUDN.PROPERTY_USERDEFINEDPOLICY,		NodeProperty.Usage.MANDATORY,	false,	NodeProperty.Type.STRING, "myPolicy","","",	"com/ibm/dev/PolicyQuery",	"PolicyQuery"),
			new NodeProperty(PolicyQueryNodeUDN.PROPERTY_USERDEFINEDPOLICYPROPERTY,		NodeProperty.Usage.MANDATORY,	false,	NodeProperty.Type.STRING, "myProperty","","",	"com/ibm/dev/PolicyQuery",	"PolicyQuery")
		};
	}

	public PolicyQueryNodeUDN() {
	}

	public final InputTerminal INPUT_TERMINAL_IN = new InputTerminal(this,"InTerminal.in");
	@Override
	public InputTerminal[] getInputTerminals() {
		return new InputTerminal[] {
			INPUT_TERMINAL_IN
	};
	}

	public final OutputTerminal OUTPUT_TERMINAL_OUT = new OutputTerminal(this,"OutTerminal.out");
	@Override
	public OutputTerminal[] getOutputTerminals() {
		return new OutputTerminal[] {
			OUTPUT_TERMINAL_OUT
		};
	}

	@Override
	public String getTypeName() {
		return NODE_TYPE_NAME;
	}

	protected String getGraphic16() {
		return NODE_GRAPHIC_16;
	}

	protected String getGraphic32() {
		return NODE_GRAPHIC_32;
	}

	/**
	 * Set the <I>PolicyQueryNodeUDN</I> "<I>userDefinedPolicyProject</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>userDefinedPolicyProject</I>"
	 */
	public PolicyQueryNodeUDN setUserDefinedPolicyProject(String value) {
		setProperty(PolicyQueryNodeUDN.PROPERTY_USERDEFINEDPOLICYPROJECT, value);
		return this;
	}

	/**
	 * Get the <I>PolicyQueryNodeUDN</I> "<I>userDefinedPolicyProject</I>" property
	 * 
	 * @return String; the value of the property "<I>userDefinedPolicyProject</I>"
	 */
	public String getUserDefinedPolicyProject() {
		return (String)getPropertyValue(PolicyQueryNodeUDN.PROPERTY_USERDEFINEDPOLICYPROJECT);
	}

	/**
	 * Set the <I>PolicyQueryNodeUDN</I> "<I>userDefinedPolicy</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>userDefinedPolicy</I>"
	 */
	public PolicyQueryNodeUDN setUserDefinedPolicy(String value) {
		setProperty(PolicyQueryNodeUDN.PROPERTY_USERDEFINEDPOLICY, value);
		return this;
	}

	/**
	 * Get the <I>PolicyQueryNodeUDN</I> "<I>userDefinedPolicy</I>" property
	 * 
	 * @return String; the value of the property "<I>userDefinedPolicy</I>"
	 */
	public String getUserDefinedPolicy() {
		return (String)getPropertyValue(PolicyQueryNodeUDN.PROPERTY_USERDEFINEDPOLICY);
	}

	/**
	 * Set the <I>PolicyQueryNodeUDN</I> "<I>userDefinedPolicyProperty</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>userDefinedPolicyProperty</I>"
	 */
	public PolicyQueryNodeUDN setUserDefinedPolicyProperty(String value) {
		setProperty(PolicyQueryNodeUDN.PROPERTY_USERDEFINEDPOLICYPROPERTY, value);
		return this;
	}

	/**
	 * Get the <I>PolicyQueryNodeUDN</I> "<I>userDefinedPolicyProperty</I>" property
	 * 
	 * @return String; the value of the property "<I>userDefinedPolicyProperty</I>"
	 */
	public String getUserDefinedPolicyProperty() {
		return (String)getPropertyValue(PolicyQueryNodeUDN.PROPERTY_USERDEFINEDPOLICYPROPERTY);
	}

	public String getNodeName() {
		String retVal = super.getNodeName();
		if ((retVal==null) || retVal.equals(""))
			retVal = "PolicyQuery";
		return retVal;
	};
}
