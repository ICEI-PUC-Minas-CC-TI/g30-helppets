package com.helppets.app.daos;

import com.helppets.app.models.VacinaModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class VacinaDAO extends DAO {
    public VacinaDAO() {
        super();
    }

    @Override
    public VacinaModel insert(Object object) throws SQLException {
        return null;
    }

    @Override
    public VacinaModel getById(int id) throws SQLException {
        return null;
    }

    @Override
    public VacinaModel deleteById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<VacinaModel> selectAllWithLimiter(int limiter) throws SQLException {
        return null;
    }

    @Override
    public VacinaModel updateById(int id, Object object) throws SQLException {
        return null;
    }

    @Override
    protected VacinaModel parseRowToDto(ResultSet resultSet) throws SQLException {
        return null;
    }


}
