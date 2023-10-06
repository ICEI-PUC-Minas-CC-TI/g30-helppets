package com.helppets.app.daos;

import com.helppets.app.models.PetsModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PetsDAO extends DAO {
    public PetsDAO() {
        super();
    }

    @Override
    public PetsModel insert(Object object) {
        return null;
    }

    @Override
    public PetsModel getById(int id) {
        return null;
    }

    @Override
    public PetsModel deleteById(int id) {
        return null;
    }

    @Override
    public List<PetsModel> selectAllWithLimiter(int limiter) {
        return null;
    }

    @Override
    public PetsModel updateById(int id, Object object) {
        return null;
    }

    @Override
    protected PetsModel parseRowToDto(ResultSet resultSet) throws SQLException {
        return null;
    }
}
