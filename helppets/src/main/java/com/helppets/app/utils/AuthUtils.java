package com.helppets.app.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AuthUtils {
    private final String passwordSalt;
    private final PrivateKey rsaPrivateKey;
    private final PublicKey rsaPublicKey;

    private final Logger logger = LoggerFactory.getLogger(AuthUtils.class);
    private final ObjectMapper mapper = new ObjectMapper();

    public AuthUtils() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        try {
            Dotenv dotenv = Dotenv.configure()
                    .directory("/private/dotenv")
                    .load();

            passwordSalt = dotenv.get("PASSWORD_SALT");

            String userPath = System.getProperty("user.dir");

            File privateKeyFile = new File(userPath.concat("/src/main/resources/private/rsaKeys/privateJwt.key"));
            File publicKeyFile = new File(userPath.concat("/src/main/resources/private/rsaKeys/publicJwt.key"));

            byte[] privateKeyBytes = Files.readAllBytes(privateKeyFile.toPath());
            byte[] publicKeyBytes = Files.readAllBytes(publicKeyFile.toPath());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);

            rsaPrivateKey = keyFactory.generatePrivate(privateKeySpec);
            rsaPublicKey = keyFactory.generatePublic(publicKeySpec);
        }
        catch (Exception e) {
            Objects.requireNonNull(logger).error("AuthUtils() - Exception: {}", e.getMessage());
            throw e;
        }
    }
    public byte[] returnCryptedPassword(String password) {
        try {
            return BCrypt.withDefaults().hash(12, passwordSalt.getBytes(), password.getBytes());
        }
        catch (Exception e) {
            logger.error("returnCryptedPassword - Exception: {}", e.getMessage());
            return null;
        }
    }

    public boolean validatePassword(String password, byte[] hashedPassword) {
        try {
            return BCrypt.verifyer().verify(password.getBytes(), hashedPassword).verified;
        }
        catch (Exception e) {
            logger.error("validatePassword - Exception: {}", e.getMessage());
            return false;
        }
    }

    public String createJWT(Map<String, Object> payload) throws JWTCreationException {
        try {
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) rsaPublicKey, (RSAPrivateKey) rsaPrivateKey);

            return JWT.create()
                    .withPayload(payload)
                    .withIssuer("auth0")
                    .withExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                    .sign(algorithm);
        }
        catch (Exception e) {
            logger.error("createJWT - Exception: {}", e.getMessage());
            return null;
        }
    }

    public DecodedJWT decodeJWT(String jwt) {
        Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) rsaPublicKey, (RSAPrivateKey) rsaPrivateKey);

        DecodedJWT decodedJWT = null;

        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build();

            decodedJWT = verifier.verify(jwt);
        }
        catch (JWTVerificationException e) {
            logger.error("decodeJWT - Exception: {}", e.getMessage());
        }

        return decodedJWT;
    }

    public Map<String, Object> getPayloadJwt(DecodedJWT jwt) throws JsonProcessingException {
        try {
            byte[] decodedPayload = Base64.getDecoder().decode(jwt.getPayload());

            String stringPayload = new String(decodedPayload);

            return mapper.readValue(stringPayload, new TypeReference<Map<String, Object>>() {});
        }
        catch (Exception e) {
            logger.error("getPayloadJwt - Exception: {}", e.getMessage());
            return null;
        }
    }

    public Boolean isJwtValid(String jwt) {
        DecodedJWT decodedJWT = decodeJWT(jwt);

        return Objects.nonNull(decodedJWT) && decodedJWT.getExpiresAtAsInstant().compareTo(Instant.now()) >= 0;
    }
}
