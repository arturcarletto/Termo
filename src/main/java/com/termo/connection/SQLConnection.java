package com.termo.connection;

import com.termo.connection.exception.AdapterNotFoundException;
import com.termo.connection.exception.InvalidAdapterException;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface SQLConnection {

    Connection connect();

    default void execute(String sql) {
        execute(sql, preparedStatement -> {
        });
    }

    default void execute(String sql, SQLPreparedStatement statement) {
        try (var connection = connect();
             var preparedStatement = connection.prepareStatement(sql)) {
            statement.accept(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            onException(exception);
        }
    }

    default <T> Optional<T> execute(String sql, SQLType<T> typeToken) {
        return execute(sql, preparedStatement -> {
        }, typeToken);
    }

    default <T> Optional<T> execute(String sql, SQLPreparedStatement statement, SQLType<T> typeToken) {
        try (var connection = connect();
             var preparedStatement = connection.prepareStatement(sql)) {

            var adapter = getRegisteredAdapter(typeToken.getType())
                    .orElseThrow(() -> new AdapterNotFoundException("No adapter found for type " + typeToken.getType().getTypeName()));

            statement.accept(preparedStatement);

            try (var resultSet = preparedStatement.executeQuery()) {
                try {
                    return !resultSet.isBeforeFirst() && resultSet.getRow() == 0 ?
                            Optional.empty()
                            : Optional.of((T) adapter.adapt(resultSet));
                } catch (ClassCastException exception) {
                    throw  new InvalidAdapterException("Adapter for type " + typeToken.getType().getTypeName() + " is invalid", exception);
                }
            }
        } catch (SQLException exception) {
            onException(exception);
            return Optional.empty();
        }
    }

    Optional<SQLResultAdapter<?>> getRegisteredAdapter(Type type);

    void registerAdapter(Type type, SQLResultAdapter<?> adapter);

    void onException(SQLException exception);

}
