package com.helppets.app.daos;

import com.helppets.app.models.CalendarioModel;
import com.helppets.app.models.UsuarioModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CalendarioDAO extends DAO{
    @Override
    public CalendarioModel insert(Object object) throws SQLException {
        makeConnection();

        CalendarioModel calendario = (CalendarioModel) object;

        PreparedStatement statement = returnPreparedStatement("INSERT INTO calendario(descricao, data, usuario_usuarioid) VALUES(?, ?, ?) RETURNING *");

        statement.setString(1, calendario.getDescricao());
        statement.setDate(2, calendario.getData());
        statement.setInt(3, calendario.getUsuario_usuarioId());

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            calendario = parseRowToDto(resultSet);
        }

        closeConnection();

        return calendario;
    }

    @Override
    public CalendarioModel getById(int id) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM calendario WHERE eventoid=" + id);

        CalendarioModel calendario = null;

        if (resultSet.next()) {
            calendario = parseRowToDto(resultSet);
        }

        closeConnection();
        resultSet.close();

        return calendario;
    }

    @Override
    public CalendarioModel deleteById(int id) throws SQLException {
        CalendarioModel calendario = getById(id);

        makeConnection();

        int resultSet = returnStatement().executeUpdate("DELETE FROM calendario WHERE eventoid=" + id);

        closeConnection();

        return resultSet > 0 ? calendario: null;
    }

    @Override
    public List<CalendarioModel> selectAllWithLimiter(int limiter) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM calendario LIMIT "+ limiter);

        ArrayList<CalendarioModel> eventos = new ArrayList<>();

        while (resultSet.next()) {
            eventos.add(parseRowToDto(resultSet));
        }

        closeConnection();
        resultSet.close();

        return eventos;
    }

    @Override
    public CalendarioModel updateById(int id, Object object) throws SQLException {
        makeConnection();

        CalendarioModel calendario = (CalendarioModel) object;

        PreparedStatement statement = returnPreparedStatement("UPDATE calendario SET descricao=?, data=? WHERE eventoid=?");

        statement.setString(1, calendario.getDescricao());
        statement.setDate(2, calendario.getData());

        statement.executeUpdate();
        closeConnection();

        calendario = getById(id);

        return calendario;
    }

    @Override
    protected CalendarioModel parseRowToDto(ResultSet resultSet) throws SQLException {
        CalendarioModel calendarioModel = new CalendarioModel();

        try {
            calendarioModel.setEventoId(resultSet.getInt("eventoid"));
            calendarioModel.setDescricao(resultSet.getString("descricao"));
            calendarioModel.setData(resultSet.getDate("data"));
            calendarioModel.setUsuario_usuarioId(resultSet.getInt("usuario_usuarioId"));
        }
        catch (Exception e) {
            logger.error("parseRowToDto - Exception: {}", e.getMessage());
            return null;
        }

        return calendarioModel;
    }

    public CalendarioModel getByIdAndUserId(int id, int userId) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM calendario WHERE  " + "user_userId=" + userId + " AND eventoid=" + id);

        CalendarioModel calendario = null;

        if (resultSet.next()) {
            calendario = parseRowToDto(resultSet);
        }

        closeConnection();
        resultSet.close();

        return calendario;
    }

    public List<CalendarioModel> getByUserId(int userId, int limiter) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM calendario WHERE usuario_usuarioId =" + userId + " LIMIT " + limiter);

        ArrayList<CalendarioModel> eventos = new ArrayList<>();

        while (resultSet.next()) {
            eventos.add(parseRowToDto(resultSet));
        }

        closeConnection();
        resultSet.close();

        return eventos;
    }
}
