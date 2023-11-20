package com.helppets.app.services;

import com.helppets.app.daos.VacinaDAO;
import com.helppets.app.models.VacinaModel;
import com.helppets.app.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class VacinaService {
    private final VacinaDAO vacinaDAO = new VacinaDAO();
    private final AuthUtils authUtils = new AuthUtils();
    private final Logger logger = LoggerFactory.getLogger(VacinaService.class);

    public VacinaService() throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {}

    public Map<String, Object> insertVacina(String jwtAuth, Map<String, Object> body) throws InvalidObjectException, SQLException, ParseException {
        try {
            if (!authUtils.isJwtValid(jwtAuth)) {
                throw new InvalidObjectException("Invalid jwt");
            }

            VacinaModel vacina = convertMapToVacinaModel(body);

            Map<String, Object> toReturn = new HashMap<>();

            toReturn.put("vacina", vacinaDAO.insert(vacina));

            return toReturn;
        }
        catch (Exception e) {
            logger.error("insertVacina({}, {}) - Exception: {}", jwtAuth, body, e.getMessage());
            throw e;
        }
    }

    public Map<String, Object> returnVacinasByPetsId(String jwtAuth, int petsId, int limiter) throws InvalidObjectException, SQLException {
        try {
            if (!authUtils.isJwtValid(jwtAuth)) {
                throw new InvalidObjectException("Invalid jwt");
            }

            List<VacinaModel> vacinas = vacinaDAO.listByPetsId(petsId, limiter);

            Map<String, Object> toReturn = new HashMap<>();

            toReturn.put("vacinas", vacinas);

            return toReturn;
        }
        catch (Exception e) {
            logger.error("returnVacinasByPetsId({}, {}) - Exception: {}", jwtAuth, petsId, e.getMessage());
            throw e;
        }
    }

    private VacinaModel convertMapToVacinaModel(Map<String, Object> map) throws ParseException {
        VacinaModel vacina = new VacinaModel();

        String dataString = (String) map.get("data");
        Date d = new java.sql.Date(new SimpleDateFormat("yyyy-mm-dd", Locale.ENGLISH).parse(dataString).getTime());

        vacina.setData(d);
        vacina.setNome((String) map.get("nome"));
        vacina.setTomou(Boolean.valueOf((String) map.get("tomou")));
        vacina.setDescricao((String) map.get("descricao"));
        vacina.setPets_petsId((Integer) map.get("pets_petsId"));

        return vacina;
    }

}
