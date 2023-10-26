package com.helppets.app.daos;

import com.helppets.app.models.PetsModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetsDAO extends DAO {
    public PetsDAO() {
        super();
    }

    @Override
    public PetsModel insert(Object object) throws SQLException {
        makeConnection();

        PetsModel pet = (PetsModel) object;

        PreparedStatement statement = returnPreparedStatement("INSERT INTO pets(nome, raca, foto, usuario_usuarioid) values(?, ?, ?, ?)");

        statement.setString(1, pet.getNome());
        statement.setString(2, pet.getRaca());
        statement.setBytes(3, pet.getFoto());
        statement.setInt(4, pet.getUsuario_usuarioId());

        statement.executeUpdate();

        closeConnection();

        return pet;
    }

    @Override
    public PetsModel getById(int id) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM pets WHERE petsid=" + id);

        PetsModel pet = null;

        if (resultSet.next()) {
            pet = parseRowToDto(resultSet);
        }

        closeConnection();
        resultSet.close();

        return pet;
    }

    @Override
    public PetsModel deleteById(int id) throws SQLException {
        PetsModel pet = getById(id);

        makeConnection();

        int resultSet = returnStatement().executeUpdate("DELETE FROM pets WHERE petsId=" + id);

        closeConnection();

        return resultSet > 0 ? pet: null;
    }

    @Override
    public List<PetsModel> selectAllWithLimiter(int limiter) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM pets LIMIT "+ limiter);

        ArrayList<PetsModel> pets = new ArrayList<>();

        while (resultSet.next()) {
            pets.add(parseRowToDto(resultSet));
        }

        closeConnection();
        resultSet.close();

        return pets;
    }

    @Override
    public PetsModel updateById(int id, Object object) throws SQLException {
        makeConnection();

        PetsModel pet = (PetsModel) object;

        PreparedStatement statement = returnPreparedStatement("UPDATE pet SET nome=?, raca=?, foto=? WHERE petsid=?");

        statement.setString(1, pet.getNome());
        statement.setString(2, pet.getRaca());
        statement.setBytes(3, pet.getFoto());
        statement.setInt(4, id);

        statement.executeUpdate();

        pet = (PetsModel) getById(id);

        return pet;
    }

    @Override
    protected PetsModel parseRowToDto(ResultSet resultSet) throws SQLException {
        PetsModel petsModel = new PetsModel();

        try {
            petsModel.setPetsId(resultSet.getInt("petsid"));
            petsModel.setRaca(resultSet.getString("raca"));
            petsModel.setNome(resultSet.getString("nome"));
            petsModel.setUsuario_usuarioId(resultSet.getInt("usuario_usuarioid"));
            petsModel.setFoto(resultSet.getBytes("foto"));
        }
        catch (Exception e) {
            logger.error("parseRowToDto - Exception: {}", e.getMessage());
            return null;
        }

        return petsModel;
    }

    public List<PetsModel> selectByUserIdWithLimiter(int userId, int limiter) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM pets where usuario_usuarioid=" + userId + " LIMIT "+ limiter);

        ArrayList<PetsModel> pets = new ArrayList<>();

        while (resultSet.next()) {
            pets.add(parseRowToDto(resultSet));
        }

        closeConnection();
        resultSet.close();

        return pets;
    }

    public PetsModel deleteByIdAndUserId(int userId, int id) throws SQLException {
        PetsModel pet = getById(id);

        makeConnection();

        int resultSet = returnStatement().executeUpdate("DELETE FROM pets WHERE usuario_usuarioid=" + userId + " AND petsId=" + id);

        closeConnection();

        return resultSet > 0 ? pet: null;
    }
}
