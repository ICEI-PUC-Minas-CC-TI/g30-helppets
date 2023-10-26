package com.helppets.app.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.helppets.app.daos.PetsDAO;
import com.helppets.app.models.PetsModel;
import com.helppets.app.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PetsService {
    private final String USER_ID = "usuarioId";
    private final AuthUtils authUtils = new AuthUtils();
    private final PetsDAO petsDAO = new PetsDAO();
    private final Logger logger = LoggerFactory.getLogger(PetsService.class);

    public PetsService() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
    }

    public Map<String, Object> insertPet(String jwtAuth, Map<String, Object> body) throws JsonProcessingException, InvalidObjectException, SQLException {
        try {
            if (!authUtils.isJwtValid(jwtAuth)) {
                throw new InvalidObjectException("Invalid jwt");
            }

            DecodedJWT decodedJWT = authUtils.decodeJWT(jwtAuth);
            Map<String, Object> jwtData = authUtils.getPayloadJwt(decodedJWT);

            PetsModel pet = convertMapToPetModel(body, (Integer) jwtData.get(USER_ID));

            petsDAO.insert(pet);

            Map<String, Object> petMap = new HashMap<>();

            petMap.put("pet", pet);

            return petMap;

        }
        catch (Exception e) {
            logger.error("insertPet({}, {}) - Exception: {}", jwtAuth, body, e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> listRegisteredPetsWithLimit(String jwtAuth, int limiter) throws InvalidObjectException, JsonProcessingException, SQLException {
        try {
            if (!authUtils.isJwtValid(jwtAuth)) {
                throw new InvalidObjectException("Invalid jwt");
            }

            DecodedJWT decodedJWT = authUtils.decodeJWT(jwtAuth);
            Map<String, Object> jwtData = authUtils.getPayloadJwt(decodedJWT);

            List<PetsModel> pets = petsDAO.selectByUserIdWithLimiter((Integer) jwtData.get(USER_ID), limiter);

            Map<String, Object> toReturn = new HashMap<>();

            toReturn.put("pets", pets);

            return toReturn;
        }
        catch (Exception e) {
            logger.error("listRegisteredAnimalsWithLimit({}, {}) - Exception: {}", jwtAuth, limiter, e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> deleteById(String jwtAuth, int id) throws SQLException, InvalidObjectException, JsonProcessingException {
        try {
            if (!authUtils.isJwtValid(jwtAuth)) {
                throw new InvalidObjectException("Invalid jwt");
            }

            DecodedJWT decodedJWT = authUtils.decodeJWT(jwtAuth);
            Map<String, Object> jwtData = authUtils.getPayloadJwt(decodedJWT);

            PetsModel pet = petsDAO.deleteByIdAndUserId((Integer) jwtData.get(USER_ID),id);

            Map<String, Object> toReturn = new HashMap<>();

            toReturn.put("deleted", pet);

            return toReturn;
        }
        catch (Exception e) {
            logger.error("deleteById({}, {}) - Exception: {}", jwtAuth, id, e.getMessage());
            throw e;
        }
    }

    private PetsModel convertMapToPetModel(Map<String, Object> map, Integer usuarioId) {
        PetsModel pet = new PetsModel();

        pet.setRaca((String) map.get("raca"));
        pet.setNome((String) map.get("nome"));
        pet.setUsuario_usuarioId(usuarioId);

        if (map.containsKey("foto") && Objects.nonNull(map.get("foto"))) {
            pet.setFoto((byte[]) map.get("foto"));
        }

        return pet;
    }
}
