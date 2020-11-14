package com.ibm.dev;


import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import com.ibm.integration.admin.http.HttpClient;
import com.ibm.integration.admin.http.HttpResponse;
import com.ibm.integration.admin.model.IntegrationServerModel;
import com.ibm.integration.admin.proxy.IntegrationAdminException;

public class ExampleACEV11AdminProgram_HTTPClient {

	public static void main(String[] args) {
		
		try {
			log("==========================================================================================");
			log("This v11 admin program shows the use of com.ibm.integration.admin.http classes (STRATEGIC)");
			log("==========================================================================================");
			
			String nodeName = "TESTNODE";
			HttpClient httpClient = new HttpClient(nodeName);
			HttpResponse httpResponse = httpClient.getMethod("/apiv2/servers/default?depth=4");
			IntegrationServerModel integrationServerModel = httpResponse.parseResponseBody(IntegrationServerModel.class);
			log("Connecting locally to the integration node named "+nodeName);
			log("URL: "+ httpResponse.getUrlPath());
			log("StatusCode: "+ httpResponse.getStatusCode());
			log("ReasonCode: "+ httpResponse.getReason());		
			log("BuildLevel: "+ integrationServerModel.getDescriptiveProperties().getBuildLevel());
			log("PlatformArchitecture: "+ integrationServerModel.getDescriptiveProperties().getPlatformArchitecture());
			log("PlatformName: "+ integrationServerModel.getDescriptiveProperties().getPlatformName());
			log("PlatformVersion: "+ integrationServerModel.getDescriptiveProperties().getPlatformVersion());
			log("ProductName: "+ integrationServerModel.getDescriptiveProperties().getProductName());
			log("ProductVersion: "+ integrationServerModel.getDescriptiveProperties().getVersion());						
		} catch (IOException | InterruptedException | IntegrationAdminException e) {
			e.printStackTrace();
		}		
	}
	
    private static void log(String string) {
    	DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.getDefault());
    	String formattedDate = df.format(new Date());
    	System.out.println("("+formattedDate+") "+string);
    }
	
}
