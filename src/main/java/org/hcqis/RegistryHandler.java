package org.hcqis;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hcqis.client.ClientManager;
import org.hcqis.model.Registry;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RegistryHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(RegistryHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(final Map<String, Object> input, final Context context) {
		LOG.info("received: {}", input);

		return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(createClient(this.extractRegistry(input)))
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless")).build();
	}

	Registry extractRegistry(final Map<String, Object> input) {
		final ObjectMapper objectMapper = new ObjectMapper();
		Registry registry;
		try {
			registry = objectMapper.readValue(
				input.get("body").toString(), Registry.class);
		} catch (IOException e) {
			throw new RuntimeException("Issue parsing input body", e);
		}
		return registry;
	}

	String createClient(final Registry registry) {
		final ObjectMapper objectMapper = new ObjectMapper();
		final ClientManager manager = new ClientManager();
		String json;
		try {
			json = objectMapper.writeValueAsString(manager.createClient(registry));
		} catch(JsonProcessingException ex) {
			throw new AuthAPIException("Problem serializing client registration response", ex);
		}
		return json;
	}
}
