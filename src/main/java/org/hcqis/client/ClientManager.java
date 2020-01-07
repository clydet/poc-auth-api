package org.hcqis.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.CreateUserPoolClientRequest;
import com.amazonaws.services.cognitoidp.model.CreateUserPoolClientResult;
import com.amazonaws.services.cognitoidp.model.UserPoolClientType;

import org.hcqis.model.Registry;

public class ClientManager {
  private static final Logger LOG = LogManager.getLogger(ClientManager.class);

  private String userPoolId;
  private String userPoolResourceServer;

  public ClientManager() {
    this.userPoolId = System.getenv("USER_POOL_ID");
    this.userPoolResourceServer = System.getenv("USER_POOL_RESOURCE_SERVER");
  }

  public UserPoolClientType createClient(final Registry registry) {
    // --allowed-o-auth-flows client_credentials 
    // --client-name test 
    // --generate-secret 
    // --allowed-o-auth-scopes transactions/post 
    // --allowed-o-auth-flows-user-pool-client
    CreateUserPoolClientRequest createUserPoolClientRequest = new CreateUserPoolClientRequest()
      .withUserPoolId(this.userPoolId)
      .withClientName(registry.getClientName())
      .withAllowedOAuthFlows(Arrays.asList("client_credentials"))
      .withGenerateSecret(true)
      .withAllowedOAuthFlowsUserPoolClient(true);
    createUserPoolClientRequest.setAllowedOAuthScopes(Arrays.asList(
      this.userPoolResourceServer + "/ReadScope",
      this.userPoolResourceServer + "/WriteScope"));
    createUserPoolClientRequest.putCustomQueryParameter("meep", "mawp");
    AWSCognitoIdentityProvider client = AWSCognitoIdentityProviderClient.builder().defaultClient();
    CreateUserPoolClientResult result = client.createUserPoolClient(createUserPoolClientRequest);
    
    LOG.info("In ClientManager " + result.getSdkResponseMetadata().toString());
    LOG.info("In ClientManager " + result.getSdkHttpMetadata().toString());
    return result.getUserPoolClient();
  }
}