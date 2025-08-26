package org.esfe.BeatySaly.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class AuthRepository {

    private final DataSource dataSource;

    @Autowired
    public AuthRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public String getPasswordHash(String correo, String tableName) {

        // 1. Lista de nombres de tablas permitidos para evitar inyección SQL.
        List<String> allowedTables = Arrays.asList("administradores", "trabajadores", "clientes");

        // 2. Valida que el nombre de la tabla esté en la lista permitida.
        if (!allowedTables.contains(tableName)) {
            System.err.println("Intento de acceso a tabla no permitida: " + tableName);
            return null; // O lanza una excepción.
        }

        String sql = "SELECT password FROM " + tableName + " WHERE correo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, correo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
