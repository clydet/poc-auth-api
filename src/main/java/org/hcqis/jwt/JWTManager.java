package org.hcqis.jwt;

import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;

import ro.ghionoiu.kmsjwt.key.KMSDecrypt;
import ro.ghionoiu.kmsjwt.key.KMSEncrypt;
import ro.ghionoiu.kmsjwt.key.KeyOperationException;
import ro.ghionoiu.kmsjwt.token.JWTEncoder;
import org.hcqis.model.Registry;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;

public class JWTManager {
    private String region;
    private String keyARN;
    private Integer tokenDuration;

    private static Date expirationDate(final int expiresInDays) {
        return new Date(Instant.now()
            .plus(expiresInDays, ChronoUnit.DAYS)
            .toEpochMilli());
    }

    public JWTManager() {
        this.region = System.getenv("AWS_REGION");
        this.keyARN = System.getenv("KEY_ARN");
        this.tokenDuration = Integer.parseInt(System.getenv("TOKEN_DURATION"));
    }

    public String generateToken(final Registry registry) {
        final AWSKMS kmsClient = AWSKMSClientBuilder
            .standard()
            .withRegion(this.region)
            .build();
        final KMSEncrypt kmsEncrypt = new KMSEncrypt(kmsClient, this.keyARN);

        String jwt;
        try {
            jwt = JWTEncoder.builder(kmsEncrypt)
                        .setExpiration(expirationDate(this.tokenDuration))
                        .claim("user", registry.getUser())
                        .claim("emailAddress", registry.getEmailAddress())
                        .claim("mfaDevice", registry.getMfaDeviceId())
                        .compact();
        } catch (KeyOperationException e) {
            throw new JWTManagerException("Issue creating JWT", e);
        }

        return jwt;
    }

    public Registry validateToken(String jwt) {
        AWSKMS kmsClient = AWSKMSClientBuilder.standard()
            .withRegion(region)
            .build();
        KMSDecrypt kmsDecrypt = new KMSDecrypt(
            kmsClient, Collections.singleton(this.keyARN));

        return new RegistryJWTDecoder(kmsDecrypt)
            .decodeAndVerify(jwt);
    }

}