package com.helppets.app.services;

import com.helppets.app.daos.UsuarioDAO;
import com.helppets.app.models.UsuarioModel;
import com.helppets.app.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final AuthUtils authUtils = new AuthUtils();

    public AuthService() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
    }

    public Map<String, String> login(Map<String, Object> body) throws SQLException {
        try {
            UsuarioModel user = usuarioDAO.getByEmailAndPassword((String) body.get("email"),
                                                                    authUtils.returnCryptedPassword((String) body.get("senha")));

            if (Objects.isNull(user)) {
                throw new NullPointerException("User not found");
            }

            if (!authUtils.validatePassword((String) body.get("senha"), user.getSenha())) {
                throw new NullPointerException("Invalid password");
            }

            Map<String, Object> tokenData = new HashMap<>();

            tokenData.put("usuarioId", user.getUsuarioId());
            tokenData.put("nome", user.getNome());
            tokenData.put("email", user.getEmail());

            Map<String, String> token = new HashMap<>();

            token.put("token", authUtils.createJWT(tokenData));

            return token;
        }
        catch (Exception e) {
            logger.error("login({}) - Exception: {}", body, e.getMessage());
            throw e;
        }
    }

    public Map<String, String> register(Map<String, Object> body) throws SQLException {
        try {
            UsuarioModel usuarioModel = usuarioModelFromMap(body);

            usuarioDAO.insert(usuarioModel);

            Map<String, String> userMap = new HashMap<>();

            userMap.put("nome", usuarioModel.getNome());
            userMap.put("email", usuarioModel.getEmail());

            return userMap;
        }
        catch (Exception e) {
            logger.error("register({}) - Exception: {}", body, e.getMessage());
            throw e;
        }
    }

    private UsuarioModel usuarioModelFromMap(Map<String, Object> map) {
        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome((String) map.get("nome"));
        usuario.setEmail((String) map.get("email"));
        usuario.setSenha(authUtils.returnCryptedPassword((String) map.get("senha")));

        return usuario;
    }
}
