package org.esfe.BeatySaly.repositorios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String getPasswordHash(String correo, String tabla) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT password FROM " + tabla + " WHERE correo = ?",
                    String.class,
                    correo
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public String getRole(String correo, String tabla) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT rol FROM " + tabla + " WHERE correo = ?",
                    String.class,
                    correo
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
