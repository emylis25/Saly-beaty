package org.esfe.BeatySaly.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public String getPasswordHash(String email, String tableName) {
        String sql = "SELECT password FROM " + tableName + " WHERE correo = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
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
