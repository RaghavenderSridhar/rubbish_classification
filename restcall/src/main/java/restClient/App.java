package restClient;

import java.io.File;
import java.net.URI;
import java.util.List;

import com.telstra.ApiClient;
import com.telstra.ApiException;
import com.telstra.Configuration;
import com.telstra.auth.OAuth;
import com.telstra.messaging.AuthenticationApi;
import com.telstra.messaging.MessageSentResponseSms;
import com.telstra.messaging.MessagingApi;
import com.telstra.messaging.OAuthResponse;
import com.telstra.messaging.ProvisionNumberRequest;
import com.telstra.messaging.ProvisionNumberResponse;
import com.telstra.messaging.ProvisioningApi;
import com.telstra.messaging.SendSMSRequest;



public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String fileName = "C:\\Users\\kartik gupta\\eclipse-workspace-hackathon\\restcall\\plastic36.jpg";
		//HttpClient httpclient = HttpClients.createDefault();

        try
        {
            File folder =new File("C:\\\\Users\\\\kartik gupta\\\\eclipse-workspace-hackathon\\\\restcall\\images");
            File[] listOfFiles = folder.listFiles();
            for(File file : listOfFiles)
            {
            	if(file.isFile())
            	{
            		System.out.println(file.getName());
            		String charset = "UTF-8";
                   
                    MultiPartUtility multipart = new MultiPartUtility("http://localhost:5000", charset);
                    
               
                     
                    multipart.addFilePart("image", file);
        
         
                    List<String> response = multipart.finish();
                     
                    
                    String code = "", message= "";
                    for (String line : response) {
                    	code = line.substring(1, 2);
                        //System.out.println(code);
                    }
                    System.out.println("classification code from server: "+ code);                
                    if(code.equalsIgnoreCase("1"))
                    {
                    	message = "PAPER";
                    }
                    else if(code.equalsIgnoreCase("2"))
                    {
                    	message = "CARDBOARD";
                    }
                    else if(code.equalsIgnoreCase("3"))
                    {
                    	message = "PLASTIC";
                    }
                    else if(code.equalsIgnoreCase("4"))
                    {
                    	message = "METAL";
                    }
                    else if(code.equalsIgnoreCase("5"))
                    {
                    	message = "TRASH";
                    }
                    else if(code.equalsIgnoreCase("0"))
                    {
                    	message = "GLASS";
                    }
                    System.out.println("classification messsage from server: " + message);
                    if(!message.isEmpty())
                    {
                    	sendMessage("garbage object found "+message);
                    }
            	}
            }
            
            
            
     
            
            
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

	}
	
	
	public static void sendMessage(String message)
	{
		ApiClient defaultClient = Configuration.getDefaultApiClient();
	    defaultClient.setBasePath("https://tapi.telstra.com/v2");

	    // Configure OAuth2 access token for authorization
	    OAuth auth = (OAuth) defaultClient.getAuthentication("auth");
	    AuthenticationApi authenticationApi = new AuthenticationApi(defaultClient);
	    String clientId = "cMg3k9nXKn1fsoc4lltGvXNciD4iyCWA";
	    String clientSecret = "VuwNyVYtRHzGvt3a";
	    String grantType = "client_credentials";
	    String scope = "NSMS";
	    try {
	      OAuthResponse oAuthResponse = authenticationApi.authToken(clientId, clientSecret, grantType, scope);
	      auth.setAccessToken(oAuthResponse.getAccessToken());
	    } catch (ApiException e) {
	      System.err.println("Exception when calling AuthenticationApi#authToken");
	      System.err.println("Status code: " + e.getCode());
	      System.err.println("Reason: " + e.getResponseBody());
	      System.err.println("Response headers: " + e.getResponseHeaders());
	      e.printStackTrace();
	    }

	    // Configure phone number subscription
	    ProvisioningApi provisioningApiInstance = new ProvisioningApi(defaultClient);
	    try {
	      ProvisionNumberRequest provisionNumberRequest = new ProvisionNumberRequest();
	      ProvisionNumberResponse result = provisioningApiInstance.createSubscription(provisionNumberRequest);
	      System.out.println(result);
	    } catch (ApiException e) {
	      System.err.println("Exception when calling ProvisioningApi#createSubscription");
	      System.err.println("Status code: " + e.getCode());
	      System.err.println("Reason: " + e.getResponseBody());
	      System.err.println("Response headers: " + e.getResponseHeaders());
	      e.printStackTrace();
	    }

	    // Send SMS
	    MessagingApi msgingApiInstance = new MessagingApi(defaultClient);
	    try {
	      SendSMSRequest sendSmsRequest = new SendSMSRequest();
	      sendSmsRequest.to("+91*******");
	      sendSmsRequest.body(message);
	      MessageSentResponseSms result = msgingApiInstance.sendSMS(sendSmsRequest);
	      System.out.println(result);
	    } catch (ApiException e) {
	      System.err.println("Exception when calling MessagingApi#sendSMS");
	      System.err.println("Status code: " + e.getCode());
	      System.err.println("Reason: " + e.getResponseBody());
	      System.err.println("Response headers: " + e.getResponseHeaders());
	      e.printStackTrace();
	    }
	}

}
