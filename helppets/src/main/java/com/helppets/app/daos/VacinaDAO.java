package com.helppets.app.daos;

import com.helppets.app.models.UsuarioModel;
import com.helppets.app.models.VacinaModel;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VacinaDAO extends DAO {
    public VacinaDAO() {
        super();
    }

    @Override
    public VacinaModel insert(Object object) throws SQLException {
        makeConnection();

        VacinaModel vacina = (VacinaModel) object;

        PreparedStatement statement = returnPreparedStatement("INSERT INTO vacina(nome, data, descricao, tomou, pets_petsid) VALUES(?, ?, ?, ?, ?) RETURNING *");

        statement.setString(1, vacina.getNome());
        statement.setDate(2, vacina.getData());
        statement.setString(3, vacina.getDescricao());
        statement.setInt(4, vacina.getTomou() ? 1 : 0);
        statement.setInt(5, vacina.getPets_petsId());

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            vacina = parseRowToDto(resultSet);
        }

        closeConnection();

        return vacina;
    }

    @Override
    public VacinaModel getById(int id) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM vacina WHERE vacinaid=" + id);

        VacinaModel vacina = null;

        if (resultSet.next()) {
            vacina = parseRowToDto(resultSet);
        }

        closeConnection();
        resultSet.close();

        return vacina;
    }

    @Override
    public VacinaModel deleteById(int id) throws SQLException {
        VacinaModel vacina = getById(id);

        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("DELETE FROM vacina WHERE vacinaid=" + id + " RETURNING *");

        if (resultSet.next()) {
            vacina = parseRowToDto(resultSet);
        }

        resultSet.close();
        closeConnection();

        return vacina;
    }

    @Override
    public List<VacinaModel> selectAllWithLimiter(int limiter) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM vacina LIMIT "+ limiter);

        ArrayList<VacinaModel> vacinas = new ArrayList<>();

        while (resultSet.next()) {
            vacinas.add(parseRowToDto(resultSet));
        }

        closeConnection();
        resultSet.close();

        return vacinas;
    }

    @Override
    public VacinaModel updateById(int id, Object object) throws SQLException {
        makeConnection();

        VacinaModel vacina = (VacinaModel) object;

        PreparedStatement statement = returnPreparedStatement("UPDATE vacina SET nome=?, data=?, descricao=?, tomou=? WHERE usuarioid=? RETURNING *");

        statement.setString(1, vacina.getNome());
        statement.setDate(2, vacina.getData());
        statement.setString(3, vacina.getDescricao());
        statement.setBoolean(4, vacina.getTomou());
        statement.setInt(5, vacina.getPets_petsId());

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            vacina = parseRowToDto(resultSet);
        }

        return vacina;
    }

    @Override
    protected VacinaModel parseRowToDto(ResultSet resultSet) throws SQLException {
        VacinaModel vacina = new VacinaModel();

        try {
           vacina.setVacinaId(resultSet.getInt("vacinaid"));
           vacina.setNome(resultSet.getString("nome"));
           vacina.setData(resultSet.getDate("data"));
            vacina.setDescricao(resultSet.getString("descricao"));
           vacina.setTomou(resultSet.getBoolean("tomou"));
           vacina.setPets_petsId(resultSet.getInt("pets_petsid"));
        }
        catch (Exception e) {
            logger.error("parseRowToDto - Exception: {}", e.getMessage());
            return null;
        }

        return vacina;
    }

    public List<VacinaModel> listByPetsId(int petsId, int limiter) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM vacina WHERE pets_petsid=" + petsId + " LIMIT "+ limiter);

        ArrayList<VacinaModel> vacinas = new ArrayList<>();

        while (resultSet.next()) {
            vacinas.add(parseRowToDto(resultSet));
        }

        closeConnection();
        resultSet.close();

        return vacinas;
    }

}
