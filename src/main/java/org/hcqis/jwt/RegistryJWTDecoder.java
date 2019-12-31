package org.hcqis.jwt;

import org.hcqis.model.Registry;

import io.jsonwebtoken.Claims;
import ro.ghionoiu.kmsjwt.key.KeyDecrypt;
import ro.ghionoiu.kmsjwt.token.JWTDecoder;
import ro.ghionoiu.kmsjwt.token.JWTVerificationException;

class RegistryJWTDecoder {
    private JWTDecoder decoder;

    RegistryJWTDecoder(final KeyDecrypt keyDecrypt) {
        this.decoder = new JWTDecoder(keyDecrypt);
    }

    Registry decodeAndVerify(String jwt) {
        Registry registry;

        try {
            Claims claims = this.decoder.decodeAndVerify(jwt);
            registry = this.mapRegistry(claims);
            // TODO: suss out any subsequent validations to be performed
            // validateRegistry()
        } catch (JWTVerificationException e) {
            throw new JWTManagerException("Issue decoding JWT", e);
        }

        return registry;
    }

    private Registry mapRegistry(Claims claims) {
        Registry registry = new Registry();
        registry.setEmailAddress(claims.get("emailAddress").toString());
        registry.setMfaDeviceId(claims.get("mfaDeviceId").toString());
        registry.setUser(claims.get("user").toString());
        return registry;
    }
}