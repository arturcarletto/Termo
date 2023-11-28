package com.termo.connection.implementation;

import com.termo.connection.SQLConnection;
import com.termo.connection.SQLResultAdapter;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SQLiteConnection implements SQLConnection {

    private static final String SQLITE_URL = "jdbc:sqlite:database.db";

    private final Map<Type, SQLResultAdapter<?>> adapters = new HashMap<>();

    @Override
    public Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            return java.sql.DriverManager.getConnection(SQLITE_URL);
        } catch (Exception e) {
            throw new RuntimeException("Failed to connect to SQLite database", e);
        }
    }

    @Override
    public Optional<SQLResultAdapter<?>> getRegisteredAdapter(Type type) {
        return Optional.ofNullable(adapters.get(type));
    }

    @Override
    public void registerAdapter(Type type, SQLResultAdapter<?> adapter) {
        adapters.put(type, adapter);
    }

    @Override
    public void onException(SQLException exception) {
        System.out.println("Um problema ocorreu ao executar uma operação no banco de dados!");
        System.out.println("Detalhes: " + exception.getMessage());
    }


}
