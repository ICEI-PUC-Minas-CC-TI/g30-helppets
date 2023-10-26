package com.helppets.app.daos;

import com.helppets.app.models.CalendarioModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CalendarioDAO extends DAO{
    @Override
    public CalendarioModel insert(Object object) throws SQLException {
        return null;
    }

    @Override
    public CalendarioModel getById(int id) throws SQLException {
        return null;
    }

    @Override
    public CalendarioModel deleteById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<CalendarioModel> selectAllWithLimiter(int limiter) throws SQLException {
        return null;
    }

    @Override
    public CalendarioModel updateById(int id, Object object) throws SQLException {
        return null;
    }

    @Override
    protected CalendarioModel parseRowToDto(ResultSet resultSet) throws SQLException {
        return null;
    }
}
