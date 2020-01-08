package org.hcqis.client;

import java.util.Arrays;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClient;
import com.amazonaws.services.cognitoidp.model.CreateUserPoolClientRequest;
import com.amazonaws.services.cognitoidp.model.CreateUserPoolClientResult;
import com.amazonaws.services.cognitoidp.model.UserPoolClientType;

import org.hcqis.model.Registry;

public class ClientManager {
  private String userPoolId;
  private String userPoolResourceServer;

  public ClientManager() {
    this.userPoolId = System.getenv("USER_POOL_ID");
    this.userPoolResourceServer = System.getenv("USER_POOL_RESOURCE_SERVER");
  }

  public UserPoolClientType createClient(final Registry registry) {
    CreateUserPoolClientRequest createUserPoolClientRequest = new CreateUserPoolClientRequest()
      .withUserPoolId(this.userPoolId)
      .withClientName(registry.getClientName())
      .withAllowedOAuthFlows(Arrays.asList("client_credentials"))
      .withGenerateSecret(true)
      .withAllowedOAuthFlowsUserPoolClient(true);
    
    createUserPoolClientRequest.setAllowedOAuthScopes(Arrays.asList(
      this.userPoolResourceServer + "/read",
      this.userPoolResourceServer + "/write"));
    
    AWSCognitoIdentityProvider client = AWSCognitoIdentityProviderClient.builder().defaultClient();
    CreateUserPoolClientResult result = client.createUserPoolClient(createUserPoolClientRequest);

    return result.getUserPoolClient();
  }
}