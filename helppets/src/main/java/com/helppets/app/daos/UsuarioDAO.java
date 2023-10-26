package com.helppets.app.daos;

import com.helppets.app.models.UsuarioModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends DAO {
    public UsuarioDAO() {
        super();
    }

    @Override
    public UsuarioModel insert(Object object) throws SQLException {
        makeConnection();

        UsuarioModel user = (UsuarioModel) object;

        PreparedStatement statement = returnPreparedStatement("INSERT INTO USUARIO(nome, email, senha) VALUES(?, ?, ?)");

        statement.setString(1, user.getNome());
        statement.setString(2, user.getEmail());
        statement.setBytes(3, user.getSenha());

        statement.executeUpdate();

        closeConnection();

        return user;
    }

    @Override
    public UsuarioModel getById(int id) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM USUARIO WHERE usuarioid=" + id);

        UsuarioModel user = null;

        if (resultSet.next()) {
            user = parseRowToDto(resultSet);
        }

        closeConnection();
        resultSet.close();

        return user;
    }

    @Override
    public UsuarioModel deleteById(int id) throws SQLException {
        UsuarioModel usuario = getById(id);

        makeConnection();

        int resultSet = returnStatement().executeUpdate("DELETE FROM USUARIO WHERE usuarioid=" + id);

        closeConnection();

        return resultSet > 0 ? usuario : null;
    }

    @Override
    public List<UsuarioModel> selectAllWithLimiter(int limiter) throws SQLException {
        makeConnection();

        ResultSet resultSet = returnStatement().executeQuery("SELECT * FROM USUARIO LIMIT "+ limiter);

        ArrayList<UsuarioModel> usuarios = new ArrayList<>();

        while (resultSet.next()) {
            usuarios.add(parseRowToDto(resultSet));
        }

        closeConnection();
        resultSet.close();

        return usuarios;
    }

    @Override
    public UsuarioModel updateById(int id, Object object) throws SQLException {
        makeConnection();

        UsuarioModel usuario = (UsuarioModel) object;

        PreparedStatement statement = returnPreparedStatement("UPDATE USUARIO SET nome=?, email=?, senha=? WHERE usuarioid=?");

        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getEmail());
        statement.setBytes(3, usuario.getSenha());
        statement.setInt(4, id);

        statement.executeUpdate();

        usuario = (UsuarioModel) getById(id);

        return usuario;
    }

    @Override
    protected UsuarioModel parseRowToDto(ResultSet resultSet) throws SQLException {
        UsuarioModel usuarioModel = new UsuarioModel();

        try {
            usuarioModel.setUsuarioId(resultSet.getInt("UsuarioId"));
            usuarioModel.setNome(resultSet.getString("nome"));
            usuarioModel.setEmail(resultSet.getString("email"));
            usuarioModel.setSenha(resultSet.getBytes("senha"));
        }
        catch (Exception e) {
            logger.error("parseRowToDto - Exception: {}", e.getMessage());
            return null;
        }

        return usuarioModel;
    }

    public UsuarioModel getByEmailAndPassword(String email, byte[] password) throws SQLException {
        makeConnection();

        PreparedStatement statement = returnPreparedStatement("SELECT * FROM USUARIO WHERE email=? AND senha=?");

        statement.setString(1, email);
        statement.setBytes(2, password);

        statement.executeQuery();

        ResultSet resultSet = statement.executeQuery();

        UsuarioModel user = null;

        if (resultSet.next()) {
            user = parseRowToDto(resultSet);
        }

        closeConnection();
        resultSet.close();

        return user;
    }
}
