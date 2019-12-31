package org.hcqis;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hcqis.jwt.JWTManager;
import org.hcqis.model.Registry;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RegistryHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(RegistryHandler.class);

	@Override
	public ApiGatewayResponse handleRequest(final Map<String, Object> input, final Context context) {
		LOG.info("received: {}", input);

		return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(createJWT(this.extractRegistry(input)))
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

	String createJWT(final Registry registry) {
		final JWTManager manager = new JWTManager();
		return manager.generateToken(registry);
	}
}
