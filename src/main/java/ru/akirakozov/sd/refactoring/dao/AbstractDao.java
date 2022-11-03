package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.exception.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Function;

public abstract class AbstractDao {
    private final String dbName;
    
    public AbstractDao(final String dbName) {
        this.dbName = dbName;
    }
    
    protected int execute(final String sql) throws DaoException {
        try (final Connection connection = DriverManager.getConnection(dbName)) {
            try (final Statement statement = connection.createStatement()) {
                return statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception in query: " + sql);
        }
    }
    
    protected <T> T execute(final String sql, final Function<ResultSet, T> function) throws DaoException {
        try (final Connection connection = DriverManager.getConnection(dbName)) {
            try (final Statement statement = connection.createStatement()) {
                try (final ResultSet resultSet = statement.executeQuery(sql)) {
                    return function.apply(resultSet);
                }
            }
        } catch (SQLException e) {
            throw new DaoException("SQL exception in query: " + sql);
        }
    }
}
